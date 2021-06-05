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

import Helper.MockFileSystem;
import org.junit.*;
import static org.junit.Assert.*;
import commands.*;

import java.lang.reflect.Field;

public class PrintWorkingDirectoryTest {

  /**
   * Define instance of a FileSystem so that we can use it for testing
   */
  private static MockFileSystem mfs;

  /**
   * Variable for using the concept of reflection in testing
   */
  Field field;

  /**
   * PrintWorkingDirectory object that is to be used in testing
   */
  PrintWorkingDirectory pwd;

  /**
   * MakeDirectory object that is to be used in testing PrintWorkingDirectory methods
   */
  MakeDirectory mkdir;

  @Before
  public void setUp(){
    mfs = MockFileSystem.getInstanceOfFileSystem();
    mkdir = new MakeDirectory();
    pwd = new PrintWorkingDirectory();
  }

  @After
  public void tearDown() throws Exception{
    field = (mfs.getClass()).getDeclaredField("mfs");
    field.setAccessible(true);

    // Setting mfs parameter to null
    field.set(null, null);
  }

  @Test
  public void testExecuteCommand() {

    String [] arguments = {"mkdir", "dir1", "dir2", "dir1/sub_dir1",
            "dir1/sub_dir1/sub_sub_dir1" };

    /* Building the FileSystem */
    mkdir.executeCommand(arguments, false);

    // Using the mock object because of dependency instead of using the ChangeDirectory method
    mfs.executeCDCommand("cd dir1/sub_dir1");

    // Checking if the correct directory is printed on calling the pwd command
    String actualPath = pwd.executeCommand(arguments, false);
    String expectedPath = "/dir1/sub_dir1";
    assertEquals(expectedPath, actualPath);






  }

}
