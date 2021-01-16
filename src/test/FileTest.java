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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileTest {

  private FileSystem fs = FileSystem.getInstance();
  private Directory root = fs.getRoot();
  private File f1;

  @Before
  public void setUp() throws Exception {
    f1 = new File("f1", root, "Hello World");
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testGetContent() {
    assertEquals("Hello World", f1.getContent());
    File f2 = new File("f2", null, "foo bar");
    assertEquals("foo bar", f2.getContent());
  }

  @Test
  public void testSetContent() {
    f1.setContent("Something different");
    assertEquals("Something different", f1.getContent());
    f1.setContent("Expecting overwrite");
    assertEquals("Expecting overwrite", f1.getContent());
  }

  @Test
  public void testAppendContent() {
    f1.appendContent("Expecting append\n");
    assertEquals("Hello WorldExpecting append\n", f1.getContent());
    f1.appendContent("\nnewline");
    assertEquals("Hello WorldExpecting append\n\nnewline", f1.getContent());
  }

  @Test
  public void testToString() {
    assertEquals("Path: /f1, Content: Hello World", f1.toString());

  }
}
