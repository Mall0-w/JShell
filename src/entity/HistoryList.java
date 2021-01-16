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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// public enum HistoryList implements Serializable {
// INSTANCE;
//
// private ArrayList<String> historyList = new ArrayList<>();
//
// public void addCommand(String command){
// historyList.add(command);
// }
//
// public List<String> getHistoryList(){
// return historyList;
// }
//
// public void setHistoryList(ArrayList<String> newHistoryList){
// Collections.copy(historyList,newHistoryList);
// }
//
// }

public class HistoryList implements Serializable {

  private ArrayList<String> historyList;


  public void addCommand(String command) {
    historyList.add(command);
  }

  public void initializeHistoryList() {
    historyList = new ArrayList<>();
  }

  public ArrayList<String> getHistoryList() {
    return historyList;
  }

  public void setNewHistoryList(ArrayList<String> newHistoryList) {
    String lastElem = historyList.get(historyList.size() - 1);
    newHistoryList.add(lastElem);
    historyList = newHistoryList;
  }


}
