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
import java.util.Arrays;
import entity.Directory;
import entity.File;
import entity.Path;

/**
 * This class implements the ability for commands to redirect their output to a file instead of to
 * the shell.
 */
public class OutputRedirection {
  /**
   * Returns whether the path exists. There is no error message outputed if file does not exist.
   * 
   * @param currDir The current directory.
   * @param path The file path.
   * @return True if the path exists, and false otherwise.
   */
  private static boolean pathExistsQuiet(Directory currDir, String path) {
    if (currDir.search(path) != null) {
      return true;
    }
    return false;
  }

  /**
   * Returns whether the path is a file.
   * 
   * @param currDir The current directory.
   * @param path The file path.
   * @return True if the path is a file, and false otherwise.
   */
  private static boolean pathIsFile(Directory currDir, String path) {
    if (pathExistsQuiet(currDir, path) && currDir.search(path) instanceof File) {
      return true;
    }
    return false;
  }

  /**
   * Returns whether the path is a directory.
   * 
   * @param currDir The current directory.
   * @param path The file path.
   * @return True if the path is a file, and false otherwise.
   */
  private static boolean pathIsDirectory(Directory currDir, String path) {
    if (pathExistsQuiet(currDir, path) && currDir.search(path) instanceof Directory) {
      return true;
    }
    return false;
  }

  /**
   * Returns whether the parent directory exists.
   * 
   * @param currDir The current directory.
   * @param path The file path.
   * @return True if the parent directory exists, and false otherwise.
   */
  private static boolean parentPathExists(Directory currDir, String path) {
    String parentPath = Directory.getParentPathString(path);
    if (currDir.search(parentPath) != null) {
      return true;
    }
    return false;
  }

  /**
   * Returns whether the file name contains invalid characters.
   * 
   * @param currDir The current directory.
   * @param path The file path.
   * @return True if the parent directory exists, and false otherwise.
   */
  private static boolean fileNameInvalid(Directory currDir, String path) {
    ArrayList<String> invalidChars = new ArrayList<String>();
    invalidChars.addAll(Arrays.asList(".", "@", "#", "$", "%", "^", "&", "*", "(", ")", "{", "}",
        "~", "|", "<", ">", "?"));
    for (String c : invalidChars) {
      if (path.contains(c)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Either appends or overwrites the file with text.
   * 
   * @param type The type of redirection.
   * @param f The file to write to.
   * @param text The text that will be written to the file.
   */
  private static void chooseWriteType(String type, File f, String text) {
    if (type.equals(">")) {
      f.setContent(text);
    } else if (type.equals(">>")) {
      f.appendContent("\n" + text);
    }
  }

  /**
   * Returns a line of user input that correctly parses the redirect symbol, such that spacing will
   * be added between redirect symbol.
   * 
   * @param line A String of user input.
   * @return A String of user input with the redirect symbol correctly parsed.
   */
  public static String redirectSymbolParse(String line) {
    if (line.contains(">>")) {
      line = line.replace(">>", " >> ");
    } else if (line.contains(">")) {
      line = line.replace(">", " > ");
    }

    return line;
  }

  /**
   * Returns a line of user input that correctly parses the redirect symbol within the text string,
   * such that any unnecessary whitespace around the redirect symbol is removed.
   * 
   * @param line A String of user input.
   * @return A String of user input with the redirect symbol in the text String correctly parsed.
   */
  public static String redirectSymbolInStringParse(String line) {
    if (line.contains(" >> ")) {
      line = line.replace(" >> ", ">>");
    } else if (line.contains(" > ")) {
      line = line.replace(" > ", ">");
    }

    return line;
  }

  /**
   * Redirects command output to a file. If the given file does not exist and is under an existing
   * directory, creates a new file and redirects the output there.
   * 
   * @param type The type of redirection.
   * @param text The text that will be written to the file.
   * @param path The file that the output will redirect to.
   * @param currDir The current directory.
   * @param cmd The command that is using output redirection.
   */
  public static void outputRedirect(String type, String text, String path, Directory currDir,
      String cmd) {
    File f;

    if (path.contains("//") || path.substring(path.length() - 1).equals("/")) {
      Output.stdError("System: Can only redirect output to a file path");
      return;
    }

    boolean pathInvalid = parentPathExists(currDir, path);

    if (pathExistsQuiet(currDir, path) && pathIsFile(currDir, path)) {
      f = (File) currDir.search(path);
      chooseWriteType(type, f, text);
    } else if (pathIsDirectory(currDir, path)) {
      Output.stdError(cmd + ": Cannot write a string into a directory");
      return;
    } else if (!pathExistsQuiet(currDir, path) && parentPathExists(currDir, path)) {
      if (fileNameInvalid(currDir, path)) {
        Output.stdError(cmd + ": File name is invalid");
        return;
      }
      currDir.create(path, Path.Type.F);
      f = (File) currDir.search(path);
      f.setContent(text);
    }

    if (!pathInvalid) {
      Output.stdError(cmd + ": Cannot create a file under non-existent directory(s)");
    }
  }
}
