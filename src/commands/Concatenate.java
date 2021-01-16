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
import entity.*;
import interfaces.*;

/**
 * This class implements the cat command.
 */
public class Concatenate implements Command, ErrorHandler {

  /**
   * Valid output
   */
  StringBuilder sb;

  /**
   * Initializes a StringBuiler used for final output.
   */
  public Concatenate() {
    sb = new StringBuilder();
  }

  @Override
  public void output(String text, String type, String path, Directory currDir, String cmd) {
    if (type.equals("build")) {
      sb.append(text);
    } else {
      Output.stdOutput(text, type, path, currDir, cmd);
    }
  }

  @Override
  public String printDescription() {
    return "cat FILE1 [FILE2 ...] [> OUTFILE] \n" + "If OUTFILE is not provided,"
        + "Display the contents of FILE1 and other files (i.e. FILE2 ...) "
        + "concatenated in the shell.  \nOtherwise, sets contents of OUTFILE to "
        + "the contents of FILE1 and other files (i.e. FILE2 ...). "
        + "This creates a new file if OUTFILE does not exist." + "\n\n"
        + "cat FILE1 [FILE2 ...] >> OUTFILE \n"
        + "Appends the conents of FILE1 and other files (i.e. FILE2 ...) to OUTFILE"
        + "This creates a new file if OUTFILE does not exist.";
  }

  @Override
  public void displayError(int code, String arg) {
    if (code == 0) {
      // "Path does not exist" error message
      Output.stdError("\n\n\ncat: " + arg + " is not an existing path");
    }
    if (code == 1) {
      // "File does not exist" error message
      Output.stdError("\n\n\ncat: " + arg + " is not an existing file");
    }
  }

  @Override
  public boolean checkInput(int code, String arg, Directory currDir) {
    if (code == 0) {
      // Check if given path exists
      if (currDir.search(arg) != null) {
        return true;
      }
      displayError(0, arg);
    } else if (code == 1) {
      // Check if given path is a file
      if (currDir.search(arg).isFile()) {
        return true;
      }
      displayError(1, arg);
    } else if (code == 2) {
      // Check if output is to a file
      if (!arg.equals("")) {
        return true;
      }
    }

    return false;
  }

  /**
   * Outputs the contents of the file specified by <code>path</code>.
   * 
   * @param path A file.
   * @param currDir The current directory in the shell.
   */
  private void cat(String path, Directory currDir, String[] redirectionArgs) {
    // Print file contents
    File f = (File) currDir.search(path);
    if (checkInput(2, redirectionArgs[0], currDir)) {
      output(f.getContent(), "build", redirectionArgs[1], currDir, "cat");
    } else {
      output(f.getContent(), "", redirectionArgs[1], currDir, "cat");
    }
  }

  @Override
  public Directory run(String[] args, Directory currDir, String[] redirectionArgs) {
    int arguments = args.length;
    if (!redirectionArgs[0].equals("")) {
      arguments -= 2;
    }

    // Print the file contents of each given file, up to the first invalid file
    for (int i = 1; i < arguments; i++) {
      if (checkInput(0, args[i], currDir) && checkInput(1, args[i], currDir)) {
        // Add three line breaks between each file content output
        if (i > 1) {
          if (checkInput(2, redirectionArgs[0], currDir)) {
            output("\n\n\n\n", "build", redirectionArgs[1], currDir, "cat");
          } else {
            output("\n\n", "", redirectionArgs[1], currDir, "cat");
          }
        }
        cat(args[i], currDir, redirectionArgs);
      } else {
        break;
      }
    }

    // Write/append StringBuilder output to file only if file redirection is chosen
    if (checkInput(2, redirectionArgs[0], currDir)) {
      output(sb.toString(), redirectionArgs[0], redirectionArgs[1], currDir, "cat");
    }

    return currDir;
  }

  @Override
  public int expectedNumOfArgs() {
    // 1 argument is needed, more is optional
    return -2;
  }
}

