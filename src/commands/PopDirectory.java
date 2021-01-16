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

import entity.Directory;
import entity.DirectoryStack;
import entity.FileSystem;
import interfaces.Command;
import interfaces.ErrorHandler;
import io.Output;

/**
 * This class implements the popd command.
 */
public class PopDirectory implements Command, ErrorHandler {


  @Override
  public void output(String text, String type, String path, Directory currDir, String cmd) {
    Output.stdOutput(text, type, path, currDir, cmd);
  }

  @Override
  public String printDescription() {
    return "popd \n" + "Removes the top most directory from the"
        + " directory stack and makes it the current working directory.";
  }

  @Override
  public void displayError(int code, String arg) {
    if (code == 0) {
      Output.stdError("popd:  Directory stack currently empty");
    }

  }

  @Override
  public boolean checkInput(int code, String arg, Directory currDir) {
    return false;
  }

  /**
   * Removes the top entry from the directory stack.
   * 
   * @return The new directory stack.
   */
  private Directory popd() {

//    return this.dirStack.dirPop();
    return FileSystem.getInstance().getDirectoryStack().popFromStack();
  }

  @Override
  public Directory run(String[] args, Directory currDir, String[] redirectionArgs) {
    if (!redirectionArgs[0].equals("") && !redirectionArgs[1].equals("")) {
      output("", "error", redirectionArgs[1], currDir, "popd");
      return currDir;
    }

    Directory newDir = popd();
    // Changes the current directory to the recently popped top entry of the directory stack.
    if (newDir == null) {
      displayError(0, "");
      return currDir;
    }
    return newDir;

  }

  @Override
  public int expectedNumOfArgs() {
    // Expects no args
    return 0;
  }

  public DirectoryStack getDirStack() {
    return FileSystem.getInstance().getDirectoryStack();
  }

}
