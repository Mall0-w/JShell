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

import java.util.Iterator;
import entity.Directory;
import entity.Path;
import entity.File;
import interfaces.Command;
import interfaces.ErrorHandler;
import io.Output;

/**
 * This class implements the rm command.
 */
public class RemoveDirectory implements Command, ErrorHandler {

  @Override
  public void output(String text, String type, String path, Directory currDir, String cmd) {
    Output.stdOutput(text, type, path, currDir, cmd);
  }

  @Override
  public String printDescription() {
    return "rm DIR \nRemoves directory DIR from the file system. "
        + "This also removes all the children of DIR";
  }

  @Override
  public void displayError(int code, String arg) {
    if (code == 0) {
      Output.stdError("rm: cannot remove current working directory");
    } else if (code == 1) {
      Output.stdError("rm: cannot remove root directory");
    } else if (code == 2) {
      Output.stdError("rm: cannot delete parent directory");
    } else if (code == 3) {
      Output.stdError("rm: Directory \"" + arg + "\" Does not exist");
    }

  }

  @Override
  public boolean checkInput(int code, String arg, Directory currDir) {
    return false;
  }

  /**
   * Removes directory or File whose path is given by <code>current</code>
   * 
   * @param current The path of the File or Directory to be deleted
   */
  public void rm(Path current) {
    if (current != null) {
      if (current instanceof File) {
        ((Directory) current.getParent()).getChilds().replace(((File) current).getName(), null);
        ((Directory) current.getParent()).getChilds().remove(((File) current).getName());
      } else {
        Iterator<String> i = ((Directory) current).getChilds().keySet().iterator();
        while (i.hasNext()) {
          String s = i.next();
          if (!(s.equals("..") || s.equals("."))) {
            i.remove();
            rm(((Directory) current).getChilds().get(s));

          }
          ((Directory) current.getParent()).getChilds().replace(current.getName(), null);
          ((Directory) current.getParent()).getChilds().remove(current.getName());
        }
      }
    }
  }


  @Override
  public Directory run(String[] args, Directory currDir, String[] redirectionArgs) {
    if (!redirectionArgs[0].equals("") && !redirectionArgs[1].equals("")) {
      output("", "error", redirectionArgs[1], currDir, "rm");
      return currDir;
    }

    Path current = (Path) currDir.search(args[1]);
    if (current == null || current instanceof File) {
      displayError(3, args[1]);
    } else if (current == currDir) {
      displayError(0, args[1]);
    } else if (current == currDir.getRoot()) {
      displayError(1, args[1]);
    } else if (current instanceof Directory
        && ((Directory) current).getIndirectChilds().contains(currDir)) {
      displayError(2, args[1]);
    } else {
      rm((Path) currDir.search(args[1]));
    }

    return currDir;
  }

  @Override
  public int expectedNumOfArgs() {
    return 1;
  }

}
