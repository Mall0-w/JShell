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

import entity.*;
import interfaces.Command;
import interfaces.ErrorHandler;
import io.Output;

/**
 * This class implements the pushd command.
 */
public class PushDirectory implements Command, ErrorHandler {

  /**
   * dirStack is the directory stack.
   */

  /**
   * Creates a new directory stack instance.
   */


  @Override
  public void output(String text, String type, String path, Directory currDir, String cmd) {
    Output.stdOutput(text, type, path, currDir, cmd);
  }

  @Override
  public void displayError(int code, String arg) {
    if (code == 0) {
      Output.stdError("pushd:  Directory " + "\"" + arg + "\"" + " does not exist");
    }
  }


  @Override
  public boolean checkInput(int code, String arg, Directory currDir) {
    return false;
  }

  @Override
  public String printDescription() {
    return "pushd DIR \n" + "Saves the current working directory"
        + " by pushing it onto the directory stack so that it can be returned"
        + " to at anytime via the popd command.";
  }

  /**
   * Pushes the current working directory into the directory stack to save it. Then, changes the new
   * current working directory to <code>toPush</code>.
   * 
   * @param toPush The new current working directory.
   */
  private void pushd(Directory toPush) {
    FileSystem.getInstance().getDirectoryStack().dirPush(toPush);

  }

  @Override
  public Directory run(String[] args, Directory currDir, String[] redirectionArgs) {
    if (!redirectionArgs[0].equals("") && !redirectionArgs[1].equals("")) {
      output("", "error", redirectionArgs[1], currDir, "pushd");
      return currDir;
    }


    Path toChange = currDir.search(args[1]);
    if (toChange == null) {
      displayError(0, args[1]);
      return currDir;
    } else if (toChange instanceof File) {
      displayError(0, args[1]);
      return currDir;
    }
    pushd(currDir);
    return (Directory) toChange;
    // changeDirectory(args[1]);
  }

  /**
   * Returns the directory stack.
   * 
   * @return The directory stack.
   */

  public DirectoryStack getDirStack(){
    return FileSystem.getInstance().getDirectoryStack();
  }

  @Override
  public int expectedNumOfArgs() {
    // Expects exactly 1 arg
    return 1;
  }

}
