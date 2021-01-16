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
package io;

import interfaces.Command;

/**
 * This class handles any input validation that occurs for all valid commands.
 */
public class InputValidater {

  // -1 means minimum zero arguments given
  // -2 means minimum one argument given
  // 0 means no arguments given
  // >0 means (positive) number of arguments needed

  /**
   * Returns the number of arguments given in the parsed input line. Output redirection arguments
   * are excluded in the count.
   * 
   * @param parsed The parsed line of user input.
   * @param expectedNumberOfArgs The number of mandatory arguments for a command.
   * @return The number of arguments, excluding any output redirection arguments.
   */
  private int countNumberOfArgs(String[] parsed, int expectedNumOfArgs) {
    int numberOfArgs = parsed.length - 1;
    for (int i = 0; i < parsed.length; i++) {
      if (parsed[i].equals(">") || parsed[i].equals(">>")) {
        if (numberOfArgs - expectedNumOfArgs == 2) {
          return numberOfArgs - 2;
        }
        break;
      }
    }

    return numberOfArgs;
  }

  /**
   * Returns a boolean value specifying if the given command exists and the number of arguments
   * given for a command is appropriate.
   * 
   * @param line A line of parsed user input.
   * @return True if the commands exists and has a valid number of arguments, and false otherwise.
   */
  public boolean validNumberOfArguments(Command command, String[] parsed) {

    int expectedNumOfArgs = command.expectedNumOfArgs();

    // Already verified that firstCommand is a valid command, no need to check it


    // If expectedNumOfArgs has a positive number, then that EXACT number of args is needed
    if (expectedNumOfArgs >= 0) {
      // Contains exactly enough args
      return (expectedNumOfArgs == countNumberOfArgs(parsed, expectedNumOfArgs));
    }
    // Otherwise if it is negative, then can have optional number of arguments.
    else if (expectedNumOfArgs < 0) {
      // Contains minimum of either 0 or 1 args, could be more.
      return (parsed.length - 1 >= (expectedNumOfArgs * -1 - 1));
    }

    return false;
  }


}
