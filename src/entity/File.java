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

/**
 * A part of the file system. A File can store a string of text.
 */
public class File extends Path {
  /**
   * The content of the file.
   */
  private String content;

  /**
   * Empty File constructor.
   */
  public File() {

  }

  /**
   * Constructor with only name, set content to an empty string
   * 
   * @param name
   */
  public File(String name) {
    super(name);
    content = "";
  }

  /**
   * Constructor with name and parent, set content to an empty string
   * 
   * @param name
   */
  public File(String name, Path parent) {
    super(name, parent);
    content = "";
  }

  /**
   * Constructor with name and parent, also set the content as a certain value
   * 
   * @param name
   * @param parent
   * @param content
   */
  public File(String name, Path parent, String content) {
    super(name, parent);
    this.content = content;
  }


  /**
   * @return the content in the file
   */
  public String getContent() {
    return content;
  }

  /**
   * Overwrite all the content in file with just newContent
   * 
   * @param newContent
   */
  public void setContent(String newContent) {
    content = newContent;
  }

  // CARLOS: Might want to change to Stringbuilder if there are constant updates

  /**
   * Add newContent to the end of the file
   * 
   * @param newContent
   */
  public void appendContent(String newContent) {
    content = content + newContent;
  }

  @Override
  public String toString() {
    String result = String.format("Path: %s, Content: %s", getPath(), content);
    return result;
  }
}
