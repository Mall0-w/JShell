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
import org.junit.Before;
import org.junit.Test;
import commands.Move;
import entity.*;

public class MoveTest {
  FileSystem fs;
  Directory currDir;
  Move mv;
  String redirectionArgs[];

  /**
   * Set up mock file system and command objects for testing.
   */
  @Before
  public void setUp() {
    fs = FileSystem.getInstance();
    currDir = fs.getRoot();
    mv = new Move();
    redirectionArgs = new String[] {"", ""};

    if (currDir.search("d1") == null) {
      currDir.create("d1", Path.Type.D);
    }
    if (currDir.search("d2") == null) {
      currDir.create("d2", Path.Type.D);
    }
    if (currDir.search("d3") == null) {
      currDir.create("d3", Path.Type.D);
    }
    if (currDir.search("d2/d4") == null) {
      currDir.create("d2/d4", Path.Type.D);
    }
    if (currDir.search("d3/f1") == null) {
      currDir.create("d3/f1", Path.Type.F);
    }
    if (currDir.search("f2") == null) {
      currDir.create("f2", Path.Type.F);
    }

    File f1 = (File) currDir.search("d3/f1");
    f1.setContent("file 1 text");
    File f2 = (File) currDir.search("f2");
    f2.setContent("file 2 text");
  }

  /**
   * Moves a file to an existing directory.
   */
  @Test
  public void moveFileToDirectory() {
    String[] args = {"mv", "d3/f1", "d1"};
    mv.run(args, currDir, redirectionArgs);
    File f1 = (File) currDir.search("d1/f1");
    String expected = "file 1 text";
    File dne = (File) currDir.search("d3/f1");

    assertEquals(expected, f1.getContent());
    assertEquals(null, dne);
  }

  /**
   * Moves a file to another file and overwrites the destination file with the contents of the
   * source file.
   */
  @Test
  public void moveFileToFile() {
    String[] args = {"mv", "d3/f1", "f2"};
    mv.run(args, currDir, redirectionArgs);
    File f2 = (File) currDir.search("f2");
    String expected = "file 1 text";
    File dne = (File) currDir.search("d3/f1");

    assertEquals(expected, f2.getContent());
    assertEquals(null, dne);
  }

  /**
   * Recursively move a directory and its contents to be under another existing directory.
   */
  @Test
  public void moveDirectoryToExistingDirectory() {
    String[] args = {"mv", "d3", "d2/d4"};
    mv.run(args, currDir, redirectionArgs);
    Directory d3Old = (Directory) currDir.search("d3");
    Directory d3New = (Directory) currDir.search("d2/d4/d3");
    File f1 = (File) currDir.search("d2/d4/d3/f1");
    String expected = "file 1 text";

    assertEquals(null, d3Old);
    assertNotEquals(null, d3New);
    assertEquals(expected, f1.getContent());
  }

  /**
   * Rename the directory.
   */
  @Test
  public void renameDirectory() {
    String[] args = {"mv", "d3", "d5/"};
    mv.run(args, currDir, redirectionArgs);
    Directory d3Old = (Directory) currDir.search("d3");
    Directory d3New = (Directory) currDir.search("d5");
    File f1 = (File) currDir.search("d5/f1");
    String expected = "file 1 text";

    assertEquals(null, d3Old);
    assertNotEquals(null, d3New);
    assertEquals(expected, f1.getContent());
  }

  /**
   * Does not move a directory to a file.
   */
  @Test
  public void moveDirectoryToFile() {
    String[] args = {"mv", "d3", "f2"};
    System.out.println("moveDirectoryToFile-------");
    System.out.println("Expected:\nmv: Cannot copy a directory to a file.");
    System.out.println("Actual:");
    mv.run(args, currDir, redirectionArgs);
    System.out.println("-------");
    Directory d3 = (Directory) currDir.search("d3");
    File f1 = (File) currDir.search("d3/f1");
    File f2 = (File) currDir.search("f2");
    String expected = "file 1 text";
    String expected2 = "file 2 text";

    assertNotEquals(null, d3);
    assertEquals(expected, f1.getContent());
    assertEquals(expected2, f2.getContent());
  }

  /**
   * Does not move a non-existent path.
   */
  @Test
  public void moveNonExistentPath() {
    String[] args = {"mv", "DoesnotExist", "d1"};
    System.out.println("moveNonExistentPath-------");
    System.out.println("Expected:\nmv: DoesnotExist is a source path that does not exist.");
    System.out.println("Actual:");
    mv.run(args, currDir, redirectionArgs);
    System.out.println("-------");
    File dne = (File) currDir.search("d1/DoesNotExist");

    assertEquals(null, dne);
  }

  /**
   * Does not move a directory to any of its children directories.
   */
  @Test
  public void moveDirectoryToChildDirectory() {
    String[] args = {"mv", "d2", "d2/d4"};
    System.out.println("moveDirectoryToChildDirectory-------");
    System.out.println(
        "Expected:\nmv: Destination directory cannot be a child of the source directory or "
            + "itself.");
    System.out.println("Actual:");
    mv.run(args, currDir, redirectionArgs);
    System.out.println("-------");
    File dne = (File) currDir.search("d2/d4/d2");
    Directory d2 = (Directory) currDir.search("d2");
    Directory d4 = (Directory) currDir.search("d2/d4");

    assertEquals(null, dne);
    assertNotEquals(null, d2);
    assertNotEquals(null, d4);
  }
}
