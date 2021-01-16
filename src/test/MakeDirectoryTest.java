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
import commands.MakeDirectory;
import entity.Directory;

/**
 * Unit tests for the mkdir command.
 */
public class MakeDirectoryTest {
  Directory dir;
  MakeDirectory mkdir;
  String redirectionArgs[];

  @Before
  public void setUp() {
    dir = new Directory();
    mkdir = new MakeDirectory();
    redirectionArgs = new String[] {"", ""};
  }

  @Test
  public void testRun1() {
    // adding a single element
    String[] expected = {"d1"};
    String[] args = {"mkdir", "d1"};
    assertEquals(expected, mkdir.run(args, dir, redirectionArgs).getChildNames().toArray());
  }

  @Test
  public void testRun2() {
    // adding multiple elements at once
    String[] expected = {"d1", "d2", "d3"};
    String[] args = {"mkdir", "d1", "d2", "d3"};
    assertEquals(expected, mkdir.run(args, dir, redirectionArgs).getChildNames().toArray());
  }

  @Test
  public void testRun3() {
    // adding a nested path
    String[] expected = {"d2"};
    String[] args = {"mkdir", "d1", "d1/d2"};
    assertEquals(expected, ((Directory) mkdir.run(args, dir, redirectionArgs).getChilds().get("d1"))
        .getChildNames().toArray());
  }

  @Test
  public void testRun4() {
    // attempting to create an invalid directory
    String[] expected = {"d1"};
    String[] args = {"mkdir", "d1", "$$$$"};
    assertEquals(expected, mkdir.run(args, dir, redirectionArgs).getChildNames().toArray());
  }
}
