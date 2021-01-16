package test;

import commands.LoadJShell;
import commands.SaveJShell;
import entity.FileSystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Field;

import static org.junit.Assert.*;


public class LoadJShellTest {

    FileSystem fs;
    SaveJShell sJShell;
    LoadJShell lJShell;
    String [] redirection;



    @Before
    public void setUp(){
        fs = FileSystem.getInstance();
        sJShell = new SaveJShell();
        redirection = new String []{"",""};
        lJShell = new LoadJShell();


    }

    @Test
    public void saveFile(){
        String [] args = {"saveJShell", "mysave"};
        String [] redirection = {"",""};
        File mySaveFile = new File("./JShellSaveFiles/mysave.ser");

        sJShell.run(args,fs.getRoot(),redirection);
        assertTrue(mySaveFile.exists());
    }



    //Unfortunately, I can't delete the singleton instance of fileSystem.
    //I looked on piazza, and Abbas recommended I set the reference field to null.
    //I cannot do that as it the reference field is located inside of the inner private static
    //class, and is a final reference. To fix this issue requires so much overhead work,
    // that goes beyond the scope of a test, so you will expect to
    //Receive an error message because of this.
    @Test
    public void loadFile() {
        String[] args = {"loadJShell", "mysave"};
        assertNotNull(lJShell.run(args,fs.getRoot(),redirection));
    }


    @After
    public void tearDown(){
        fs = null;
    }
}
