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
import java.util.Stack;

/**
 * The stack of directories used in the command pushd and popd.
 */
public class DirectoryStack implements Serializable {


  private Stack<Directory> s;

  public void intializeStack(){
    s = new Stack<>();
  }


  /**
   *
   * Pushes a directory onto the Filesystem's directory stack
   */
  public void dirPush(Directory dir){
    FileSystem.getInstance().getDirectoryStack().addOntoStack(dir);
  }


  /**
   *
   * Pushes a directory onto the current DirectoryStack's stack. Should only be used
   * by the DirectoryStack object located in FileSystem
   */
  public void addOntoStack(Directory dir){
    s.push(dir);
  }
  public Directory popFromStack(){
    if (s != null && !s.isEmpty()) {
      return s.pop();
    }
    return null;
  }


  /**
   *
   * Pops a directory from the Filesystem's directory stack
   */
  public Directory dirPop(){
    return FileSystem.getInstance().getDirectoryStack().popFromStack();
  }

  /**
   * Returns the directory stack.
   * 
   * @return The directory stack.
   */
  public Stack<Directory> getStack() {
    return FileSystem.getInstance().getDirectoryStack().getFileSystemStack();
  }

  public Stack<Directory> getFileSystemStack(){
    return s;
  }
}
