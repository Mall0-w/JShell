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

import commands.Tree;
import entity.Directory;
import entity.FileSystem;
import entity.Path;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Unit tests for the tree command.
 */
public class TreeTest {

  Tree tree = new Tree();
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
  public void run() {
    assertEquals(root, tree.run(new String[0], root, redirectionArgs));
    assertEquals(dir, tree.run(new String[0], dir, redirectionArgs));
  }

  @Test
  public void testTree() {
    StringBuilder sb = new StringBuilder("\\\n");
    sb.append("\tf1\n");
    sb.append("\td1\n");
    sb.append("\t\tf2\n");
    sb.append("\td2\n");
    sb.append("\t\td2\n");
    sb.append("\t\t\tf3\n");
    sb.append("\t\t\tf4\n");
    HashMap<String, Integer> expected = new HashMap<>();
    for (String s : sb.toString().split("\n")) {
      expected.put(s, expected.getOrDefault(s, 1));
    }

    HashMap<String, Integer> actual = new HashMap<>();
    for (String s : tree.tree(root).split("\n")) {
      actual.put(s, actual.getOrDefault(s, 0) + 1);
    }
    assertEquals(expected, actual);

    actual = new HashMap<>();
    for (String s : tree.tree(dir).split("\n")) {
      actual.put(s, actual.getOrDefault(s, 0) + 1);
    }
    assertEquals(expected, actual);
  }

  @Test
  public void testExpectedNumOfArgs() {
    assertEquals(0, tree.expectedNumOfArgs());
    assertNotEquals(1, tree.expectedNumOfArgs());
  }

  @AfterClass
  public static void tearDown() throws Exception {
    fs = null;
    root = null;
    dir = null;
    redirectionArgs = null;
  }
}
