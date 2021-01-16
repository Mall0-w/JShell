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
import commands.ClientURL;
import entity.Directory;
import entity.File;
import entity.FileSystem;

public class ClientURLTest {
  FileSystem fs;
  Directory currDir;
  ClientURL curl;

  @Before
  public void setUp() {
    curl = new ClientURL();
    fs = FileSystem.getInstance();
    currDir = fs.getRoot();
  }

  @Test
  public void testRun() {
    // testing to see if grabs file
    String[] args = {"curl", "http://www.cs.cmu.edu/~spok/grimmtmp/073.txt"};
    String[] redArgs = {"", ""};
    String expected1 = "There was once a king who had an illness, and no one believed that he";
    String expected2 = "as long as they lived.";
    // check to make sure currDir isn't changed
    assertEquals(currDir, curl.run(args, currDir, redArgs));
    // check to make sure file is created
    assertTrue(currDir.getChilds().containsKey("073.txt"));
    String result = ((File) currDir.getChilds().get("073.txt")).getContent();
    // check to make sure file has proper contents
    assertTrue(result.contains(expected1) && result.contains(expected2));
  }

}
