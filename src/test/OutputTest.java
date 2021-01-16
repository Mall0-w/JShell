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
import io.Output;
import entity.*;

/**
 * Unit tests for the Output base class.
 */
public class OutputTest {
  static FileSystem fs = FileSystem.getInstance();
  static Directory currDir = fs.getRoot();
  Output out = new Output();

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
   * Returns the number of arguments in an array of parsed input.
   */
  @Test
  public void numberOfArgs() {
    String[] args = {"cp", "f1", "d1/d5"};

    assertEquals(2, out.numberOfArgs(args));
  }

  /**
   * Outputs a String of text output to the shell.
   */
  @Test
  public void stdOutputToShell() {
    System.out.println("stdOutputToShell-------");
    System.out.println("Expected:\ntest output");
    System.out.println("Actual: ");
    Output.stdOutput("test output", "", "", currDir, "example command");
    System.out.println("-------");
  }

  /**
   * Outputs an error message saying that output redirection is not supported.
   */
  @Test
  public void stdOutputRedirectionImpossible() {
    System.out.println("stdOutputRedirectionImpossible-------");
    System.out
        .println("Expected:\nexample command: This command does not support output redirection");
    System.out.println("Actual: ");

    Output.stdOutput("test output", "error", "f1", currDir, "example command");
    System.out.println("-------");
  }

  /**
   * Redirects a String of text output to a file with the > operation
   */
  @Test
  public void stdOutputRedirectOverwrite() {
    Output.stdOutput("test output", ">", "f3", currDir, "example command");
    File f3 = (File) currDir.search("f3");
    String expected = "test output";

    assertEquals(expected, f3.getContent());
  }

  /**
   * Outputs an error message to a StringBuilder.
   */
  @Test
  public void stdErrorToStringBuilder() {
    StringBuilder sb = new StringBuilder();
    Output.stdError("Error message here", sb);

    assertEquals("Error message here", sb.toString());
  }


  /**
   * Overwrites a file with a String.
   */
  @Test
  public void fileSetOutput() {
    File f1 = (File) currDir.search("d3/f1");
    Output.fileSetOutput(f1, "PogU");
    String expected = "PogU";

    assertEquals(expected, f1.getContent());
  }

  /**
   * Appends a String to a file.
   */
  @Test
  public void fileAppendOutput() {
    File f1 = (File) currDir.search("d3/f1");
    f1.setContent("file 1 text");
    Output.fileAppendOutput(f1, " PogU");
    String expected = "file 1 text PogU";

    assertEquals(expected, f1.getContent());
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
