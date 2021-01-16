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
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import MockObjects.MockDirectory;
import commands.RemoveDirectory;
import entity.Directory;
import entity.Path;

/**
 * Unit tests for the rm command.
 */
public class RemoveDirectoryTest {
  MockDirectory test;
  RemoveDirectory rm;
  String[] redirectionArgs;

  @Before
  public void setUp() {
    test = new MockDirectory();
    rm = new RemoveDirectory();
    redirectionArgs = new String[] {"", ""};
    String[] children = {"d1", "d2"};
    test.setChildDirectories(children);
  }

  @Test
  public void testRun1() {
    // testing removing a single directory with no children
    String[] args = {"rm", "d1"};
    String[] expected = {"d2"};
    assertEquals(rm.run(args, test, redirectionArgs).getChildNames().toArray(), expected);
  }

  @Test
  public void testRun2() {
    // testing removing a directory that doesn't exist
    String[] args = {"rm", "d8"};
    String[] expected = {"d1", "d2"};
    assertEquals(rm.run(args, test, redirectionArgs).getChildNames().toArray(), expected);
  }

  @Test
  public void testRun3() {
    // testing removing current directory
    String[] args = {"rm", "."};
    String[] expected = {"d1", "d2"};
    assertEquals(rm.run(args, test, redirectionArgs).getChildNames().toArray(), expected);
  }

  @Test
  public void testRun4() {
    // testing removing a with children
    String[] newchildren = {"test1", "test2"};
    Directory temp = ((Directory) test.getChilds().get("d2"));
    temp.create(newchildren[0], Path.Type.D);
    temp.create(newchildren[1], Path.Type.D);
    String[] args = {"rm", "d2"};
    String[] expected = {"d1"};
    assertEquals(rm.run(args, test, redirectionArgs).getChildNames().toArray(), expected);
  }

  @Test
  public void testRun5() {
    // testing removing a with child that isn't directly related
    String[] newchildren = {"test1", "test2"};
    ((Directory) test.getChilds().get("d2")).create(newchildren[0], Path.Type.D);
    ((Directory) test.getChilds().get("d2")).create(newchildren[1], Path.Type.D);
    String[] expectedSub = {"test1"};
    String[] expectedMain = {"d1", "d2"};
    String[] args = {"rm", "d2/test2"};
    assertEquals(expectedSub,
        ((Directory) rm.run(args, test, redirectionArgs).getChilds().get("d2")).getChildNames()
            .toArray());
    assertEquals(expectedMain, test.getChildNames().toArray());
  }

}
