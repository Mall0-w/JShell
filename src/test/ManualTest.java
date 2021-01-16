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
import commands.Manual;
import entity.Directory;
import entity.File;
import entity.FileSystem;

public class ManualTest {
  FileSystem fs;
  Directory dir;
  Manual man;

  @Before
  public void setUp() {
    fs = FileSystem.getInstance();
    dir = fs.getRoot();
    man = new Manual();
  }

  @Test
  public void testMan1() {
    // testing to see if manual returns correct output
    String expected = "man CMD [> OUTFILE] \n" + "If OUTFILE is not given, "
        + "prints documentation for CMD.  Otherwise, set the contents of OUTFILE "
        + "to the documentation for CMD;\nif OUTFILE doesn't exist, a new file will"
        + " be created\n\n" + "man CMD >> OUTFILE\n"
        + "Appends the documentation for CMD to OUTFILE.  If OUTFILE doesn't exist, "
        + "a new file will be created";;
    String args = "man";
    String[] redArgs = {"", ""};
    assertEquals(expected, man.man("man", dir, redArgs));
  }

  @Test
  public void testMan2() {
    // testing to see if manual writes to files
    String expected = "man CMD [> OUTFILE] \n" + "If OUTFILE is not given, "
        + "prints documentation for CMD.  Otherwise, set the contents of OUTFILE "
        + "to the documentation for CMD;\nif OUTFILE doesn't exist, a new file will"
        + " be created\n\n" + "man CMD >> OUTFILE\n"
        + "Appends the documentation for CMD to OUTFILE.  If OUTFILE doesn't exist, "
        + "a new file will be created";
    String args = "man";
    String[] redArgs = {">", "f1"};
    man.man("man", dir, redArgs);
    assertEquals(expected, ((File) dir.getChilds().get("f1")).getContent());
  }

  @Test
  public void testRun() {
    // testing to see if man mutates directory
    String expected = "man CMD [CMD2 ...] \n" + "Prints documentation for " + "CMD(s).";
    String[] args = {"man", "man"};
    String[] redArgs = {"", ""};
    assertEquals(dir, man.run(args, dir, redArgs));
  }

}
