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
package io;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import commands.History;
import entity.FileSystem;
import entity.HistoryList;
import interfaces.Command;

/**
 * A class that implements basic input parsing for user command input.
 */
public class InputParser {

  // MARK: Fields
  /**
   * Store the history information for the history command
   */

  // MARK: Constructor
  /**
   * Creates a new history instance to keep track of command usage for history.
   */



  // MARK: Public methods

  /**
   * Returns a two element array, containing the output redirection symbol and redirection file
   * respectively, if they exist. If it does not exist, the array element contains a null String
   * instead.
   * 
   * @param parsed The array of parsed user input.
   * @param command An existing shell command.
   * @return A String array with the output redirection symbol and redirection file respectively.
   */
  public String[] getRedirectionArgs(String[] parsed, Command command) {
    String redirectionArgs[] = new String[2];
    for (int i = 0; i < parsed.length; i++) {
      if (parsed[i].equals(">") || parsed[i].equals(">>")) {
        redirectionArgs[0] = parsed[i];

        if (parsed.length - i == 2) {
          redirectionArgs[1] = parsed[i + 1];
          return redirectionArgs;
        }
        break;
      }
    }

    redirectionArgs[0] = "";
    redirectionArgs[1] = "";
    return redirectionArgs;
  }

  /**
   * Returns parsed user input as an array of strings. Also adds the parsed command to the history
   * stack.
   * 
   * @param line A string of user input unparsed.
   * @return A String array of parsed user input.
   */
  public String[] parseToArray(String line) {

    FileSystem.getInstance().getStoredHistoryList().addCommand(line);


    // String[] stringArrCommand = line.replaceAll("\\s+", " ").split(" ");
    // return stringArrCommand;
    line = OutputRedirection.redirectSymbolParse(line);
    List<String> parsedList = new ArrayList<String>();
    Pattern pattern = Pattern.compile("[^\\s\"]+|\"([^\"]*)\"");
    Matcher matcher = pattern.matcher(line);

    while (matcher.find()) {
      if (matcher.group(0) != null) {
        String str = matcher.group(0);
        parsedList.add(str);
      }
    }

    String[] parsedArray = new String[parsedList.size()];
    parsedArray = parsedList.toArray(parsedArray);
    for (int i = 0; i < parsedArray.length; i++) {
      parsedArray[i] = OutputRedirection.redirectSymbolInStringParse(parsedArray[i]);
    }

    return parsedArray;
  }
}
