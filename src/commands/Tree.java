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
import entity.FileSystem;
import entity.Path;
import interfaces.Command;
import io.Output;

/**
 * This class implements the tree command.
 */
public class Tree implements Command {

  /**
   * File system object used to construct tree.
   */
  FileSystem fs = FileSystem.getInstance();

  @Override
  public void output(String text, String type, String path, Directory currDir, String cmd) {
    Output.stdOutput(text, type, path, currDir, cmd);
  }

  @Override
  public String printDescription() {
    StringBuilder sb = new StringBuilder("tree [> OUTFILE] \n");
    sb.append(
        "If OUTFILE is not provided, starting from the root directory ('\\'), display the entire file\n");
    sb.append("system as a tree. For every level of the tree, you must indent by a tab character.");
    sb.append("\nOtherwise, set the contents of OUTFILE to the entire system as a tree ");
    sb.append("if OUTFILE does not exist, a new file will be created\n\n");
    sb.append("tree >> OUTFILE \n");
    sb.append("Staring from the root directory ('\\'), append the entire file \n");
    sb.append("system as a tree. For every level of the tree, you must indent by a tab character.");
    sb.append(" If OUTFILE does not exist, a new file will be created");
    return sb.toString();
  }

  /**
   * Creates a tree representing the whole file system.
   * 
   * @param currDir The current directory in the shell.
   * @return The tree of the file system.
   */
  public String tree(Directory currDir) {
    StringBuilder sb = new StringBuilder("\\\n");
    Directory root = fs.getRoot();
    for (Path child : root.getDirectChilds()) {
      sb.append(runHelper(child, 1));
    }
    return sb.toString();
  }

  /**
   * Formats the tree.
   * 
   * @param currDir The current directory of the shell.
   * @param indent The indentation level of the tree node.
   * @return The tree of the file system.
   */
  private String runHelper(Path currDir, int indent) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < indent; i++) {
      sb.append('\t');
    }
    sb.append(currDir.getName()).append('\n');
    if (!currDir.isDirectory()) {
      return sb.toString();
    }
    for (Path child : ((Directory) currDir).getDirectChilds()) {
      sb.append(runHelper(child, indent + 1));
    }
    return sb.toString();
  }

  @Override
  public Directory run(String[] args, Directory currDir, String[] redirectionArgs) {
    String output = tree(currDir);
    if (output.endsWith("\n")) {
      output(output.substring(0, output.length() - 1), redirectionArgs[0], redirectionArgs[1],
          currDir, "tree");
    } else {
      output(output, redirectionArgs[0], redirectionArgs[1], currDir, "tree");
    }
    return currDir;
  }


  @Override
  public int expectedNumOfArgs() {
    return 0;
  }
}
