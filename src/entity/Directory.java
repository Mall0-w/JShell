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

package entity;

import interfaces.ErrorHandler;
import io.Output;

import java.util.ArrayList;
import java.util.HashMap;
import commands.invalidCharList.*;

/**
 * A part of the file system. A Directory can contain files or directories.
 */
public class Directory extends Path implements ErrorHandler {
  protected HashMap<String, Path> childs; // a map mapping child name to the direct childs

  /**
   * This constructor is reserved only for creating the root directory at the beginning
   */
  public Directory() {
    super();
    childs = new HashMap<>();
    childs.put(".", this);
  }

  /**
   * Constructor of Directory Set <code>this.name</code> to <code>name</code>
   * 
   * @param name
   */
  public Directory(String name) {
    super(name);
    childs = new HashMap<>();
    childs.put(".", this);
  }

  /**
   * Constructor with both name and parent
   * 
   * @param name
   * @param parent
   */
  public Directory(String name, Path parent) {
    super(name, parent);
    childs = new HashMap<>();
    childs.put(".", this);
    childs.put("..", parent);
  }

  /**
   * Create a path under the relative/absolute path pathString
   * 
   * @param pathString: Where we want entity to end up
   * @param entity: the path we want to insert
   * @return true if insert correctly
   */
  public boolean create(String pathString, Path entity) {
    if (!(entity instanceof Directory) && !(entity instanceof File)) {
      // TODO raise error, cannot add an entity that is neither file or dir
      displayError(0, null);
      return false;
    }
    return putChildWithPath(pathString, entity);
  }

  /**
   * Create a path of type t under the relative/absolute path pathString
   * 
   * @param pathString: Where we want entity to end up
   * @param t: the type of path we want to create
   * @return true if insert correctly
   */
  public boolean create(String pathString, Type t) {
    Path child = null;
    switch (t) {
      case F:
        child = new File(null);
        break;
      case D:
        child = new Directory(null);
        break;
    }

    return putChildWithPath(pathString, child);
  }

  /**
   * Insert a path object to the path specified by pathString
   *
   * @param pathString: Where we want child to end up at
   * @param child: The path object we are inserting
   * @return true if inserted successfully, false otherwise
   */
  private boolean putChildWithPath(String pathString, Path child) {

    if (pathString.contains("//")) {
      displayError(5, null);
      return false;
    }

    pathString = formatPathString(pathString);
    String parentPathString, childName;
    if (pathString.contains("/")) {
      parentPathString = pathString.substring(0, pathString.lastIndexOf('/') + 1);
      childName = pathString.substring(pathString.lastIndexOf('/') + 1);
      if (InvalidsList.isInvalid(childName) != null || InvalidsList.badExtension(childName)) {
        displayError(6, null);
        return false;
      }
    } else {
      parentPathString = "";
      childName = pathString;
      if (InvalidsList.isInvalid(childName) != null || InvalidsList.badExtension(childName)) {
        displayError(6, null);
        return false;
      }
    }

    Path parentPath = search(parentPathString);
    if (parentPath == null) {
      displayError(3, null);
      return false;
    } else if (!(parentPath instanceof Directory)) {
      displayError(4, null);
      return false;
    }

    Directory parent = (Directory) parentPath;

    child.setName(childName);
    child.setParent(parent);
    if (child instanceof Directory) {
      ((Directory) child).putChild("..", parent);
    }

    return parent.putChild(childName, child);
  }


  /**
   * Insert a child into the current directory
   *
   * @return true if the child is inserted correctly, false otherwise
   */
  private boolean putChild(String name, Path child) {
    if (containsDirectChild(name)) {
      // TODO raise error here, tell user cannot add path with same name
      displayError(1, null);
      return false;
    }
    childs.put(name, child);
    return true;
  }

  /**
   * Returns a Path type path given a String type path, if it exists. WARNING: This method only
   * checks if the path exists, it doesn't know if the path points to a file or directory
   *
   * @param pathString A String type path.
   * @return A Path type path if the path exists, otherwise null.
   */
  public Path search(String pathString) {
    if (pathString.contains("//")) { // invalid path
      return null;
    }

    pathString = formatPathString(pathString);


    if (pathString.length() == 0) {
      return this;
    }

    Path currOn = (pathString.charAt(0) == '/') ? getRoot() : this;
    String[] args = pathString.split("/");
    for (int i = 0; i < args.length; i++) {
      if (args[i].length() == 0) {
        continue;
      }
      if (currOn == null || currOn instanceof File) {
        return null;
      }
      currOn = ((Directory) currOn).getDirectChild(args[i]);
    }
    return currOn;
  }

  /**
   * Check if a direct child named name exists
   * 
   * @param name
   * @return true of there is a child named name under the current dir, false o/w
   */
  private boolean containsDirectChild(String name) {
    return childs.containsKey(name);
  }

  /**
   * Get a direct child with name name
   * 
   * @param name
   * @return a path object of the object is a direction of the current dir and is named name o/w
   *         null
   */
  public Path getDirectChild(String name) {
    if (containsDirectChild(name)) {
      return childs.get(name);
    }
    // displayError(2, name);
    return null;
  }

  /**
   * Returns a path string that is formatted by removing any unnecessary characters in the path
   * string.
   * 
   * @param pathString An unformatted path string.
   * @return A formatted path string.
   */
  public static String formatPathString(String pathString) {
    // String newString = pathString.replaceAll("/+", "/");

    /*
     * if (newString.length() > 1 && newString.charAt(newString.length() - 1) == '/') { return
     * newString.substring(0, newString.length() - 1); } return newString;
     */

    if (pathString.contains("//")) {
      return pathString;
    }
    if (pathString.length() > 1 && pathString.charAt(pathString.length() - 1) == '/') {
      return pathString.substring(0, pathString.length() - 1);
    }
    return pathString;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(String.format("Path: %s, Childs: ", getPath()));
    for (String name : childs.keySet()) {
      sb.append(name).append(", ");
    }
    String result = sb.toString();
    return result.substring(0, result.length() - 2);
  }

  /**
   * @return a list of name of the direct child of this directory
   */
  public ArrayList<String> getChildNames() {
    ArrayList<String> names = new ArrayList<>();
    for (String s : childs.keySet()) {
      if (!s.equals(".") && !s.equals("..")) {
        names.add(s);
      }
    }
    return names;
  }

  public ArrayList<Path> getDirectChilds() {
    ArrayList<Path> childs = new ArrayList<>();
    for (String s : this.childs.keySet()) {
      if (!s.equals(".") && !s.equals("..")) {
        childs.add(this.childs.get(s));
      }
    }
    return childs;
  }

  public ArrayList<Path> getIndirectChilds() {
    ArrayList<Path> childs = new ArrayList<>();
    for (String s : this.childs.keySet()) {
      if (!s.equals(".") && !s.equals("..")) {
        if (this.childs.get(s) instanceof Directory) {
          childs.addAll(((Directory) this.childs.get(s)).getIndirectChilds());
        } else {
          childs.add(this.childs.get(s));
        }
      }
    }
    childs.add(this);
    return childs;

  }

  @Override
  public void displayError(int code, String arg) {
    switch (code) {
      case 0:
        Output.stdOutput("Background: Invalid entity, try insert File or Directory", null, null,
            null, null);
        break;
      case 1:
        Output.stdOutput("System: Duplicate path detected", null, null, null, null);
        break;
      case 2:
        Output.stdOutput("System: Cannot find direct child with name: " + arg, null, null, null,
            null);
        break;
      case 3:
        Output.stdOutput("System: Cannot add a file or directory under a non-existent path", null,
            null, null, null);
        break;
      case 4:
        Output.stdOutput("System: Cannot add a file or directory under a non-directory path", null,
            null, null, null);
        break;
      case 5:
        Output.stdOutput("System: Invalid path", null, null, null, null);
      case 6:
        Output.stdOutput("System: Invalid File Name", null, null, null, null);
    }
  }

  @Override
  public boolean checkInput(int code, String arg, Directory currDir) {
    return true;
  }

  public HashMap<String, Path> getChilds() {
    return this.childs;
  }

}
