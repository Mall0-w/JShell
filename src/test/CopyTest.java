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
import commands.Copy;
import entity.*;

/**
 * Unit tests for the cp command.
 */
public class CopyTest {
  static FileSystem fs = FileSystem.getInstance();
  static Directory currDir = fs.getRoot();
  String redirectionArgs[] = {"", ""};
  Copy cp = new Copy();

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
  }

  /**
   * Copies a file to an existing directory.
   */
  @Test
  public void copyFileToDirectory() {
    String[] args = {"cp", "d3/f1", "d1"};
    cp.run(args, currDir, redirectionArgs);
    File f1 = (File) currDir.search("d1/f1");
    String expected = "file 1 text";

    assertEquals(expected, f1.getContent());
  }

  /**
   * Copies a file to another file by overwriting the destination file with the contents of the
   * source file.
   */
  @Test
  public void copyFileToFile() {
    String[] args = {"cp", "d3/f1", "f2"};
    cp.run(args, currDir, redirectionArgs);
    File f2 = (File) currDir.search("f2");
    String expected = "file 1 text";

    assertEquals(expected, f2.getContent());
  }

  /**
   * Recursively copy a directory and its contents to be under another directory.
   */
  @Test
  public void copyDirectoryToDirectory() {
    String[] args = {"cp", "d3", "d2/d4"};
    cp.run(args, currDir, redirectionArgs);
    Directory d3Old = (Directory) currDir.search("d3");
    Directory d3New = (Directory) currDir.search("d2/d4/d3");
    File f1 = (File) currDir.search("d2/d4/d3/f1");
    String expected = "file 1 text";

    assertEquals(d3Old.getChildNames(), d3New.getChildNames());
    assertEquals(expected, f1.getContent());
  }

  /**
   * Does not copy a directory to a file. Outputs an error message.
   */
  @Test
  public void copyDirectoryToFile() {
    String[] args = {"cp", "d3", "f2"};
    System.out.println("copyDirectoryToFile-------");
    System.out.println("Expected:\ncp: Cannot copy a directory to a file.");
    System.out.println("Actual:");
    cp.run(args, currDir, redirectionArgs);
    System.out.println("-------");
    File f2 = (File) currDir.search("f2");
    String expected = "file 2 text";

    assertEquals(expected, f2.getContent());
  }

  /**
   * Does not copy a non-existent path. Outputs an error message.
   */
  @Test
  public void copyNonExistentPath() {
    String[] args = {"cp", "DoesnotExist", "d1"};
    System.out.println("copyNonExistentPath-------");
    System.out.println("Expected:\ncp: DoesnotExist is a source path that does not exist.");
    System.out.println("Actual:");
    cp.run(args, currDir, redirectionArgs);
    System.out.println("-------");
    File dne = (File) currDir.search("d1/DoesNotExist");

    assertEquals(null, dne);
  }

  /**
   * Does not copy a directory to any of its children directories. Outputs an error message.
   */
  @Test
  public void copyDirectoryToChildDirectory() {
    String[] args = {"cp", "d2", "d2/d4"};
    System.out.println("copyDirectoryToChildDirectory-------");
    System.out
        .println("Expected:\ncp:Destination directory cannot be a child of the source directory "
            + "or itself.");
    System.out.println("Actual:");
    cp.run(args, currDir, redirectionArgs);
    System.out.println("-------");
    File dne = (File) currDir.search("d2/d4/d2");

    assertEquals(null, dne);
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
