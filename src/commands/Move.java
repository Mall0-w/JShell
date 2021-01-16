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

/**
 * This class implements the mv command.
 *
 */
public class Move implements Command, ErrorHandler {

  @Override
  public void output(String text, String type, String path, Directory currDir, String cmd) {
    Output.stdOutput(text, type, path, currDir, cmd);
  }

  @Override
  public String printDescription() {
    return "mv OLDPATH NEWPATH\n"
        + "Move existing item OLDPATH to NEWPATH.\nIf OLDPATH and NEWPATH are existing files, "
        + " move OLDPATH to NEWPATH and overwrite NEWPATH.\nIf OLDPATH is a file and NEWPATH "
        + "is an existing directory, move OLDPATH into NEWPATH directory.\nIf OLDPATH and NEWPATH "
        + "are existing directories, recursively move OLDPATH and its contents into NEWPATH.\n"
        + "If NEWPATH does not exist, rename OLDPATH to NEWPATH.";
  }

  @Override
  public void displayError(int code, String arg) {
    if (code == 0) {
      Output.stdError("mv: " + arg + " is a source path that does not exist.");
    } else if (code == 1) {
      Output.stdError("mv: " + arg + " is an invalid destination path because the parent path "
          + "does not exist.");
    } else if (code == 2) {
      Output.stdError("mv: Cannot copy a directory to a file.");
    } else if (code == 3) {
      Output.stdError("mv: Destination directory " + arg + " does not exist.");
    } else if (code == 4) {
      Output.stdError(
          "mv: Destination directory cannot be a child of the source directory or itself.");
    }
  }

  @Override
  public boolean checkInput(int code, String arg, Directory currDir) {
    if (code == 0) {
      // Check if path exists
      if (currDir.search(arg) != null) {
        return true;
      }
    } else if (code == 1) {
      // Check if path is a file
      if (currDir.search(arg) instanceof File) {
        return true;
      }
    } else if (code == 2) {
      // Check if new path is a directory
      if (arg.endsWith("/") || currDir.search(arg) instanceof Directory) {
        return true;
      }

    }

    return false;
  }

  /**
   * Returns the path of the moved file or directory.
   * 
   * @param oldPath The source path.
   * @param newPath The destination path.
   * @param currDir The current directory in the shell.
   * @return
   */
  private String getMovePath(String oldPath, String newPath, Directory currDir) {
    if (newPath.substring(newPath.length() - 1).equals("/")) {
      return newPath + currDir.search(oldPath).getName();
    } else {
      return newPath + "/" + currDir.search(oldPath).getName();
    }
  }


  /**
   * Moves a file to an existing directory.
   * 
   * @param oldPath The String source path of the file to be moved.
   * @param newPath The String destination directory where the file will be moved to.
   * @param currDir The current directory in the shell.
   */
  private void moveFileToDirectory(String oldPath, String newPath, Directory currDir) {
    if (checkInput(0, newPath, currDir)) {
      String copyOfFile = getMovePath(oldPath, newPath, currDir);
      String copyOfFileContent = ((File) currDir.search(oldPath)).getContent();

      currDir.create(copyOfFile, Path.Type.F);
      ((File) currDir.search(copyOfFile)).setContent(copyOfFileContent);

      RemoveDirectory rm = new RemoveDirectory();
      rm.rm(currDir.search(oldPath));
    } else {
      displayError(3, newPath);
    }
  }

  /**
   * Overwrites the file <code>newPath</code> with the contents of file <code>newPath></code>. If
   * <code>newPath</code> does not exist, creates the file first.
   * 
   * @param oldPath The String source path of the file to be moved.
   * @param newPath The String destination file where the file <code>newPath</code> will be moved
   *        to.
   * @param currDir The current directory in the shell.
   */
  private void moveFileToFile(String oldPath, String newPath, Directory currDir) {
    String copyOfFileContent = ((File) currDir.search(oldPath)).getContent();

    if (!(checkInput(0, newPath, currDir))) {
      currDir.create(newPath, Path.Type.F);
    }

    ((File) currDir.search(newPath)).setContent(copyOfFileContent);

    RemoveDirectory rm = new RemoveDirectory();
    rm.rm(currDir.search(oldPath));
  }

  /**
   * Moves a directory and its contents to a new directory. If the new directory
   * <code>newPath</code> does not exist, rename the source directory <code>oldPath</code> to
   * <code>newPath</code>.
   * 
   * @param oldPath The String source path of the file to be copied.
   * @param newPath The String destination path of the directory <code>oldPath</code> will be copied
   *        to.
   * @param currDir The current directory in the shell.
   */
  private void moveDirectoryToDirectory(String oldPath, String newPath, Directory currDir) {
    Copy cp = new Copy();
    RemoveDirectory rm = new RemoveDirectory();

    if (checkInput(0, newPath, currDir)) {
      String copyOfDirectory = getMovePath(oldPath, newPath, currDir);

      if (checkInput(0, copyOfDirectory, currDir)) {
        rm.rm(currDir.search(copyOfDirectory));
      }

      currDir.create(copyOfDirectory, Path.Type.D);
      cp.copyRecursive(oldPath, copyOfDirectory, currDir);

      rm.rm(currDir.search(oldPath));
    } else {
      currDir.create(newPath.substring(0, newPath.length() - 1), Path.Type.D);
      cp.copyRecursive(oldPath, newPath.substring(0, newPath.length() - 1), currDir);

      rm.rm(currDir.search(oldPath));
    }
  }

  /**
   * Removes the source paths, after they are already copied to the destination.
   * 
   * @param args The source paths.
   * @param currDir The current directory of the shell.
   * @param redirectionArgs Redirection arguments.
   */
  private void moveRemove(String[] args, Directory currDir, String[] redirectionArgs) {
    if (checkInput(1, args[1], currDir)) {
      if (checkInput(2, args[2], currDir)) {
        moveFileToDirectory(args[1], args[2], currDir);
      } else {
        moveFileToFile(args[1], args[2], currDir);
      }
    } else {
      if (checkInput(2, args[2], currDir)) {
        moveDirectoryToDirectory(args[1], args[2], currDir);
      } else {
        displayError(2, null);
      }
    }
  }

  @Override
  public Directory run(String[] args, Directory currDir, String[] redirectionArgs) {
    Copy cp = new Copy();

    if (!redirectionArgs[0].equals("") && !redirectionArgs[1].equals("")) {
      output("", "error", redirectionArgs[1], currDir, "mv");
      return currDir;
    }

    if (!checkInput(0, args[1], currDir)) {
      displayError(0, args[1]);
      return currDir;
    } else if (!checkInput(0, Directory.getParentPathString(args[2]), currDir)) {
      displayError(1, args[2]);
      return currDir;
    } else if (cp.childOfSourceDirectory(args[1], args[2]) || args[1].equals(".")
        || args[1].equals("..")) {
      displayError(4, null);
      return currDir;
    }

    moveRemove(args, currDir, redirectionArgs);

    return currDir;
  }

  @Override
  public int expectedNumOfArgs() {
    return 2;
  }

}
