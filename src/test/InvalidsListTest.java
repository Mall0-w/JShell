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
import commands.invalidCharList.InvalidsList;

public class InvalidsListTest {

  @Test
  public void testIsInvalid() {
    String valid = "Thisisvalid";
    String invalid = "Th^i$..n*tval-id";
    assertTrue(InvalidsList.isInvalid(valid) == null);
    assertTrue(InvalidsList.isInvalid(invalid) != null);
  }

  @Test
  public void testBadExtension() {
    String valid = "valid.txt";
    String invalid = "bad.";
    assertTrue(InvalidsList.badExtension(invalid));
    assertFalse(InvalidsList.badExtension(valid));

  }
}
