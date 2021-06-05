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

public class PopDirectoryTest {

  /**
   * Define instance of a FileSystem so that we can use it for testing
   */
  private static MockFileSystem mfs;

  /**
   * Variable for using the concept of reflection in testing
   */
  Field field;

  /**
   * PopDirectory object to use for testing the PopDirectory methods
   */
  PopDirectory popd;

  /**
   * MakeDirectory object to use for testing PopDirectory Methods
   */
  MakeDirectory mkdir;

  @Before
  public void setUp() {
    mfs = MockFileSystem.getInstanceOfFileSystem();
    popd = new PopDirectory();
    mkdir = new MakeDirectory();
    while (MockFileSystem.directoryStack.size() != 0){
      MockFileSystem.directoryStack.pop();
    }

  }

  @After
  public void tearDown() throws Exception {
    field = (mfs.getClass()).getDeclaredField("mfs");
    field.setAccessible(true);

    // Setting mfs parameter to null
    field.set(null, null);
  }

  @Test
  public void testExecuteCommand1() {

    String[] arguments = {"mkdir", "dir1", "dir2", "dir1/sub_dir1",
            "dir1/sub_dir1/sub_sub_dir1"};

    /* The set up for the PopDirectory command */
    mkdir.executeCommand(arguments, false);
    mfs.executePushDCommand("dir1");
    mfs.executePushDCommand("sub_dir1");

    // One Usage of PopDirectory
    mfs.executePopDCommand();

    // Checking that the size of the directory stack changes
    assertEquals(1, MockFileSystem.directoryStack.size());


    // Checking the change of directory
    String actualPath = MockFileSystem.currentDirectory.getPath();
    String expectedPath = "/dir1";
    assertEquals(expectedPath, actualPath);

    // Checking the directory stack
    String expectedStack = "/";
    if(MockFileSystem.directoryStack.peek().equals("")){
      MockFileSystem.directoryStack.pop();
      MockFileSystem.directoryStack.push("/");
    }
    String actualStack = MockFileSystem.directoryStack.peek();
    assertEquals(expectedStack, actualStack);
  }

  @Test
  public void testExecuteCommand2(){

    String[] arguments = {"mkdir", "dir1", "dir2", "dir1/sub_dir1",
            "dir1/sub_dir1/sub_sub_dir1"};

    /* The set up for the PopDirectory command */
    mkdir.executeCommand(arguments);
    mfs.executePushDCommand("dir1");
    mfs.executePushDCommand("sub_dir1");


    // Two usages of PopDirectory now
    mfs.executePopDCommand();
    mfs.executePopDCommand();

        /*Checking that the size of the directory stack changes, also telling us that the stack is
        empty and has no elements in it
         */
    assertEquals(0, MockFileSystem.directoryStack.size());

    // Checking that the currentDirectory changes
    String expectedPath = "/";
    if(MockFileSystem.directoryStack.size() == 0){
      MockFileSystem.directoryStack.push("/");
    }
    String actualPath = MockFileSystem.directoryStack.peek();
    assertEquals(expectedPath, actualPath);
  }
}

