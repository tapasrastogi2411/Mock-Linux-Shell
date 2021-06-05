// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: kharolba
// UT Student #: 1006045496
// Author: Balraj Kharol
//
// Student2:
// UTORID user_name: amirrazi
// UT Student #: 1005813880
// Author: Anaqi Amir Razif
//
// Student3:
// UTORID user_name: doshish2
// UT Student #: 1005716940
// Author: Shashwat Piyush Doshi
//
// Student4:
// UTORID user_name: rastog32
// UT Student #: 1005734608
// Author: Tapas Rastogi
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************

package test;

import org.junit.*;
import static org.junit.Assert.*;
import commands.*;

import java.io.*;
import java.lang.reflect.Field;

public class SaveJShellTest {
  /**
   * Define instance of a FileSystem so that we can use it for testing
   */
  private static FileSystem fs;

  /**
   * Variable for using the concept of reflection in testing
   */
  Field field;

  /**
   * MakeDirectory variable to be used for testing saveJShell methods
   */
  MakeDirectory mkdir;

  /**
   * Echo variable to be used for testing saveJShell methods
   */

  Echo echo;

  /**
   * PushDirectory variable to be used for testing saveJShell methods
   */
  PushDirectory pushd;

  /**
   * PopDirectory variable to be used for testing saveJShell methods
   */
  PopDirectory popd;

  /**
   * saveJShell variable so that we can test its methods
   */
  SaveJShell saveJShell;

  Concatenate cat;

  @Before
  public void setUp() {
    fs = FileSystem.getInstanceOfFileSystem();
    mkdir = new MakeDirectory();
    echo = new Echo();
    pushd = new PushDirectory();
    popd = new PopDirectory();
    saveJShell = new SaveJShell();
    cat = new Concatenate();
  }

  @After
  public void tearDown() throws Exception {
    field = (fs.getClass()).getDeclaredField("fs");
    field.setAccessible(true);

    // Setting fs parameter to null
    field.set(null, null);
  }

  @Test
  public void testExecuteCommand() {

    String[] argumentsForMkDir = {"mkdir",  "a",  "a/b",  "c", "c/d"};
    mkdir.executeCommand(argumentsForMkDir, false);

    echo.executeCommand("echo \"String\" > /a/File1", true);
    echo.executeCommand("echo \"String 2\" > /c/d/File2", true);

    pushd.executeCommand("a/b");
    pushd.executeCommand("/c");

    saveJShell.executeCommand("C:\\Users\\tapas\\rastog32\\TestForSave.txt");

    System.out.println("\n\n");
    StringBuilder actual = new StringBuilder();

    try (BufferedReader br = new BufferedReader
            (new FileReader("C:\\Users\\tapas\\rastog32\\TestForSave.txt"))) {
      String line = br.readLine();
      while ((line = br.readLine()) != null) {
        actual.append(line).append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    // actual contains the contents of the File 'TestForSave'

    String expected =
            "mkdir /a\n" +
                    "mkdir /c\n" +
                    "mkdir /a/b\n" +
                    "mkdir /c/d\n" +
                    "FileList: \n" +
                    "echo \"String\" > /a/File1\n" +
                    "echo \"String 2\" > /c/d/File2\n" +
                    "Stack: \n" +
                    "$ \n" +
                    "$ /a/b\n" +
                    "History: \n" +
                    "Tree: \n" +
                    "/\n" +
                    "\ta\n" +
                    "\t\tFile1\n" +
                    "\t\tb\n" +
                    "\tc\n" +
                    "\t\td\n" +
                    "\t\t\tFile2\n";
    assertEquals(expected, actual.toString());

  }



}
