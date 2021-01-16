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
package interfaces;

import entity.Directory;

/**
 * An interface that includes the basic structure for command error handling.
 */
public interface ErrorHandler {

  /**
   * Displays encountered error to console
   * 
   * @param code the error code for an encountered error
   * @param arg the argument involved in an encountered error
   */
  public void displayError(int code, String arg);

  /**
   * checks to see if command input is valid
   * 
   * @param code number for a given input test
   * @param arg argument to be tested
   * @param currDir current working directory at time of test
   * @return true if arg is valid
   */
  public boolean checkInput(int code, String arg, Directory currDir);

}
