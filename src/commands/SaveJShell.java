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
import interfaces.ErrorHandler;
import io.Output;
import entity.FileSystem;
import commands.invalidCharList.InvalidsList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class implements the saveJShell command.
 */
public class SaveJShell implements Command, ErrorHandler {

  // Implemented methods

  @Override
  public void output(String text, String type, String path, Directory currDir, String cmd) {
    Output.stdOutput(text, type, path, currDir, cmd);
  }

  @Override
  public String printDescription() {
    return "saveJShell FileName \n " + "Saves the current state of the JShell into a file";
  }

  /**
   * Returns the filename of the save location.
   * 
   * @param fileName A filepath to the save location.
   * @return The save location filename.
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
      output("", "error", redirectionArgs[1], currDir, "saveJShell");
      return true;
    }
    return false;
  }





  @Override
  public void displayError(int code, String arg) {
    Output.stdError(arg);
  }

  @Override
  public boolean checkInput(int code, String arg, Directory currDir) {
    return false;
  }


  // Stores files in the JShellSaveFiles package
  @Override
  public Directory run(String[] args, Directory currDir, String[] redirectionArgs) {
    // Check to make sure there aren't any redirection args
    if (containsRedirectionArgs(currDir, redirectionArgs)) { return currDir; }

    String fileName = args[1];
    if (containsInvalidChars(fileName)) { return currDir; }

    try {
      // Getting the file path relative to the src directory
      String filePathName = getFilePathNameRelativeToSrc(fileName);
      File saveFile = new File(filePathName);
      if(!saveFile.exists()){saveFile.createNewFile();}
      FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
      objectOutputStream.writeObject(FileSystem.getInstance());

      objectOutputStream.flush();
      fileOutputStream.flush();


    } catch (FileNotFoundException e) {
      Output.stdError("saveJShell: File not found");
    } catch (IOException e) {
      Output.stdError("saveJShell: Error initializing stream");
    } catch (Exception e) {
      Output.stdError("saveJShell: Some other exception I haven't thought of");
    }
    return currDir;
  }

  @Override
  public int expectedNumOfArgs() {
    // Expects exactly 1 arg, the name of the file
    return 1;
  }
}
