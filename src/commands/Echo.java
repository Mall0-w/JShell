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

import io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import entity.*;
import interfaces.*;


/**
 * This class implements the echo command.
 */
public class Echo implements Command, ErrorHandler {

  @Override
  public void output(String text, String type, String path, Directory currDir, String cmd) {
    Output.stdOutput(text, type, path, currDir, cmd);
  }

  @Override
  public String printDescription() {
    return "echo STRING [> OUTFILE]\n" + "If OUTFILE is not "
        + "provided, put STRING on the shell. Otherwise, put STRING into "
        + "file OUTFILE.  This will create a new file if OUTFILE does not exist"
        + " and erases the old contents of OUTFILE if it exists. \n\n" + "echo STRING >> OUTFILE\n"
        + "If OUTFILE is not provided, put " + "STRING on the shell. Otherwise, put STRING into "
        + "file OUTFILE.  This will create a new file if OUTFILE does not exist"
        + "and appends to the contents of OUTFILE if it exists";
  }

  @Override
  public void displayError(int code, String arg) {
    if (code == 0) {
      // "Symbol given not > or >>" error message
      Output.stdError("echo: Invalid symbol was provided. Must be \">\" or \">>\"");
    } else if (code == 1) {
      Output.stdError("echo: Invalid arguments given.\nNote that a set of double quotes counts "
          + "as one argument");
    } else if (code == 2) {
      Output.stdError("echo: Given text string is not surrounded by double quotes");
    }
  }

  @Override
  public boolean checkInput(int code, String arg, Directory currDir) {
    if (code == 0) {
      // Check if given symbol is > or >>
      if (arg.equals(">") || arg.equals(">>")) {
        return true;
      }
      displayError(0, null);
    } else if (code == 1) {
      // Checks if string is surrounded by quotation marks
      if (arg.startsWith("\"") && arg.endsWith("\"")) {
        return true;
      }
      displayError(2, null);
    }
    return false;
  }

  /**
   * Outputs <code>text</code> to the shell.
   * 
   * @param text The string to write to shell.
   * @currDir The current directory in the shell.
   * 
   * @return The String in text.
   */
  public String echoPrint(String text, Directory currDir) {
    output(text, "", "", currDir, "echo");
    return text;
  }

  /**
   * Overwrites file <code>path</code> with <code>text</code>, if file already exists. If file does
   * not exist, creates a new file specified by <code>path</code> and writes <code>text</code> to
   * it.
   * 
   * @param text The string to write to the file.
   * @param path The file <code>text</code> will be written to.
   * @param currDir The current directory in the shell.
   */
  private void echoOverwrite(String text, String path, Directory currDir) {
    OutputRedirection.outputRedirect(">", text, path, currDir, "echo");
  }

  /**
   * Appends file <code>path</code> with <code>text</code>, if file already exists. If file does not
   * exist, creates a new file specified by <code>path</code> and writes <code>text</code> to it.
   * 
   * @param text The string to write to the file.
   * @param path The file <code>text</code> will be written to.
   * @param currDir The current directory in the shell.
   */
  private void echoAppend(String text, String path, Directory currDir) {
    OutputRedirection.outputRedirect(">>", text, path, currDir, "echo");
  }

  /**
   * Returns a string array of parsed user input, specifically for echo. This version of parsing
   * disregards any whitespace between double quotes when separating arguments in a line of user
   * input, meaning that anything between double quotes is one argument, excluding the quotes.
   * 
   * @param args A String array of parsed user input incorrectly parsed for echo.
   * @return A String array of user input parsed correctly for echo.
   */
  private String[] echoParser(String[] args) {
    StringBuilder unparsedArgs = new StringBuilder();

    for (int i = 0; i < args.length; i++) {
      unparsedArgs.append(args[i]);
      if (i < args.length - 1) {
        unparsedArgs.append(" ");
      }
    }

    String line = unparsedArgs.toString();
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
    parsedArray[1] = OutputRedirection.redirectSymbolInStringParse(parsedArray[1]);

    return parsedArray;
  }

  @Override
  public Directory run(String[] args, Directory currDir, String[] redirectionArgs) {
    String[] newArgs = echoParser(args);
    String text = newArgs[1];
    if (!checkInput(1, text, currDir)) {
      return currDir;
    }
    text = text.replace("\"", "");
    if (newArgs.length == 4) {
      String symbol = newArgs[2];
      if (checkInput(0, symbol, currDir)) {
        String path = newArgs[3];

        if (symbol.equals(">")) {
          echoOverwrite(text, path, currDir);
        } else if (symbol.equals(">>")) {
          echoAppend(text, path, currDir);
        }
      }
    } else if (newArgs.length == 2) {
      echoPrint(text, currDir);
    } else {
      displayError(1, null);
    }
    return currDir;
  }

  @Override
  public int expectedNumOfArgs() {
    // Needs 1 argument, can accept more as optional.
    return -2;
  }
}
