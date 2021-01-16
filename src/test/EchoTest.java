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
import commands.Echo;
import entity.*;

/**
 * Unit tests for the echo command and by extension the OutputRedirection class.
 */
public class EchoTest {
  static FileSystem fs = FileSystem.getInstance();
  static Directory currDir = fs.getRoot();
  Echo echo = new Echo();

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
    f1.setContent("old text!");
  }

  /**
   * Prints a string to shell output.
   */
  @Test
  public void printToShell() {
    assertEquals("poggers", echo.echoPrint("poggers", currDir));
  }

  /**
   * Writes a string to new file with overwrite (>).
   */
  @Test
  public void writeToNewFileOverwrite() {
    String[] args = {"echo", "\"kekw omegalul\"", ">", "f2"};
    String[] redirectionArgs = {">", "f2"};
    echo.run(args, currDir, redirectionArgs);
    File f2 = (File) currDir.search("f2");

    assertEquals("kekw omegalul", f2.getContent());
  }

  /**
   * Overwrites an existing file's contents with a string.
   */
  @Test
  public void overwriteFile() {
    String[] args = {"echo", "\"forsenCD CHEATING forsenCD\"", ">", "d3/f1"};
    String[] redirectionArgs = {">", "d3/f1"};
    echo.run(args, currDir, redirectionArgs);
    File f1 = (File) currDir.search("d3/f1");

    assertEquals("forsenCD CHEATING forsenCD", f1.getContent());
  }

  /**
   * Writes a string to new file with append (>>).
   */
  @Test
  public void writeToNewFileAppend() {
    String[] args = {"echo", "\"This is very Sadge.\"", ">>", "d2/d4/f3"};
    String[] redirectionArgs = {">>", "d2/d4/f3"};
    echo.run(args, currDir, redirectionArgs);
    File f3 = (File) currDir.search("d2/d4/f3");

    assertEquals("This is very Sadge.", f3.getContent());
  }

  /**
   * Appends a string to existing file.
   */
  @Test
  public void appendToFile() {
    String[] args1 = {"echo", "\"forsenCD CHEATING forsenCD\"", ">", "d3/f1"};
    String[] redirectionArgs1 = {">", "d3/f1"};
    echo.run(args1, currDir, redirectionArgs1);
    String[] args2 = {"echo", "\"forsenCD CHEATING forsenCD\"", ">>", "d3/f1"};
    String[] redirectionArgs2 = {">>", "d3/f1"};
    echo.run(args2, currDir, redirectionArgs2);
    File f1 = (File) currDir.search("d3/f1");

    assertEquals("forsenCD CHEATING forsenCD\nforsenCD CHEATING forsenCD", f1.getContent());
  }

  /**
   * Does not write a string that is not surrounded by double quotes to the shell.
   */
  @Test
  public void writeUnquotedStringToShell() {
    String[] args = {"echo", "forsenCD CHEATING forsenCD", ">", "f4"};
    String[] redirectionArgs = {">", "f4"};
    System.out.println("writeUnquotedStringToShell-------");
    System.out.println("Expected:\necho: Given text string is not surrounded by double quotes");
    System.out.println("Actual:");
    echo.run(args, currDir, redirectionArgs);
    System.out.println("-------");
    File f4 = (File) currDir.search("f4");

    assertEquals(null, f4);
  }

  /**
   * Does not do echo operation when a redirection symbol other than > or >> is provided.
   */
  @Test
  public void redirectWithUnknownSymbol() {
    String[] args = {"echo", "\"\nforsenCD CHEATING forsenCD\"", "?", "f4"};
    String[] redirectionArgs = {"", ""};
    System.out.println("redirectWithUnknownSymbol-------");
    System.out.println("Expected:\necho: Invalid symbol was provided. Must be \">\" or \">>\"");
    System.out.println("Actual:");
    echo.run(args, currDir, redirectionArgs);
    System.out.println("-------");
    File f4 = (File) currDir.search("f4");

    assertEquals(null, f4);
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
