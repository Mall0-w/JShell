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

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import commands.Concatenate;
import entity.*;

/**
 * Unit tests for the cat command.
 */
public class ConcatenateTest {
  static FileSystem fs = FileSystem.getInstance();
  static Directory currDir = fs.getRoot();
  Concatenate cat = new Concatenate();
  String redirectionArgs[] = {">", "test"};



  /**
   * Set up mock file system and command objects for testing.
   */
  @BeforeClass
  public static void setUp() {
    currDir.create("d1", Path.Type.D);
    currDir.create("d2", Path.Type.D);
    currDir.create("d3", Path.Type.D);
    currDir.create("d2/d4", Path.Type.D);
    currDir.create("d3/f1", Path.Type.F);
    File f1 = (File) currDir.search("d3/f1");
    f1.setContent("file 1 text");
    currDir.create("f2", Path.Type.F);
    File f2 = (File) currDir.search("f2");
    f2.setContent("file 2 text");
    currDir.create("d1/f3", Path.Type.F);
    File f3 = (File) currDir.search("d1/f3");
    f3.setContent("file 3 text");
  }

  /**
   * Prints the contents of a single file.
   */
  @Test
  public void catOneFile() {
    String[] args = {"cat", "d3/f1", ">", "test"};
    cat.run(args, currDir, redirectionArgs);
    String expected = "file 1 text";
    File test = (File) currDir.search("test");

    assertEquals(expected, test.getContent());
  }

  /**
   * Prints the contents of multiple files.
   */
  @Test
  public void catMultipleFiles() {
    String[] args = {"cat", "d3/f1", "f2", "d1/f3", ">", "test"};
    cat.run(args, currDir, redirectionArgs);
    String expected = "file 1 text\n\n\n\nfile 2 text\n\n\n\nfile 3 text";
    File test = (File) currDir.search("test");

    assertEquals(expected, test.getContent());
  }

  /**
   * Does not print out the file contents of any file since the first file is an invalid path.
   */
  @Test
  public void catFirstArgInvalid() {
    String[] args = {"cat", "DoesNotExist", "f2", "d1/f3", ">", "test"};
    System.out.println("catFirstArgInvalid-------");
    System.out.println("Expected:\n\n\ncat: DoesNotExist is not an existing path");
    System.out.println("Actual:");
    cat.run(args, currDir, redirectionArgs);
    System.out.println("-------");
    File test = (File) currDir.search("test");

    assertEquals("", test.getContent());
  }

  /**
   * Only prints the file contents of the files before the first invalid path argument.
   */
  @Test
  public void catMiddleArgInvalid() {
    String[] args = {"cat", "d3/f1", "DoesNotExist", "d1/f3", ">", "test"};
    System.out.println("catMiddleArgInvalid-------");
    System.out.println("Expected:\n\n\ncat: DoesNotExist is not an existing path");
    System.out.println("Actual:");
    cat.run(args, currDir, redirectionArgs);
    System.out.println("-------");
    String expected = "file 1 text";
    File test = (File) currDir.search("test");

    assertEquals(expected, test.getContent());
  }

  /**
   * Clean up @Before variables.
   * 
   * @throws Exception An exception.
   */
  @AfterClass
  public static void tearDown() throws Exception {
    fs = null;
    currDir = null;
  }
}
