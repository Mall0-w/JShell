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
import org.junit.Test;
import entity.Path;

/**
 * Unit tests for method Path.getParentPathString(String path).
 */
public class GetParentPathStringTest {
  /**
   * Parent path is root
   */
  @Test
  public void testRun1() {
    String path = "test";
    assertEquals(Path.getParentPathString(path), "");
  }

  /**
   * Parent path is not root
   */
  @Test
  public void testRun2() {
    String path = "test/test2/test3";
    assertEquals(Path.getParentPathString(path), "test/test2");
  }
}
