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
import entity.Path;
import interfaces.Command;
import interfaces.ErrorHandler;
import io.Output;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class implements the search command.
 */
public class Search implements Command, ErrorHandler {

  @Override
  public void output(String text, String type, String path, Directory currDir, String cmd) {
    Output.stdOutput(text, type, path, currDir, cmd);
  }

  @Override
  public String printDescription() {
    StringBuilder sb =
        new StringBuilder("search path ... -type [f|d] -name expression [> OUTFILE]\n");
    sb.append(
        "If OUTFILE is not provided, For each path provided, search through its direct child\n");
    sb.append("If the path contains a child with the matching name expression and type\n");
    sb.append("Then print the path of file/directory of it ");
    sb.append("Otherwise, set the contents of OUTFILE to the path of file/directory of it.");
    sb.append("\nIf OUTFILE doesn't exist, a new file will be created\n");
    sb.append("\nsearch path ... -type [f|d] -name expression >> OUTFILE\n");
    sb.append("For each path provided, search through its direct child\n");
    sb.append("If the path contains a child with the matching name expression and type\n");
    sb.append("Then append the path of file/directory of it to OUTFILE");
    sb.append("if OUTFILE doesn't exist, a new file will be created\n");
    return sb.toString();
  }

  @Override
  public void displayError(int code, String arg) {
    if (code == 0) {
      Output.stdError(String.format("search: %s is not a valid path", arg));
    } else if (code == 1) {
      Output.stdError(String.format("search: %s is not a valid directory", arg));
    } else if (code == 2) {
      Output.stdError("search: Expected -type");
    } else if (code == 3) {
      Output.stdError("search: Expected f or d");
    } else if (code == 4) {
      Output.stdError("search: Expected -name");
    } else if (code == 5) {
      Output.stdError("search: name expression needs to be surrounded by quotation mark");
    } else if (code == 6) {
      Output.stdError(String.format("search: %s is not a valid name argument", arg));
    }
  }

  @Override
  public boolean checkInput(int code, String arg, Directory currDir) {
    if (code == 0) {
      return isValidPath(arg);
    } else if (code == 1) { // check if the folder exists
      Path pathToSearch = currDir.search(arg);
      return pathToSearch != null && pathToSearch.isDirectory();
    } else if (code == 2) {
      return arg.equals("-type");
    } else if (code == 3) {
      return arg.equals("f") || arg.equals("d");
    } else if (code == 4) {
      return arg.equals("-name");
    } else if (code == 5) {
      return arg.startsWith("\"") && arg.endsWith("\"");
    } else if (code == 6) {
      return isValidNameArg(arg);
    }
    return true;
  }

  /**
   * Searches file system for a path match.
   * 
   * @param args The search parameters.
   * @param currDir The current directory in the shell.
   * @return Search result.
   */
  public String search(String[] args, Directory currDir) {
    StringBuilder sbBig = new StringBuilder();
    for (int i = 1; i < args.length - 4
            && checkInputHelper(args, i, currDir); i++) {
      StringBuilder sb = new StringBuilder();
      String name = nameParser(args[args.length - 1]);
      Directory parent = (Directory) currDir.search(args[i]);
      ArrayList<Path> childs = parent.getDirectChilds();
      for (Path child : childs) {
        if (args[args.length - 3].equals("f")) {
          sb.append(searchHelper(child, name, Path.Type.F));
        } else if (args[args.length - 3].equals("d")) {
          sb.append(searchHelper(child, name, Path.Type.D));
        }
      }
      String temp = sb.toString();
      if (temp.isEmpty()) {
        if (args[args.length - 3].equals("f")) {
          temp = "File not found";
        } else {
          temp = "Directory not found";
        }
      }
      sbBig.append(temp);
    }
    return sbBig.toString();
  }

  /**
   * Searches file system for a path match. Helper method for search().
   * 
   * @param path A path object.
   * @param name A file name.
   * @param t The type of path (file or directory).
   * @return An updated search result.
   */
  private String searchHelper(Path path, String name, Path.Type t) {
    StringBuilder sb = new StringBuilder();
    if (path.getName().equals(name)) {
      if (t == Path.Type.F && path.isFile()) {
        sb.append(path.getPath()).append('\n');
      } else if (t == Path.Type.D && path.isDirectory()) {
        sb.append(path.getPath()).append('\n');
      }
    }
    if (!path.isDirectory()) {
      return sb.toString();
    }
    ArrayList<Path> childs = ((Directory) path).getDirectChilds();
    for (Path child : childs) {
      sb.append(searchHelper(child, name, t));
    }
    return sb.toString();
  }

  /**
   * Parsing the name argument for search purpose
   *
   * @param name argument from JShell
   * @return name argument after parsed
   */
  private String nameParser(String name) {
    if (name.contains("/")) {
      return name;
    }
    return name.substring(1, name.length() - 1);
  }

  /**
   * Bundle argument checking
   *
   * @param args arguments came from JShell
   * @param currDir the working directory
   * @return true if all arguments are what we expected
   */
  private boolean checkInputHelper(String[] args, int i, Directory currDir) {
    if (!checkInput(0, args[i], currDir)) {
      displayError(0, args[i]);
      return false;
    } else if (!checkInput(1, args[i], currDir)) {
      displayError(1, args[i]);
      return false;
    }
    if (!checkInput(2, args[args.length - 4], currDir)) {
      displayError(2, null);
      return false;
    } else if (!checkInput(3, args[args.length - 3], currDir)) {
      displayError(3, null);
      return false;
    } else if (!checkInput(4, args[args.length - 2], currDir)) {
      displayError(4, null);
      return false;
    } else if (!checkInput(5, args[args.length - 1], currDir)) {
      displayError(5, null);
      return false;
    } else if (!checkInput(6, args[args.length - 1], currDir)) {
      displayError(6, args[args.length - 1]);
      return false;
    }
    return true;
  }

  /**
   * Returns whether the file name contains invalid characters.
   *
   * @param path The file path.
   * @return True if the parent directory exists, and false otherwise.
   */
  private boolean isValidPath(String path) {
    ArrayList<String> invalidChars = new ArrayList<String>();
    invalidChars.addAll(Arrays.asList("@", "#", "$", "%", "^", "&", "*", "(", ")", "{", "}", "~",
        "|", "<", ">", "?", "//"));
    for (String c : invalidChars) {
      if (path.contains(c)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns true if the filename is valid, false if it contains an illegal character.
   * 
   * @param name A filename.
   * @return True if filename valid, false otherwise.
   */
  private boolean isValidNameArg(String name) {
    if (!(name.startsWith("\"") && name.endsWith("\""))) {
      return false;
    }
    ArrayList<String> invalidChars = new ArrayList<String>();
    invalidChars.addAll(Arrays.asList("@", "#", "$", "%", "^", "&", "*", "(", ")", "{", "}", "~",
        "|", "<", ">", "?", "//", "/"));
    for (String c : invalidChars) {
      if (name.contains(c)) {
        return false;
      }
    }
    return true;

  }

  @Override
  public Directory run(String[] args, Directory currDir, String[] redirectionArgs) {
    String output;

    if (!redirectionArgs[0].equals("") && !redirectionArgs[1].equals("")) {
      String newArgs[] = new String[args.length - 2];
      for (int i = 0; i < args.length - 2; i++) {
        newArgs[i] = args[i];
      }
      output = search(newArgs, currDir);
    } else {
      output = search(args, currDir);
    }

    if (output.endsWith("\n")) {
      output(output.substring(0, output.length() - 1), redirectionArgs[0], redirectionArgs[1],
          currDir, "search");
    } else {
      output(output, redirectionArgs[0], redirectionArgs[1], currDir, "search");
    }
    return currDir;
  }


  @Override
  public int expectedNumOfArgs() {
    return -5;
  }
}
