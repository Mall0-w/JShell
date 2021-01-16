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
import interfaces.*;
import io.Output;
import entity.File;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.net.*;

/**
 * This class implements the curl command.
 */
public class ClientURL implements Command, ErrorHandler {

  // Implemented methods from interface
  @Override
  public void output(String text, String type, String path, Directory currDir, String cmd) {
    Output.stdOutput(text, type, path, currDir, cmd);
  }

  @Override
  public String printDescription() {
    return "curl URL \n" + "Fetches the file at the given URL"
        + " and saves it in the current directory. Keeps the same file name";
  }

  @Override
  public void displayError(int code, String arg) {
    System.out.println(arg);
  }

  @Override
  public boolean checkInput(int code, String arg, Directory currDir) {
    return false;
  }

  /**
   * Returns a String filename given a URL.
   * 
   * @param url A website address in String.
   * @return The String filename from the URL.
   */
  private String getFileName(String url) {
    int lastSlashIndex = url.lastIndexOf('/');
    return url.substring(lastSlashIndex);
  }

  @Override
  public Directory run(String[] args, Directory currDir, String[] redirectionArgs) {
    if (!redirectionArgs[0].equals("") && !redirectionArgs[1].equals("")) {
      output("", "error", redirectionArgs[1], currDir, "curl");
      return currDir;
    }
    // Second element of the String array is the url
    String urlString = args[1];

    try {
      URL urlObj = new URL(urlString);
      String retrievedFileContents =
          new Scanner(urlObj.openStream(), StandardCharsets.UTF_8).useDelimiter("\\A").next();
      String filename = getFileName(urlString);

      // Creating a new File object and setting its contents
      File newFile = new File(filename);
      newFile.setContent(retrievedFileContents);


      // Add the File object into the current directory
      currDir.create(filename, newFile);

    } catch (MalformedURLException e) {
      Output.stdError("curl: The given URL is invalid");
    } catch (IOException e) {
      Output.stdError("curl: Couldn't connect to the URL, IO exception."
          + "Issue is likely due to local wifi connectivity problems");
    }
    return currDir;
  }

  @Override
  public int expectedNumOfArgs() {
    // Expects exactly 1 argument: the URL.
    return 1;
  }
}
