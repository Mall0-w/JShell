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
import entity.File;
import entity.Path;
import interfaces.Command;
import interfaces.ErrorHandler;
import io.Output;
import java.util.ArrayList;

/**
 * This class implements the ls command.
 */
public class ListFiles implements Command, ErrorHandler {

  /**
   * Valid output
   */
  StringBuilder sb;
  /**
   * Error output
   */
  StringBuilder sbError;

  public ListFiles() {
    sb = new StringBuilder();
    sbError = new StringBuilder();
  }

  @Override
  public void output(String text, String type, String path, Directory currDir, String cmd) {
    if (type.equals("build")) {
      sb.append(text);
      sb.append("\n");
    } else {
      Output.stdOutput(text, type, path, currDir, cmd);
    }
  }

  @Override
  public String printDescription() {
    return "ls [-R] [Path ...] [> OUTFILE] \n" + "If no paths are given, print the "
        + "contents (file or directory) of the current directory, with a new "
        + "line following each of the content (file or directory). \n"
        + "Otherwise, for each p, the order listed:\n " + "* if p specifies a file, print p.\n"
        + "* if p specifies a directory, print p, a colon, then the contents of"
        + "that directory, then an extra new line. \n"
        + "If -R is given, ls will recursively list all subdirectories \n "
        + "if OUTFILE is given, ls will set conents of OUTFILE to its output instead "
        + "of printing to the shell.  If OUTFILE does not exist, a new file will "
        + "be created\n\n" + "ls [-R] [Path ...] >> OUTFILE \n "
        + "If no paths are given, append the "
        + "contents (file or directory) of the current directory, with a new "
        + "line following each of the content (file or directory) to OUTFILE. \n"
        + "Otherwise, for each p, the order listed:\n " + "* if p specifies a file, append p.\n"
        + "* if p specifies a directory, append p, a colon, then the contents of"
        + "that directory, then an extra new line. \n" + "To OUTFILE \n"
        + "If -R is given, ls will recursively list all subdirectories \n"
        + "If OUTFILE does not exist, a new file will be created\n";
  }

  @Override
  public void displayError(int code, String arg) {
    if (code == 0) {
      Output.stdError("ls: location " + "\"" + arg + "\"" + " does not exist", sbError);
    }

  }

  @Override
  public boolean checkInput(int code, String arg, Directory currDir) {
    if (code == 0) {
      if (!arg.equals("")) {
        return true;
      }
    }

    return false;
  }

  /**
   * lists all subdirectories and files contained within <code>toSearch</code>
   * 
   * @param toSearch current path for which you're listing contents
   * @return
   */
  public StringBuilder listFiles(Path toSearch, Directory currDir, String[] redirectionArgs) {
    StringBuilder sb = new StringBuilder();
    if (toSearch instanceof File) {
      output(toSearch.getName(), "build", redirectionArgs[1], currDir, "ls");
      sb.append(toSearch.getName());
    } else {
      output(toSearch.getPath() + ":", "build", redirectionArgs[1], currDir, "ls");
      sb.append(toSearch.getPath() + ":");
      ArrayList<String> names = ((Directory) toSearch).getChildNames();
      output(names.toString(), "build", redirectionArgs[1], currDir, "ls");
      sb.append(names);
    }
    output("", "build", redirectionArgs[1], currDir, "ls");
    return sb;
  }

  /**
   * Recursively list all subdirectories and files within <code> currPath </code>
   * 
   * @param currPath : current path that you're listing
   * @param list : list of all subdirectories and files so far
   * @return a list containing the full traversal of listFilesRecursive
   */

  public StringBuilder listFilesRecursive(Path currPath, StringBuilder list, Directory currDir,
      String[] redirectionArgs) {
    if (currPath == null) {
      return list;
    } else if (currPath instanceof File) {
      output(currPath.getName(), "build", redirectionArgs[1], currDir, "ls");
      list.append(currPath.getName() + "\n");
      return list;
    } else {
      output(currPath.getPath() + ":", "build", redirectionArgs[1], currDir, "ls");
      list.append(currPath.getPath() + ":\n");
      output(((Directory) currPath).getChildNames().toString() + "\n", "build", redirectionArgs[1],
          currDir, "ls");
      list.append(((Directory) currPath).getChildNames().toString() + "\n");
      for (String name : ((Directory) currPath).getChildNames()) {
        if (((Directory) currPath).search(name) instanceof Directory) {
          listFilesRecursive(((Directory) currPath).search(name), list, currDir, redirectionArgs);
        }
      }
    }
    return list;
  }

  /**
   * Lists all files and subdirectories in the current directory, one level down.
   * 
   * @param currDir The current directory in the shell.
   * @param redirectionArgs Redirection arguments.
   */
  private void listParentDirectoryFiles(Directory currDir, String[] redirectionArgs) {
    for (String name : currDir.getChildNames()) {
      output(name, "build", redirectionArgs[1], currDir, "ls");
    }

    output(sb.toString().substring(0, Math.max(sb.toString().length() - 1, 0)), redirectionArgs[0],
        redirectionArgs[1], currDir, "ls");
  }

  /**
   * Lists all files and subdirectories in the list of given directories in order, one level down.
   * 
   * @param arguments The number of files and directories given.
   * @param args A list of directories and files.
   * @param currDir The current directory in the shell.
   * @param redirectionArgs Redirection arguments.
   */
  private void listMultipleDirectoryFiles(int arguments, String[] args, Directory currDir,
      String[] redirectionArgs) {
    for (int i = 1; i < arguments; i++) {
      Path toSearch = currDir.search(args[i]);
      if (toSearch == null) {
        // System.out.println("testing");
        displayError(0, args[i]);
        break;
      } else {
        listFiles(toSearch, currDir, redirectionArgs);
      }
    }
  }

  /**
   * Returns the number of paths given for ls.
   * 
   * @param arguments The arguments given for the command.
   * @param redirectionArgs Redirection arguments.
   * @return The number of path arguments given.
   */
  private int numberOfPaths(int arguments, String[] redirectionArgs) {
    if (!redirectionArgs[0].equals("")) {
      arguments -= 2;
    }

    return arguments;
  }

  /**
   * Converts the StringBuilder strings into output for ls.
   * 
   * @param sb The valid output.
   * @param sbError The error output.
   * @param redirectionArgs Redirection arguments.
   * @param currDir The current directory in the shell.
   */
  private void convertOutput(StringBuilder sb, StringBuilder sbError, String[] redirectionArgs,
      Directory currDir) {
    if (!sb.toString().equals("")) {
      output(sb.toString(), redirectionArgs[0], redirectionArgs[1], currDir, "ls");
    }

    if (!sbError.toString().equals("")) {
      // output(sbError.toString(), redirectionArgs[0], redirectionArgs[1], currDir, "ls");
      Output.stdError(sbError.toString());
    }
  }

  @Override
  public Directory run(String[] args, Directory currDir, String[] redirectionArgs) {
    int arguments = numberOfPaths(args.length, redirectionArgs);

    // Check if there are no args
    if (arguments == 1) {
      listParentDirectoryFiles(currDir, redirectionArgs);
      return currDir;
    }
    // For a given path (has an argument)
    if (!(args[1].equals("-R"))) {
      listMultipleDirectoryFiles(arguments, args, currDir, redirectionArgs);
    } else {
      StringBuilder list = new StringBuilder();
      if (arguments == 2) {
        listFilesRecursive(currDir, list, currDir, redirectionArgs);
      } else {
        for (int i = 2; i < arguments; i++) {
          Path toSearch = currDir.search(args[i]);
          if (toSearch == null) {
            displayError(0, args[i]);
            break;
          } else {
            listFilesRecursive(toSearch, list, currDir, redirectionArgs);
          }
        }
      }
    }

    convertOutput(sb, sbError, redirectionArgs, currDir);
    return currDir;
  }

  @Override
  public int expectedNumOfArgs() {
    // Expect 0 arguments or 1+ arguments (the path as a string array).
    return -1;
  }
}
