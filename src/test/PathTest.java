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

import entity.*;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * Unit tests for public Path object methods.
 *
 */
public class PathTest {

  FileSystem fs = FileSystem.getInstance();

  Path root, child;

  @Before
  public void setUp() {
    root = fs.getRoot();
    child = new Path("bar", root);
  }

  @Test
  public void testGetName() {
    assertEquals("bar", child.getName());
  }

  @Test
  public void testGetParent() {
    assertEquals(root, child.getParent());
  }

  @Test
  public void testGetPath() {
    assertEquals("/", root.getPath());
    assertEquals("/bar", child.getPath());
  }

  @Test
  public void testGetRoot() {
    assertEquals(fs.getRoot(), root.getRoot());
    assertEquals(fs.getRoot(), child.getRoot());
  }

  @Test
  public void testSetName() {
    root.setName("foo");
    child.setName("bar");
    assertEquals("foo", root.getName());
    assertEquals("bar", child.getName());
  }

  @Test
  public void testSetParent() {
    Path temp = new Path();
    child.setParent(temp);
    assertEquals(temp, child.getParent());
  }

  @Test
  public void testIsDirectory() {
    assertTrue(root.isDirectory());
    assertFalse(child.isFile());
  }

  @Test
  public void testIsFile() {
    assertFalse(child.isFile());
    assertFalse(root.isFile());
    Path temp = new File("Temp");
    assertTrue(temp.isFile());
  }

  @Test
  public void testToString() {
    assertEquals("Path: /, Childs: .", root.toString());
    assertEquals("Path: /bar", child.toString());
    Path temp = new Path("hello", child);
    assertEquals("Path: /bar/hello", temp.toString());
  }

  @After
  public void tearDown() {

  }
}
