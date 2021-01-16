// **********************************************************
// Assignment2:

// Student1:
// Author: Carlos Fei Huang
//
// Student2: Kyle Lewis
// Author: Kyle Lewis
//
// Student3: Glenn Qing Yuan Ye
// Author: Glenn Qing Yuan Ye
//
// Student4: Youzhang Sun (Mark)
// Author: Youzhang Sun
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package driver;

import java.util.Scanner;

import commands.commandsList.CommandsHashmap;
import interfaces.Command;
import io.InputParser;
import entity.*;
import io.InputValidater;

/**
 * <h1>Java Mock Shell</h1> The Java Mock Shell program implements a simplified version of the Unix
 * shell.
 * 
 * @author Youzhang Sun (Mark), Kyle Lewis, Carlos Fei Huang, Glenn Qing Yuan Ye
 * @version 1.0
 */
public class JShell {



  /**
   *
   * @param commandClassName name of the Java class file we want
   * @return an object of the Java class intialized. Returns null if something went wrong
   */
  private static Command getCommandObj(String commandClassName) {
    // Already checked to make sure that the command exists.

    Command command = null;
    try {
      Class<?> commandClass = Class.forName(commandClassName);
      Object commandObject = commandClass.getDeclaredConstructor().newInstance();

      // creates the actual command
      // If there's an error, then either wrong Class file name or typo in CommandsHashMap
      command = (Command) commandObject;

    } catch (Exception e) {
      System.out.println("Something messed up here, probably in backend code");
      e.printStackTrace();
    }

    return command;
  }

  /**
   * Launches the Java Mock Shell in the console. First checks if the command inputed exists, then
   * if the number of arguments given is valid, before finally running the command and updating the
   * current directory.
   * 
   * @param args Unused.
   *
   */

    public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    FileSystem fs = FileSystem.getInstance();
    Path currDir = fs.getCurrDir();
    InputParser inputParser = new InputParser();
    InputValidater inputValidater = new InputValidater();
    String input;
    String[] parsed;

    while (true) {
      System.out.print(currDir.getPath() + ": ");
      input = sc.nextLine();
      if (input.strip().isEmpty()) {
        continue;
      }
      parsed = inputParser.parseToArray(input);

      if (!CommandsHashmap.containsCommand(parsed[0])) {
        System.out.println("Error: command \"" + parsed[0] + "\" not found");
      } else {
        String commandClassName = CommandsHashmap.getCommandClassName(parsed[0]);
        Command currCommandObj = getCommandObj(commandClassName);
        if (!inputValidater.validNumberOfArguments(currCommandObj, parsed)) {
          System.out.println("Error: invalid number of arguments");
        } else {
          String redirectionArgs[] = inputParser.getRedirectionArgs(parsed, currCommandObj);
          FileSystem.getInstance().
                  setCurrDir( currCommandObj.run(parsed, (Directory) currDir, redirectionArgs));
          currDir = FileSystem.getInstance().getCurrDir();
        }
      }
    }
  }


}
