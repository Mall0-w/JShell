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

package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.ListFiles;
import entity.Directory;
import entity.File;
import entity.FileSystem;
import entity.Path;

public class ListFilesTest {
  Directory currDir;
  FileSystem fs;
  ListFiles ls;

  @Before
  public void setUp() {
    fs = FileSystem.getInstance();
    currDir = fs.getRoot();
    ls = new ListFiles();

    currDir.create("d1", Path.Type.D);
    currDir.create("f1", Path.Type.F);
    ((Directory) currDir.getChilds().get("d1")).create("d2", Path.Type.D);
  }

  @Test
  public void testListFiles() {
    // testing ls without redirection
    Directory toSearch = ((Directory) currDir.getChilds().get("d1"));
    String[] redArgs = {"", ""};
    assertEquals("/d1:[d2]", ls.listFiles(toSearch, currDir, redArgs).toString());
  }

  @Test
  public void testListFilesRecursive() {

    StringBuilder sb = new StringBuilder();
    String[] redArgs = {"", ""};
    String expected = "/:\n" + "[f1, d1]\n" + "/d1:\n" + "[d2]\n" + "/d1/d2:\n" + "[]\n";
    assertEquals(expected, ls.listFilesRecursive(currDir, sb, currDir, redArgs).toString());
  }

  @Test
  public void testRunNormal() {
    currDir.create("d1", Path.Type.D);
    currDir.create("f1", Path.Type.F);
    ((Directory) currDir.getChilds().get("d1")).create("d2", Path.Type.D);

    String[] args = {"ls", "d1", ">>", "fx"};
    String[] redArgs = {">>", "fx"};
    assertEquals(currDir, ls.run(args, currDir, redArgs));
    System.out.println(currDir);
    assertTrue(currDir.getChilds().containsKey("fx"));
    assertEquals("/d1:\n[d2]\n\n", ((File) currDir.getChilds().get("fx")).getContent());
  }

  @Test
  public void testRunRecursive() {
    currDir.create("d1", Path.Type.D);
    currDir.create("f1", Path.Type.F);
    ((Directory) currDir.getChilds().get("d1")).create("d2", Path.Type.D);

    String[] args = {"ls", "-R", ">>", "fy"};
    String[] redArgs = {">>", "fy"};
    assertEquals(currDir, ls.run(args, currDir, redArgs));
    System.out.println(currDir);
    assertTrue(currDir.getChilds().containsKey("fy"));
    String expected = "/:\n" + "[fx, f1, d1]\n" + "\n" + "/d1:\n" + "[d2]\n"
        + "\n" + "/d1/d2:\n" + "[]\n\n";
    assertEquals(expected, ((File) currDir.getChilds().get("fy")).getContent());
  }

}
