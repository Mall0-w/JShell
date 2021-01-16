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
import commands.PrintWorkingDirectory;
import entity.Directory;
import entity.File;
import entity.FileSystem;
import entity.Path;

public class PrintWorkingDirectoryTest {
  Directory currDir;
  FileSystem fs;
  PrintWorkingDirectory pwd;

  @Before
  public void setUp() {
    fs = FileSystem.getInstance();
    currDir = fs.getRoot();
    pwd = new PrintWorkingDirectory();

  }


  @Test
  public void testPwd1() {
    // testing for root directory
    String[] redArg = {"", ""};
    assertEquals("/", pwd.pwd(currDir, redArg));
  }

  @Test
  public void testPwd2() {
    // testing for root directory
    String[] redArg = {"", ""};
    currDir.create("d1", Path.Type.D);
    Directory test = (Directory) currDir.getChilds().get("d1");
    assertEquals("/d1", pwd.pwd(test, redArg));
  }

  @Test
  public void testPwd3() {
    // testing to see if pwd works with redirection and file does not exist
    String[] redArg = {">>", "f1"};
    String[] args = {"pwd"};
    pwd.run(args, currDir, redArg);
    assertEquals("/", ((File) currDir.getChilds().get("f1")).getContent());
  }

  @Test
  public void testPwd4() {
    // testing to see if pwd works with redirection and file does not exist
    currDir.create("f2", Path.Type.F);
    ((File) currDir.getChilds().get("f2")).setContent("Testing");
    String[] redArg = {">>", "f2"};
    String[] args = {"pwd"};
    pwd.run(args, currDir, redArg);
    assertEquals("Testing\n/", ((File) currDir.getChilds().get("f2")).getContent());
  }

  @Test
  public void testRun() {
    // testing to see if pwd mutates directory
    String[] redArg = {"", ""};
    pwd.pwd(currDir, redArg);
    String[] args = {"pwd"};
    assertEquals(currDir, pwd.run(args, currDir, redArg));
  }

}
