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
package commands;

import java.util.ArrayList;
import java.util.Arrays;
import entity.Directory;
import entity.Path;
import interfaces.Command;
import interfaces.ErrorHandler;
import io.Output;

/**
 * This class implements the mkdir comamnd.
 */
public class MakeDirectory implements Command, ErrorHandler {
  /**
   * args holds parsed user input
   */
  protected String args[];

  /**
   * Adds all invalid directory names to an ArrayList.
   */
  private static ArrayList<String> invalids = new ArrayList<String>();

  @Override
  public void output(String text, String type, String path, Directory currDir, String cmd) {
    Output.stdOutput(text, type, path, currDir, cmd);
  }

  @Override
  public String printDescription() {
    return "mkdir DIR1 DIR2 \n" + "Create directories DIR1, DIR2"
        + ", each of which may be relative to the current directory or may " + "be a full path.";
  }

  @Override
  public void displayError(int code, String arg) {
    if (code == 0) {
      Output.stdError("mkdir: directory contains invalid character " + arg);
    }
  }

  @Override
  public boolean checkInput(int code, String arg, Directory currDir) {
    return false;
  }

  /**
   * Creates a new directory with path of <code>path</code>
   * 
   * @param path The path of the Directory to be created
   * @param currDir The current working directory
   */

  public boolean mkdir(String path, Directory currDir, boolean validCreation) {
    if (!(currDir.create(path, Path.Type.D))) {
      return false;
    }
    return true;
  }

  /**
   * Returns the string <code>name</code> if the directory name has an invalid character. Otherwise,
   * return null.
   * 
   * @param name The name of the directory.
   * @return The name of the invalid directory or null if directory has a valid name.
   */
  public String checkForInvalids(String name) {
    for (String c : invalids) {
      if (name.contains(c))
        return c;
    }
    return null;
  }

  @Override
  public Directory run(String[] args, Directory currDir, String[] redirectionArgs) {
    if (!redirectionArgs[0].equals("") && !redirectionArgs[1].equals("")) {
      output("", "error", redirectionArgs[1], currDir, "mkdir");
      return currDir;
    }

    boolean validCreation = true;
    int i = 1;

    while (i < args.length && validCreation) {
      validCreation = mkdir(args[i], currDir, validCreation);
      i++;
    }

    return currDir;
  }

  @Override
  public int expectedNumOfArgs() {
    // Expects exactly at least 1 arg, which is a directory
    return -2;
  }
}
