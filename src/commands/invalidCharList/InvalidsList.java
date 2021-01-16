package commands.invalidCharList;

import java.util.ArrayList;
import java.util.Arrays;

public class InvalidsList {
  /**
   * A list of invalid file name characters.
   */
  private static final ArrayList<Character> invalidChars = new ArrayList<Character>();

  /**
   * Adds all invalid characters to the ArrayList.
   */
  static {
    invalidChars.addAll(Arrays.asList('!','@', '#', '$', '%', '^', '&', '*', '(', ')', '{', '}', '~',
        '|', '<', '>', '?'));
  }

  /**
   * Returns the list of invalid file name characters.
   * 
   * @return An ArrayList of all invalid file name characters.
   */
  public static ArrayList<Character> getInvalids() {
    return invalidChars;
  }

  /**
   * Returns the invalid character if it exists in the string, returns null otherwise.
   * 
   * @param s A string.
   * @return The invalid chracter if it exists, null otherwise.
   */
  public static Character isInvalid(String s) {
    for (int i = 0; i < s.length(); i++) {
      if (invalidChars.contains(s.charAt(i))) {
        return s.charAt(i);
      }
    }
    return null;
  }

  public static boolean badExtension(String s) {
    if (!(s.contains("."))) {
      return false;
    }
    if (s.contains(".") && s.length() > 2 && s.charAt(s.length() - 1) != '.'
        && s.charAt(0) != '.') {
      char[] temp = s.toCharArray();
      boolean check1 = false;
      for (int i = 0; i < temp.length; i++) {
        if (temp[i] == '.') {
          if (check1) {
            return true;
          }
          check1 = true;
        }
      }
      return false;
    }
    return true;
  }

}
