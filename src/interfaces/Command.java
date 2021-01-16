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
 * An interface that includes the basic structure of a command.
 */
public interface Command {
  /**
   * Outputs valid command output (no error messages) to either the shell or a specified file.
   * 
   * @param text the text that is supposed to be put in terminal
   * @param type The redirection symbol, if it exists.
   * @param path The destination file to redirect command output to.
   * @param currDir The current directory of the shell.
   * @param cmd The valid command generating the output.
   */
  public void output(String text, String type, String path, Directory currDir, String cmd);

  /**
   * Prints a description of the command. Used for the manual command.
   * 
   */
  public String printDescription();

  /**
   * Runs a command.
   * 
   * @param args the arguments for a given command, including the command itself
   * @param currDir the current working directory at time of command
   * @param redirectionArgs the redirection symbol and destination file
   * @return the directory that should be switched to after running a command
   */
  public Directory run(String args[], Directory currDir, String[] redirectionArgs);

  /**
   * Returns the number of expected arguments for the given command. Negative numbers mean optional
   * arguments, and positive numbers means mandatory arguments
   *
   * -1: Minimum of 0 arguments needed, any more is optional -2: Minimum of 1 arguments needed, any
   * more is optional 0: Exactly no arguments are expected and needed >0: Exactly n arguments are
   * needed. No more, no less
   *
   * @return expected number of arguments for the given command
   */
  public int expectedNumOfArgs();

}
