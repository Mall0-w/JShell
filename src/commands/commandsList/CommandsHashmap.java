package commands.commandsList;

import java.util.*;

/**
 * This class implements the hashmap with all valid commands and their corresponding class file
 * locations.
 */
public class CommandsHashmap {
  /**
   * HashMap containing all existing commands and their class locations.
   */
  private static final HashMap<String, String> commandsHashMap = new HashMap<>();

  /**
   * Creates a hashmap of all the commands and their file locations.
   */
  static {
    commandsHashMap.put("cat", "commands.Concatenate");
    commandsHashMap.put("cd", "commands.ChangeDirectory");
    commandsHashMap.put("cp", "commands.Copy");
    commandsHashMap.put("curl", "commands.ClientURL");
    commandsHashMap.put("echo", "commands.Echo");
    commandsHashMap.put("exit", "commands.Exit");
    commandsHashMap.put("history", "commands.History");
    commandsHashMap.put("loadJShell", "commands.LoadJShell");
    commandsHashMap.put("ls", "commands.ListFiles");
    commandsHashMap.put("man", "commands.Manual");
    commandsHashMap.put("mkdir", "commands.MakeDirectory");
    commandsHashMap.put("mv", "commands.Move");
    commandsHashMap.put("popd", "commands.PopDirectory");
    commandsHashMap.put("pushd", "commands.PushDirectory");
    commandsHashMap.put("pwd", "commands.PrintWorkingDirectory");
    commandsHashMap.put("rm", "commands.RemoveDirectory");
    commandsHashMap.put("saveJShell", "commands.SaveJShell");
    commandsHashMap.put("search", "commands.Search");
    commandsHashMap.put("search", "commands.Search");
    commandsHashMap.put("tree", "commands.Tree");
  }

  /**
   * Returns the commands HashMap.
   * 
   * @return Commands HashMap.
   */
  public static HashMap<String, String> getCommandsHashMap() {
    return commandsHashMap;
  }

  /**
   * Returns the list of commands.
   * 
   * @return A list of the commands.
   */
  public static List<String> getCommandsList() {
    return new ArrayList<String>(commandsHashMap.keySet());
  }

  /**
   * Returns true if the command exists, otherwise return false.
   * 
   * @param command
   * @return True if the command exists, otherwise false.
   */
  public static boolean containsCommand(String command) {
    return commandsHashMap.containsKey(command);
  }

  /**
   * Returns the class location of a command.
   * 
   * @param command A valid command.
   * @return The class location.
   */
  public static String getCommandClassName(String command) {
    return commandsHashMap.get(command);
  }

}
