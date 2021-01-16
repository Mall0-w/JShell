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

import commands.invalidCharList.InvalidsList;
import entity.Directory;
import entity.FileSystem;
import interfaces.Command;
import interfaces.ErrorHandler;
import io.Output;


import java.io.*;

/**
 * This class implements the loadJShell command.
 */
public class LoadJShell implements Command, ErrorHandler {

  @Override
  public void output(String text, String type, String path, Directory currDir, String cmd) {
    Output.stdOutput(text, type, path, currDir, cmd);
  }

  @Override
  public String printDescription() {
    return "loadJShell FileName \n "
        + "Loads the JShell from the given file. Only works when loadJShell is the first"
        + "command ran.";

  }

  /**
   *
   * @param fileName the name of the file wanted
   * @return the path that the file will be stored in
   */

  private String getFilePathNameRelativeToSrc(String fileName) {
    return fileName + ".ser";
  }

  /**
   *
   * @param fileName String that represents the filename given by JShell
   * @return whether fileName contains invalid characters or not
   */
  private boolean containsInvalidChars(String fileName) {
    if (InvalidsList.isInvalid(fileName) != null || fileName.contains(File.separator)
        || fileName.contains(".")) {
      Output.stdError("Error: You have given an invalid file name. "
          + "Note that the file name cannot contain extensions.");
      return true;
    }
    return false;
  }

  /**
   * Checks if there are redirection arguments in the command. If there are, then it's an invalid
   * command
   *
   */
  private boolean containsRedirectionArgs(Directory currDir, String[] redirectionArgs) {
    if (!redirectionArgs[0].equals("") && !redirectionArgs[1].equals("")) {
      output("", "error", redirectionArgs[1], currDir, "loadJShell");
      return true;
    }
    return false;
  }

  /**
   * Checks if the loadJShell command is the first command inputted in history.
   *
   */
  private boolean isFirstCommandInHistoryList() {
    if (FileSystem.getInstance().historyListContainsOneCommand()) {
      return true;
    } else {
      Output.stdError("Error: There are other commands already inputted. "
          + "LoadJShell must be the first command ran.");
      return false;
    }
  }


  @Override
  public void displayError(int code, String arg) {}

  @Override
  public boolean checkInput(int code, String arg, Directory currDir) {
    return false;
  }

  @Override
  public Directory run(String[] args, Directory currDir, String[] redirectionArgs) {

    if (containsRedirectionArgs(currDir, redirectionArgs)) { return currDir; }
    if (!isFirstCommandInHistoryList()) { return currDir; }

    String filename = args[1];
    if (containsInvalidChars(filename)) { return currDir; }

    try {
      String fileNameStr = getFilePathNameRelativeToSrc(filename);
      File jShellFile = new File(fileNameStr);
      FileInputStream fileInputStream = new FileInputStream(jShellFile);
      ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
      FileSystem loadedFileSystem = (FileSystem) objectInputStream.readObject();

      FileSystem.getInstance().setNewFileSystem(loadedFileSystem);

      objectInputStream.close();
      fileInputStream.close();

    } catch (FileNotFoundException e) {
      Output.stdError(
          "loadJShell: Could not find the file from the given path." + " (FileInputStream) ");
    } catch (IOException e) {
      Output.stdError("loadJShell: Error initializing stream (ObjectInputStream)");
    } catch (ClassNotFoundException e) {
      Output.stdError("loadJShell: Could not find the corresponding Class. "
          + "Problem with backend. Check if the Class file is in right directory");
    }
    return FileSystem.getInstance().getCurrDir();
  }

  @Override
  public int expectedNumOfArgs() {
    // Expects exactly 1 arg: The name of the saved JShell file
    return 1;
  }
}
