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
// ********************************************************

package commands;

import entity.Directory;
import entity.File;
import entity.Path;
import interfaces.Command;
import interfaces.ErrorHandler;
import io.Output;

/**
 * This class implements the cd command.
 */
public class ChangeDirectory implements Command, ErrorHandler {

  @Override
  public void output(String text, String type, String path, Directory currDir, String cmd) {
    Output.stdOutput(text, type, path, currDir, cmd);
  }

  @Override
  public String printDescription() {
    return "cd DIR \n" + "change current directory to DIR which"
        + " may be relative to the current directory or may be a full path";
  }

  @Override
  public void displayError(int code, String arg) {
    if (code == 0) {
      Output.stdError("cd: " + arg + " is not an existing path");
    } else if (code == 1) {
      Output.stdError("cd: " + arg + " is not a directory");
    }
  }

  @Override
  public boolean checkInput(int code, String arg, Directory currDir) {
    /*
     * if (code == 0) { if (currDir.search(arg) != null) { return true; } displayError(0, arg); }
     * else if (code == 1) { if (currDir.isDirectory()) { return true; } displayError(1, arg); }
     */
    return false;
  }

  /**
   * Returns the new current directory of the shell, <code>path</code>.
   * 
   * @param path The new current directory of the shell.
   * @param currDir The current directory of the shell.
   * @return The new current directory.
   */
  public Directory cd(String path, Directory currDir) {
    Path p = currDir.search(path);
    if (p == null || p instanceof File) {
      displayError(1, path);
      return currDir;

    }
    return (Directory) p;

  }

  @Override
  public Directory run(String[] args, Directory currDir, String[] redirectionArgs) {
    if (!redirectionArgs[0].equals("") && !redirectionArgs[1].equals("")) {
      output("", "error", redirectionArgs[1], currDir, "cd");
      return currDir;
    }

    return cd(args[1], currDir);
  }

  @Override
  public int expectedNumOfArgs() {
    // Needs exactly 1 arg
    return 1;
  }

}
