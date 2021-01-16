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
import entity.Directory;
import entity.File;
import entity.FileSystem;
import entity.Path;
import io.OutputRedirection;

/**
 * Unit tests for the output redirection class.
 */
public class OutputRedirectionTest {
  static FileSystem fs = FileSystem.getInstance();
  static Directory currDir = fs.getRoot();

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
   * Guarantee a space before and after the redirect symbol >.
   */
  @Test
  public void redirectSymbolAddSpace() {
    assertEquals(" > ", OutputRedirection.redirectSymbolParse(">"));
  }

  /**
   * Remove the space character before and after the redirect symbol >> if it exists.
   */
  @Test
  public void redirectSymbolRemoveWhiteSpace() {
    assertEquals(">>", OutputRedirection.redirectSymbolInStringParse(" >> "));
  }

  // NOTE: Successful > and >> operations for outputRedirect() method already tested in
  // EchoTest.java unit tests so no need to test again in this test file.
  // The output redirection functionality in echo uses the code in OutputRedirection via calling
  // these static methods.

  /**
   * Does not do the output redirection operation if path has a double slash //.
   */
  @Test
  public void redirectWithDoubleSlashPath() {
    System.out.println("-------");
    System.out.println("Expected:\nSystem: Invalid path");
    System.out.println("Actual:");
    OutputRedirection.outputRedirect(">", "test text", "d2//f1", currDir, "echo");
    System.out.println("-------");
  }

  /**
   * Does not do output redirection if the destination path is a directory.
   */
  @Test
  public void redirectToDirectory() {
    System.out.println("-------");
    System.out.println("Expected:\necho: Cannot write a string into a directory");
    System.out.println("Actual:");
    OutputRedirection.outputRedirect(">", "test text", "d2", currDir, "echo");
    System.out.println("-------");
  }

  /**
   * Does not redirect to a new file under a non-existent directory.
   */
  @Test
  public void redirectToNewDirectory() {
    System.out.println("-------");
    System.out.println("Expected:\ncat: Cannot create a file under non-existent directory(s)");
    System.out.println("Actual:");
    OutputRedirection.outputRedirect(">>", "test text", "d69/f1", currDir, "cat");
    System.out.println("-------");
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
