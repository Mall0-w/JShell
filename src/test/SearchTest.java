// **********************************************************
// Assignment2:

// Student1:
// UTORID user_name: huan1942
// UT Student #: 1006274274
// Author: Carlos Fei Huang
//
// Student2: Kyle Lewis
// UTORID user_name: lewisky2
// UT Student #: 1006113215
// Author: Kyle Lewis
//
// Student3: Glenn Qing Yuan Ye
// UTORID user_name: yeglenn
// UT Student #: 1006102977
// Author: Glenn Qing Yuan Ye
//
// Student4: Youzhang Sun (Mark)
// UTORID user_name: sunyou
// UT Student #: 1005982830
// Author: Youzhang Sun
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************

package test;

import commands.Search;
import entity.Directory;
import entity.FileSystem;
import entity.Path;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Unit tests for the search command.
 */
public class SearchTest {

  Search search = new Search();
  static FileSystem fs = FileSystem.getInstance();
  static Directory root = fs.getRoot();
  static Directory dir;
  static String[] redirectionArgs = new String[] {"", ""};

  @BeforeClass
  public static void setup() {
    root.create("d1", Path.Type.D);
    root.create("d2", Path.Type.D);
    root.create("f1", Path.Type.F);
    root.create("d1/f2", Path.Type.F);
    root.create("d2/d2", Path.Type.D);
    root.create("/d2/d2/f3", Path.Type.F);
    root.create("d2/d2/f4", Path.Type.F);
    dir = (Directory) root.search("d1");
  }


  @Test
  public void testRun() {
    String[] args = {"search", "/", "-type", "f", "-name", "\"f1\""};
    assertEquals(root, search.run(args, root, redirectionArgs));
    assertEquals(dir, search.run(args, dir, redirectionArgs));
  }

  @Test
  public void testSearch() {
    HashMap<String, Integer> expect = new HashMap<>();
    HashMap<String, Integer> actual = new HashMap<>();
    String[] args = {"search", "/", "-type", "f", "-name", "\"f1\""};
    populateStringHashMap(expect, "/f1\n", "\n");
    populateStringHashMap(actual, search.search(args, root), "\n");
    assertEquals(expect, actual);
    String[] args2 = {"search", "/", "/d2", "-type", "d", "-name", "\"d2\""};
    populateStringHashMap(expect, "/d2\n/d2/d2\n/d2/d2\n", "\n");
    populateStringHashMap(actual, search.search(args2, root), "\n");
    assertEquals(expect, actual);
  }

  private void populateStringHashMap(HashMap<String, Integer> map, String string,
      String splitRegex) {
    map = new HashMap<>();
    for (String s : string.split(splitRegex)) {
      map.put(s, map.getOrDefault(s, 0) + 1);
    }
  }

  @Test
  public void testCheckInput() {
    // mostly just check search, since the rest are simple one liner
    assertTrue(search.checkInput(1, "/", root));
    assertTrue(search.checkInput(1, "d1", root));
    assertFalse(search.checkInput(1, "f1", root));

  }

  @Test
  public void testExpectedNumOfArgs() {
    assertEquals(-5, search.expectedNumOfArgs());
  }

  @AfterClass
  public static void tearDown() throws Exception {
    fs = null;
    root = null;
    dir = null;
    redirectionArgs = null;
  }
}
