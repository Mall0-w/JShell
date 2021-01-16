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

import interfaces.*;
import io.*;
import entity.Directory;
import commands.commandsList.CommandsHashmap;

/**
 * This class implements the man command.
 */
public class Manual implements Command, ErrorHandler {


  @Override
  public void output(String text, String type, String path, Directory currDir, String cmd) {
    Output.stdOutput(text, type, path, currDir, cmd);
  }

  @Override
  public String printDescription() {
    return "man CMD [> OUTFILE] \n" + "If OUTFILE is not given, "
        + "prints documentation for CMD.  Otherwise, set the contents of OUTFILE "
        + "to the documentation for CMD;\nif OUTFILE doesn't exist, a new file will"
        + " be created\n\n" + "man CMD >> OUTFILE\n"
        + "Appends the documentation for CMD to OUTFILE.  If OUTFILE doesn't exist, "
        + "a new file will be created";
  }

  @Override
  public void displayError(int code, String arg) {
    if (code == -1) {
      Output.stdError("man: Command " + "\"" + arg + "\"" + " not found");
    }
  }

  @Override
  public boolean checkInput(int code, String args, Directory currDir) {
    return true;
  }

  /**
   * Outputs the manual description of command <code>cmd</code> or an error message if the command
   * does not exist.
   * 
   * @param cmd
   */
  public String man(String cmd, Directory currDir, String[] redirectArgs) {

    if (CommandsHashmap.containsCommand(cmd)) {

      try {
        // Setting up the command object
        String commandClassName = CommandsHashmap.getCommandClassName(cmd);
        Class<?> commandClass = Class.forName(commandClassName);
        Object commandObject = commandClass.getDeclaredConstructor().newInstance();
        Command command = (Command) commandObject;

        // Want the description of the command
        String description = command.printDescription();
        Output.stdOutput(description, redirectArgs[0], redirectArgs[1], currDir, "man");
        return command.printDescription();
      } catch (Exception e) {
        e.printStackTrace();
      }

    } else {
      displayError(-1, cmd);
    }
    return null;
  }

  @Override
  public Directory run(String[] args, Directory currDir, String[] redirectionArgs) {

    // for (int i = 1; i < args.length; i++) {
    // man(args[i], currDir, redirectionArgs);
    // }
    man(args[1], currDir, redirectionArgs);
    return currDir;
  }

  @Override
  public int expectedNumOfArgs() {
    // Must have 1 argument (command name you want manual of)
    return 1;
  }
}
