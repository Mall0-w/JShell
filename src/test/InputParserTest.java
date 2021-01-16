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

import commands.Tree;
import io.InputParser;
import org.junit.Test;
import static org.junit.Assert.*;

public class InputParserTest {

  InputParser ip = new InputParser();

  /**
   * Tests getRedirectionArgs() for correct array of redirection arguments returned.
   */
  @Test
  public void getRedirectionArgs() {
    String[] expected = new String[2];
    expected[0] = ">";
    expected[1] = "Hello";
    String[] args1 = {"foo", "bar", ">", "Hello"};
    assertArrayEquals(expected, ip.getRedirectionArgs(args1, new Tree()));
    expected[0] = "";
    expected[1] = "";
    String[] args2 = {"foo", "bar", ">>"};
    assertEquals(expected, ip.getRedirectionArgs(args2, new Tree()));
    expected[0] = "";
    expected[1] = "";
    String[] args3 = {"foo", "bar"};
    assertArrayEquals(expected, ip.getRedirectionArgs(args3, new Tree()));
  }

  /**
   * Tests parseToArray() for correct parsing of user input line.
   */
  @Test
  public void parseToArray() {
    String input = "cp file1 file2 file3 >> test";
    String expected[] = {"cp", "file1", "file2", "file3", ">>", "test"};

    assertArrayEquals(expected, ip.parseToArray(input));
  }
}
