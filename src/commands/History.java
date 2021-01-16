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

import entity.FileSystem;
import entity.HistoryList;
import interfaces.Command;
import interfaces.ErrorHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import entity.Directory;
import io.Output;

/**
 * This class implements the history command, using the singleton design pattern.
 */
public class History implements Command, ErrorHandler, Serializable {

  /**
   * Serial number for keeping track of file version
   */
  private static final long serialVersionUID = 1L;


  // MARK: Fields


  // MARK: Methods

  @Override
  public void output(String text, String type, String path, Directory currDir, String cmd) {
    Output.stdOutput(text, type, path, currDir, cmd);
  }

  @Override
  public String printDescription() {
    return "history [NUMBER] [> OUTFILE] \n"
        + "If OUTFILE is not given, This command will print out"
        + " recent commands, one command per line, including syntactical errors."
        + "\nIf OUTFILE is given, the contents of OUTFILE will be set to "
        + " recent commands, one command per line, including syntactical errors; "
        + "if OUTFILE does not exist, a new file will be created"
        + " If NUMBER is given,\nwill only list the last NUMBER commands \n\n"
        + "history [NUMBER] >> OUTFILE\n"
        + "Will append the recent commands, one command per line, including syntactical errors "
        + "to OUTFILE, if OUTFILE does not exist, a new file will be created\n"
        + "If NUMBER is given, will only list the last NUMBER commands";

  }

  @Override
  public void displayError(int code, String arg) {
    Output.stdError(arg);
  }

  @Override
  public boolean checkInput(int code, String arg, Directory currDir) {
    return false;
  }

  /**
   *
   * @param length is the number of past commands wanted
   * @return A list Previous String Commands based upon the wanted length
   */


  private List<String> createHistoryStringList(int length) {

    List<String> historyStringList = new ArrayList<>();
    StringBuilder tempStrBuilder;

    int historyListSize = FileSystem.getInstance().getStoredHistoryList().getHistoryList().size();

    for (int i = historyListSize - length; i < historyListSize; i++) {
      // Creates a new empty StringBuilder each time. Is actually quite efficient.
      tempStrBuilder = new StringBuilder();
      tempStrBuilder.append(i + 1).append(". ")
          .append(FileSystem.getInstance().getStoredHistoryList().getHistoryList().get(i));
      historyStringList.add(tempStrBuilder.toString());
    }

    return historyStringList;

  }

  /**
   * Returns a history list given no arguments.
   * 
   * @return The history list as a String List.
   */
  public List<String> outputHistoryList() {
    return createHistoryStringList(
        FileSystem.getInstance().getStoredHistoryList().getHistoryList().size());
  }


  /**
   * Returns a history list given one argument.
   * 
   * @param length The length of the list.
   * @return The history list a String List.
   */
  public List<String> outputHistoryList(int length) {
    return createHistoryStringList(
        Math.min(length, FileSystem.getInstance().getStoredHistoryList().getHistoryList().size()));
  }


  /**
   * Returns a boolean value indicating whether number of user arguments given to the history
   * command is valid.
   * 
   * @param args The arguments given for the command history.
   * @return True if the number of arguments is valid, otherwise false.
   */
  public boolean validateArguments(String[] args, String[] redirectionArgs) {
    // From inputValidator, we know that there are at least 0 arguments, so
    // Length of the array is at least 1.

    // Checks for more arguments than needed
    if (args.length > 2 && redirectionArgs[0].equals("") && redirectionArgs[1].equals("")) {
      return false;
    }
    // Check for single argument. First argument has already been validated by
    // InputValidator, don't need to check anything.
    else if (args.length == 1
        || (args.length == 3 && !redirectionArgs[0].equals("") && !redirectionArgs[1].equals(""))) {
      return true;
    } else {
      // This means is exactly 1 argument, and thus array length of 2.
      try {
        int numHistoryCommands = Integer.parseInt(args[1]);

        // cannot have negative values
        if (numHistoryCommands < 0)
          return false;

      } catch (NumberFormatException e) {
        return false;
      }
      // No exception, so numHistoryCommands is a valid integer.
      return true;
    }
  }

  /**
   *
   * @param args the arguments for a given command, including the command itself
   * @param currDir the current working directory at time of command
   * @param redirectionArgs the redirection symbol and destination file
   * @return the current directory, unchaged
   */
  @Override
  public Directory run(String[] args, Directory currDir, String[] redirectionArgs) {
    List<String> historyList;
    StringBuilder outputStringBuilder = new StringBuilder();

    // Check to make sure the arguments are valid
    boolean isValidArgs = validateArguments(args, redirectionArgs);

    if (isValidArgs) {

      // Check if there is an optional argument there or not. Could put into a ternary
      // operator if want to, but less readable.
      if (args.length == 1 || (args.length == 3 && !redirectionArgs[0].equals("")
          && !redirectionArgs[1].equals(""))) {
        historyList = outputHistoryList();
      } else {
        historyList = outputHistoryList(Integer.parseInt(args[1]));
      }
      // Turns list of history commands into a single string, outputs it. (prints)
      for (String historyLine : historyList) {
        outputStringBuilder.append(historyLine).append("\n");
      }
      output(outputStringBuilder.toString(), redirectionArgs[0], redirectionArgs[1], currDir,
          "history");
    }

    else {
      // The arguments are not valid. Returning an error. Will change in future
      displayError(-1, "history: The input for the History Command is not valid");
    }
    return currDir;
  }


  @Override
  public int expectedNumOfArgs() {
    // Could have 0 or 1 argument.
    return -1;
  }
}
