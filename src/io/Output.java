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

import entity.Directory;
import entity.File;

public class Output {

  /**
   * Returns the number of arguments in the parsed input. For debugging purposes.
   * 
   * @param parsedInput The used input parsed into a String array.
   * @return The number of arguments.
   */
  public int numberOfArgs(String[] parsedInput) {
    return parsedInput.length - 1;
  }


  // MARK: Public methods
  /**
   * Outputs a line of text to the shell or file via redirection.
   * 
   * @param line A String of text.
   * @param type The redirection symbol, if it exists.
   * @param path The destination file to redirect command output to.
   * @param currDir The current directory of the shell.
   * @param cmd The valid command generating the output.
   */
  public static void stdOutput(String line, String type, String path, Directory currDir,
      String cmd) {
    if (type == null || type.equals("")) {
      System.out.println(line);
    } else if (type.equals("error")) {
      System.out.println(cmd + ": This command does not support output redirection");
    } else {
      OutputRedirection.outputRedirect(type, line, path, currDir, cmd);
    }
  }

  /**
   * Outputs an error message to the shell.
   * 
   * @param line A String error message.
   */
  public static void stdError(String line) {
    System.out.println(line);
  }

  /**
   * Outputs a user-delayed error message.
   * 
   * @param line A String of text.
   * @param sb The error message.
   */
  public static void stdError(String line, StringBuilder sb) {
    sb.append(line);
  }

  /**
   * Overwrites the file contents with text.
   * 
   * @param f A file.
   * @param text The new text.
   */
  public static void fileSetOutput(File f, String text) {
    f.setContent(text);
  }

  /**
   * Appends to the file contents with text.
   * 
   * @param f A file.
   * @param text The new text.
   */
  public static void fileAppendOutput(File f, String text) {
    f.appendContent(text);
  }
}
