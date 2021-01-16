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
import commands.ChangeDirectory;
import entity.Directory;
import entity.FileSystem;
import entity.Path;

public class ChangeDirectoryTest {
  Directory currDir;
  FileSystem fs;
  ChangeDirectory cd;

  @Before
  public void setUp() {
    fs = FileSystem.getInstance();
    currDir = fs.getRoot();
    cd = new ChangeDirectory();
  }

  @Test
  public void testCdLocal() {
    // testing switching to a local directory
    currDir.create("test1", Path.Type.D);
    assertEquals(currDir.getChilds().get("test1"), cd.cd("test1", currDir));
  }

  @Test
  public void testCdAbsolute() {
    // testing switching to an absolute directory
    currDir.create("d1", Path.Type.D);
    ((Directory) currDir.getChilds().get("d1")).create("d2", Path.Type.D);
    Directory expected =
        (Directory) ((Directory) currDir.getChilds().get("d1")).getChilds().get("d2");
    assertEquals(expected, cd.cd("d1/d2", currDir));
  }

  @Test
  public void testCdFile() {
    // testing switching to a File (should return currDir)
    currDir.create("f1", Path.Type.F);
    assertEquals(currDir, cd.cd("f1", currDir));
  }

  @Test
  public void testCdMissing() {
    // testing switching to a non-existing path (should return currDir)
    assertEquals(currDir, cd.cd("x", currDir));
  }

  @Test
  public void testRun() {
    // making sure cd will mutate
    String[] args = {"cd", "dir_x"};
    String[] redArgs = {"", ""};
    currDir.create("dir_x", Path.Type.D);
    Directory expected = (Directory) currDir.getChilds().get("dir_x");
    assertEquals(expected, cd.run(args, currDir, redArgs));
  }

}
