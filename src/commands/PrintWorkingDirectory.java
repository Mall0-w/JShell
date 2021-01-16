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
import interfaces.Command;
import io.Output;

/**
 * This class implements the pwd command.
 */
public class PrintWorkingDirectory implements Command {
  @Override
  public void output(String text, String type, String path, Directory currDir, String cmd) {
    Output.stdOutput(text, type, path, currDir, cmd);
  }

  @Override
  public String printDescription() {
    return "pwd [>OUTFILE] \n" + "If OUTFILE is not given, prints the current working directory "
        + "(including the whole path).\nOtherwise, sets the contents of OUTFILE "
        + "to the path of the current working directory\n\n" + "pwd >>OUTFILE\n"
        + "Appends the path of the current working directory to OUTFILE.  If "
        + "OUTFILE doesn't exist, a new file will be created.";
  }

  /**
   * Prints the current directory of the shell.
   * 
   * @param currDir The current directory of the shell.
   */
  public String pwd(Directory currDir, String[] redirectionArgs) {
    output(currDir.getPath(), redirectionArgs[0], redirectionArgs[1], currDir, "pwd");
    return currDir.getPath();
  }

  @Override
  public Directory run(String[] args, Directory currDir, String[] redirectionArgs) {
    pwd(currDir, redirectionArgs);
    return currDir;
  }

  @Override
  public int expectedNumOfArgs() {
    // Expects no args, just prints directory
    return 0;
  }

}
