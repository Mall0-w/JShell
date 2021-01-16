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

import commands.History;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A system of file and directory type paths
 */


public class FileSystem implements Serializable {

  private static final long serialVersionUID = 1L;

  private Directory root;
  private HistoryList storedHistoryList;
  private DirectoryStack directoryStack;

  private Directory currDir;

  private FileSystem() {
    root = new Directory();
    storedHistoryList = new HistoryList();
    storedHistoryList.initializeHistoryList();

    directoryStack = new DirectoryStack();
    directoryStack.intializeStack();
    currDir = root;
  }

  private static class SingletonHelper {
    private static final FileSystem instance = new FileSystem();
  }

  public static FileSystem getInstance() {
    return SingletonHelper.instance;
  }

  public Directory getRoot() {
    return this.root;
  }

  public HistoryList getStoredHistoryList() {
    return this.storedHistoryList;
  }

  public DirectoryStack getDirectoryStack(){
    return this.directoryStack;
  }

  public void setNewFileSystem(FileSystem newFileSystem) {
    this.root = newFileSystem.getRoot();
    this.storedHistoryList.
            setNewHistoryList(newFileSystem.getStoredHistoryList().getHistoryList());
    this.directoryStack = newFileSystem.getDirectoryStack();
    this.currDir = newFileSystem.getCurrDir();
  }

  // Used for LoadJShell,
  // making sure that HistoryList only contains 1 command: the loadJShell command
  public boolean historyListContainsOneCommand() {
    return storedHistoryList.getHistoryList().size() == 1;
  }

  public Directory getCurrDir(){
    return this.currDir;
  }

  public void setCurrDir(Directory newCurrDir){
    this.currDir = newCurrDir;
  }

}
