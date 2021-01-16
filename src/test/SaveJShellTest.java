package test;

import commands.SaveJShell;
import entity.FileSystem;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;


public class SaveJShellTest {

    FileSystem fs;
    SaveJShell sJShell;


    @Before
    public void setUp(){
        fs = FileSystem.getInstance();
        sJShell = new SaveJShell();
    }

    @Test
    public void saveFile(){
        String [] args = {"saveJShell", "mysave"};
        String [] redirection = {"",""};
        File mySaveFile = new File("./JShellSaveFiles/mysave.ser");

        sJShell.run(args,fs.getRoot(),redirection);
        assertTrue(mySaveFile.exists());
    }



}
