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
 * This class implements the cp command.
 */
public class Copy implements Command, ErrorHandler {

  @Override
  public void output(String text, String type, String path, Directory currDir, String cmd) {
    Output.stdOutput(text, type, path, currDir, cmd);
  }

  @Override
  public String printDescription() {
    return "cp OLDPATH NEWPATH\n"
        + "Copy existing item OLDPATH to NEWPATH.\nIf OLDPATH and NEWPATH are existing files, "
        + "overwrite NEWPATH with OLDPATH.\nIf OLDPATH is a file and NEWPATH is an existing "
        + "directory, copy OLDPATH into NEWPATH directory.\nIf OLDPATH and NEWPATH are existing "
        + "directories, recursively copy OLDPATH and its contents into NEWPATH.\nIf NEWPATH does "
        + "not exist, create a copy of OLDPATH and its contents and rename the copy to NEWPATH.";
  }

  @Override
  public void displayError(int code, String arg) {
    if (code == 0) {
      Output.stdError("cp: " + arg + " is a source path that does not exist.");
    } else if (code == 1) {
      Output.stdError("cp: " + arg + " is an invalid destination path because the parent path "
          + "does not exist.");
    } else if (code == 2) {
      Output.stdError("cp: Cannot copy a directory to a file.");
    } else if (code == 3) {
      Output.stdError("cp: Destination directory " + arg + " does not exist.");
    } else if (code == 4) {
      Output.stdError(
          "cp: Destination directory cannot be a child of the source directory or itself.");
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
   * Returns the path of the copied file or directory.
   * 
   * @param oldPath The source path.
   * @param newPath The destination path.
   * @param currDir The current directory in the shell.
   * @return
   */
  private String getCopyPath(String oldPath, String newPath, Directory currDir) {
    if (newPath.substring(newPath.length() - 1).equals("/")) {
      return newPath + currDir.search(oldPath).getName();
    } else {
      return newPath + "/" + currDir.search(oldPath).getName();
    }
  }

  /**
   * Copies a file to an existing directory.
   * 
   * @param oldPath The String source path of the file to be copied.
   * @param newPath The String destination directory where the file will be copied to.
   * @param currDir The current directory in the shell.
   */
  private void copyFileToDirectory(String oldPath, String newPath, Directory currDir) {
    if (checkInput(0, newPath, currDir)) {
      String copyOfFile = getCopyPath(oldPath, newPath, currDir);
      String copyOfFileContent = ((File) currDir.search(oldPath)).getContent();
      currDir.create(copyOfFile, Path.Type.F);
      ((File) currDir.search(copyOfFile)).setContent(copyOfFileContent);
    } else {
      displayError(3, newPath);
    }
  }

  /**
   * Overwrites the file <code>newPath</code> with the contents of file <code>newPath></code>. If
   * <code>newPath</code> does not exist, creates the file first.
   * 
   * @param oldPath The String source path of the file to be copied.
   * @param newPath The String destination file where the file <code>newPath</code> will be copied
   *        to.
   * @param currDir The current directory in the shell.
   */
  private void copyFileToFile(String oldPath, String newPath, Directory currDir) {
    String copyOfFileContent = ((File) currDir.search(oldPath)).getContent();

    if (!(checkInput(0, newPath, currDir))) {
      currDir.create(newPath, Path.Type.F);
    }

    ((File) currDir.search(newPath)).setContent(copyOfFileContent);
  }

  /**
   * Returns a valid path combining path1 and path2. Helper function for copyRecursive() to ensure
   * it has the correct paths when recursively searching, e.g. ensures that "dir1" and "dir1/" are
   * parsed correctly to mean the same thing.
   * 
   * @param path1 The first part of the path.
   * @param path2 The second part of the path.
   * @return Returns a valid path string made from path1 plus path2.
   */
  private String copyRecursivePathCombiner(String path1, String path2) {
    if (path1.substring(path1.length() - 1).equals("/")) {
      return path1 + path2;
    } else {
      return path1 + "/" + path2;
    }
  }

  /**
   * Recursively copies a directory's contents to another directory.
   * 
   * @param oldRoot The root of the source directory.
   * @param newRoot The root of the destination directory.
   * @param currDir The current directory in the shell.
   */
  protected void copyRecursive(String oldRoot, String newRoot, Directory currDir) {
    ((Directory) currDir.search(oldRoot)).getChilds().forEach((key, value) -> {
      String newPath = copyRecursivePathCombiner(newRoot, value.getName());
      if (value instanceof Directory && !(key.equals(".")) && !(key.equals(".."))) {
        // System.out.println(key + ": " + value + "\n");
        currDir.create(newPath, Path.Type.D);
        copyRecursive(copyRecursivePathCombiner(oldRoot, key), newPath, currDir);
      } else if (value instanceof File) {
        // System.out.println(key + ": " + value + "\n");
        currDir.create(newPath, Path.Type.F);
        ((File) currDir.search(newPath)).setContent(((File) value).getContent());
      }
    });
  }

  /**
   * Copies a directory and its contents to a new directory. If the new directory
   * <code>newPath</code> does not exist, rename the source directory <code>oldPath</code> to
   * <code>newPath</code>.
   * 
   * @param oldPath The String source path of the file to be copied.
   * @param newPath The String destination path of the directory <code>oldPath</code> will be copied
   *        to.
   * @param currDir The current directory in the shell.
   */
  private void copyDirectoryToDirectory(String oldPath, String newPath, Directory currDir) {
    if (checkInput(0, newPath, currDir)) {
      String copyOfDirectory = getCopyPath(oldPath, newPath, currDir);

      if (checkInput(0, copyOfDirectory, currDir)) {
        RemoveDirectory rm = new RemoveDirectory();
        rm.rm(currDir.search(copyOfDirectory));
      }

      currDir.create(copyOfDirectory, Path.Type.D);
      copyRecursive(oldPath, copyOfDirectory, currDir);
    } else {
      currDir.create(newPath.substring(0, newPath.length() - 1), Path.Type.D);
      copyRecursive(oldPath, newPath.substring(0, newPath.length() - 1), currDir);
    }
  }

  /**
   * Runs the correct type of copy: copy file to directory, copy file to file, or copy directory to
   * directory.
   * 
   * @param args The arguments given for the copy command.
   * @param currDir The current directory in the shell.
   */
  private void copyType(String[] args, Directory currDir) {
    if (checkInput(1, args[1], currDir)) {
      if (checkInput(2, args[2], currDir)) {
        copyFileToDirectory(args[1], args[2], currDir);
      } else {
        copyFileToFile(args[1], args[2], currDir);
      }
    } else {
      if (checkInput(2, args[2], currDir)) {
        copyDirectoryToDirectory(args[1], args[2], currDir);
      } else {
        displayError(2, null);
      }
    }
  }

  /**
   * Returns true if the destination path, the path of the copied file, is a child of the source
   * path, the path to be copied. Otherwise, returns false.
   * 
   * @param sourcePath The source path.
   * @param destinationPath The destination path.
   * @return True if destination path is a child of the source path, and false otherwise.
   */
  protected boolean childOfSourceDirectory(String sourcePath, String destinationPath) {
    StringBuilder sourcePathModified = new StringBuilder();
    StringBuilder destinationPathModified = new StringBuilder();
    sourcePathModified.append(sourcePath);
    destinationPathModified.append(destinationPath);

    if (!(sourcePath.substring(sourcePath.length() - 1).equals("/"))) {
      sourcePathModified.append("/");
    }

    if (!(destinationPath.substring(destinationPath.length() - 1).equals("/"))) {
      destinationPathModified.append("/");
    }

    if (destinationPath.contains(sourcePathModified.toString())
        || sourcePath.equals(destinationPath)
        || sourcePathModified.toString().equals(destinationPath)
        || sourcePath.equals(destinationPathModified.toString())) {
      return true;
    }

    return false;
  }

  @Override
  public Directory run(String[] args, Directory currDir, String[] redirectionArgs) {
    if (!redirectionArgs[0].equals("") && !redirectionArgs[1].equals("")) {
      output("", "error", redirectionArgs[1], currDir, "cp");
      return currDir;
    }

    if (!checkInput(0, args[1], currDir)) {
      displayError(0, args[1]);
      return currDir;
    } else if (!checkInput(0, Directory.getParentPathString(args[2]), currDir)) {
      displayError(1, args[2]);
      return currDir;
    } else if (childOfSourceDirectory(args[1], args[2]) || args[1].equals(".")
        || args[1].equals("..")) {
      displayError(4, null);
      return currDir;
    }

    copyType(args, currDir);

    return currDir;
  }

  @Override
  public int expectedNumOfArgs() {
    return 2;
  }

}
