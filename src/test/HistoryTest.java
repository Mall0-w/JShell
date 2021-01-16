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

import commands.History;
import entity.FileSystem;
import entity.HistoryList;
import org.junit.*;

import java.util.ArrayList;

public class HistoryTest {


    FileSystem fs;
    HistoryList hl;
    History his;


    @Before
    public void setUp(){
        fs = FileSystem.getInstance();
        hl = fs.getStoredHistoryList();
        hl.addCommand("sample command one");
        hl.addCommand("mkdir d1");
        hl.addCommand("Sample third command");
        hl.addCommand("latest command");
        his = new History();
    }

    @Test
    public void testWithoutNumber(){
        ArrayList<String> templist = new ArrayList<>();
        templist.add("1. sample command one");
        templist.add("2. mkdir d1");
        templist.add("3. Sample third command");
        templist.add("4. latest command");
        assertEquals(templist,his.outputHistoryList());
    }

    @Test
    public void testWithNumber(){
        ArrayList<String> templist = new ArrayList<>();
        templist.add("2. mkdir d1");
        templist.add("3. Sample third command");
        templist.add("4. latest command");
        assertEquals(templist,his.outputHistoryList(3));

    }

    @Test
    public void testWithNumberOverLimit(){
        ArrayList<String> templist = new ArrayList<>();
        templist.add("1. sample command one");
        templist.add("2. mkdir d1");
        templist.add("3. Sample third command");
        templist.add("4. latest command");
        assertEquals(templist,his.outputHistoryList(10));

    }

    @After
    public void tearDown(){
        fs = null;
    }


}
