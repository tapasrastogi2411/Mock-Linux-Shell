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

public class PushDirectoryTest {

  /**
   * Define instance of a FileSystem so that we can use it for testing
   */
  private static MockFileSystem mfs;

  /**
   * Variable for using the concept of reflection in testing
   */
  Field field;

  /**
   * PushDirectory object to use for testing the PushDirectory methods
   */

  PushDirectory pushd;

  /**
   * MakeDirectory object to use for testing the PushDirectory methods
   */
  MakeDirectory mkdir;

  @Before
  public void setUp(){
    mfs = MockFileSystem.getInstanceOfFileSystem();
    pushd = new PushDirectory();
    mkdir = new MakeDirectory();
  }

  @After
  public void tearDown() throws Exception{
    field = (mfs.getClass()).getDeclaredField("mfs");
    field.setAccessible(true);

    // Setting mfs parameter to null
    field.set(null, null);
  }

  @Test
  public void testExecuteCommand(){

    String [] arguments = {"mkdir", "dir1", "dir2", "dir1/sub_dir1",
            "dir1/sub_dir1/sub_sub_dir1" };

    /* Building the FileSystem */
    mkdir.executeCommand(arguments, false);

        /* This is the MockFileSystem version of our actual PushDirectory command, which works
        exactly like out real fileSystem implementation of PushDirectory command */
    mfs.executePushDCommand("dir1/sub_dir1");

    // Checking that the directory is changed
    String expectedPath = "/dir1/sub_dir1";
    String actualPath = MockFileSystem.currentDirectory.getPath();

    // Checking that the directoryStack is updated
    String expectedStack = "/";
    if(MockFileSystem.directoryStack.peek().equals("")){
      MockFileSystem.directoryStack.pop();
      MockFileSystem.directoryStack.push("/");
    }

    String actualStack = MockFileSystem.directoryStack.peek();

    assertEquals(expectedPath, actualPath);
    assertEquals(expectedStack, actualStack);





  }







}
