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

import java.io.Serializable;

/**
 * A part of the file system. A Path can be a File or Directory.
 */
public class Path implements Serializable {
  /**
   * the object that represents the parent of the current path
   */
  private Path parent = null;
  // TODO: Anyway to make root not static and be able to not break our code?
  /**
   * the root path aka "/"
   */
  private Path root = null;
  /**
   * name of the current path
   */
  private String name;

  /**
   * Keeps track of file version
   */
  private static final long serialVersionUID = 1L;


  /**
   * F for File type, D for directory type.
   */
  public static enum Type {
    F, D
  }

  /**
   * This constructor should only be used once in the entire program. This constructor will create
   * the root directory, and set the static root variable for all path from now on
   */
  public Path() {}

  /**
   * Constructor when only have name
   * 
   * @param name
   */
  public Path(String name) {
    this.name = name;
  }

  /**
   * constructor for when have both name and parent path
   * 
   * @param name
   * @param parent
   */
  public Path(String name, Path parent) {
    this.name = name;
    this.parent = parent;
  }

  /**
   * Sets the name of the file or directory.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the name of the parent path.
   * 
   * @param parent The initial parent path.
   */
  public void setParent(Path parent) {
    this.parent = parent;
  }

  /**
   * Returns the name of the file or directory.
   * 
   * @return The file/directory name.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the name of the parent path.
   * 
   * @return The file/directory name.
   */
  public Path getParent() {
    return parent;
  }

  /**
   * Returns the absolute path of the path object.
   * 
   * @return The absolute path.
   */
  public String getPath() {
    if (this == getRoot()) {
      return "/";
    } else if (parent == getRoot()) {
      return "/" + name;
    }
    StringBuilder sb = new StringBuilder();
    sb.append(parent.getPath()).append("/").append(name);
    return sb.toString();
  }

  /**
   * Returns a boolean value regarding whether the path is a directory.
   * 
   * @return True if this path is specifically a directory, false otherwise.
   */
  public boolean isDirectory() {
    return this.getClass() == Directory.class;
  }

  /**
   * Returns a boolean value regarding whether the path is a file.
   * 
   * @return True if this path is specifically a file, false otherwise.
   */
  public boolean isFile() {
    return this.getClass() == File.class;
  }

  /**
   * Returns the root directory, aka the "/".
   * 
   * @return The root directory.
   */
  public Path getRoot() {
    return FileSystem.getInstance().getRoot();
  }

  /**
   * Returns the parent path as a string.
   *
   * @param path
   * @return the parent part of a multi part path.
   */
  public static String getParentPathString(String path) {
    String parentPath = path.replaceAll("/[^/]+$", "");
    if (parentPath.equals(path)) {
      parentPath = "";
    }
    return parentPath;
  }

  @Override
  public String toString() {
    return String.format("Path: %s", getPath());
  }


}
