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

/**
 * Unit tests for the pushd command.
 */
import static org.junit.Assert.*;
import java.util.Stack;
import org.junit.Before;
import org.junit.Test;
import commands.PushDirectory;
import entity.Directory;
import entity.Path;

public class PushDirectoryTest {
  PushDirectory pushd;
  Directory currDir;
  String[] redirectionArgs;

  @Before
  public void setUp() {
    currDir = new Directory();
    pushd = new PushDirectory();
    redirectionArgs = new String[] {"", ""};
  }

  @Test
  public void testRun1() {
    // pushing a directory
    currDir.create("d1", Path.Type.D);
    String[] args = {"pushd", "d1"};
    Directory temp = (Directory) currDir.getChilds().get("d1");
    assertEquals(temp, pushd.run(args, currDir, redirectionArgs));
    assertEquals(true, pushd.getDirStack().getStack().contains(currDir));
  }

  @Test
  public void testRun2() {
    // pushing a directory that does not exist
    String[] args = {"pushd", "d1"};
    assertEquals(currDir, pushd.run(args, currDir, redirectionArgs));
    assertEquals(false, pushd.getDirStack().getStack().contains(currDir));
  }

}
