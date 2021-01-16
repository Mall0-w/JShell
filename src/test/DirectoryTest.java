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

import entity.Directory;
import entity.File;
import entity.FileSystem;
import entity.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.*;

public class DirectoryTest {

  static FileSystem fs = FileSystem.getInstance();

  static Directory root = fs.getRoot(), d1, d3;

  static File f1, f2;

  @BeforeClass
  public static void setUpClass() throws Exception {
    f1 = new File("f1");
    f2 = new File("f2");
    d1 = new Directory("d1");

    root.create("/f1", f1);
    root.create("d1", d1);
    root.create("d1/f2", f2);

    // notice even though we initialized d3's name to "d2"
    // It should be changed to "d3" when inserted
    d3 = new Directory("d2");
    root.create("d3", d3);
  }

  @After
  public void tearDown() throws Exception {

  }


  @Test
  public void testCreate() {
    // insert success is already sorta built into the setUpClass
    assertFalse(root.create("/f1", Path.Type.F));
    assertFalse(root.create("f1", Path.Type.F));
    assertFalse(root.create("d1/f2", Path.Type.D));
  }

  @Test
  public void testSearch() {
    assertEquals(f1, root.search("f1"));
    assertEquals(d1, root.search("d1"));
    assertEquals(f2, root.search("d1/f2"));
    assertEquals(f1, root.search("/f1"));
    assertEquals(d1, root.search("/d1"));
    assertEquals(f2, root.search("/d1/f2"));
    assertNull(root.search("Something That Shouldn't Exist"));
    assertEquals(f2, d1.search("f2"));
  }

  @Test
  public void testGetDirectChild() {
    assertEquals(f1, root.getDirectChild("f1"));
    assertEquals(d1, root.getDirectChild("d1"));
    assertEquals(f2, d1.getDirectChild("f2"));
  }

  @Test
  public void testFormatPathString() {
    assertEquals(Directory.formatPathString("A"), "A");
    assertEquals(Directory.formatPathString("A/"), "A");
    assertEquals(Directory.formatPathString("A/B"), "A/B");
    assertEquals(Directory.formatPathString("A/B/"), "A/B");
    assertEquals(Directory.formatPathString("/"), "/");
    assertEquals(Directory.formatPathString("/A"), "/A");
    assertEquals(Directory.formatPathString("/A/"), "/A");
    assertEquals(Directory.formatPathString("/A/B"), "/A/B");
    assertEquals(Directory.formatPathString("/A/B/"), "/A/B");
  }

  @Test
  public void testToString() {
    // Dude to order reason, this test is done by printing to terminal and checking manually
    System.out.println("========= Test toString =========");
    System.out.println("Expected:");
    System.out.println(String.format("Path: %s, Childs: . .. f2", d1.getPath()));
    System.out.println("Actual:");
    System.out.println(d1.toString());
    System.out.println("========= End Test toString =========");
  }

  @Test
  public void testGetChildNames() {
    HashSet<String> expected = new HashSet<>();
    for (String s : "f1 d1 d3".split(" ")) {
      expected.add(s);
    }
    HashSet<String> actual = new HashSet<>();
    for (String s : root.getChildNames()) {
      actual.add(s);
    }
    assertEquals(expected, actual);
  }

  @Test
  public void testGetDirectChilds() {
    HashSet<Path> expected = new HashSet<>();
    expected.add(f1);
    expected.add(d1);
    expected.add(d3);
    ArrayList<Path> actualBuffer = root.getDirectChilds();
    HashSet<Path> actual = new HashSet<>();
    for (Path p : actualBuffer) {
      actual.add(p);
    }
    assertEquals(expected, actual);
  }

  @Test
  public void testGetIndirectChilds() {
    ArrayList<Path> expected = new ArrayList();
    expected.add(f1);
    expected.add(root);
    expected.add(f2);
    expected.add(d1);
    expected.add(d3);
    assertTrue(root.getIndirectChilds().containsAll(expected)
        && expected.containsAll(root.getIndirectChilds()));
  }

  @Test
  public void testGetChilds() {
    HashMap<String, Path> expected = new HashMap<>();
    expected.put(".", root);
    expected.put("f1", f1);
    expected.put("d1", d1);
    expected.put("d3", d3);
    assertEquals(expected, root.getChilds());
  }
}
