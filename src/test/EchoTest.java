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

public class EchoTest {

  /**
   * Define instance of a FileSystem so that we can use it for testing
   */
  private static FileSystem fs;

  /**
   * Define instance of a MockFileSystem so that we can use it for testing in isolation
   */
  private static MockFileSystem mfs;

  /**
   * Variable for using the concept of reflection in testing
   */
  Field field;

  /**
   * Echo object to be used to test echo methods
   */
  Echo echo;

  @Before
  public void setup() {

    fs = FileSystem.getInstanceOfFileSystem();
    mfs = MockFileSystem.getInstanceOfFileSystem();
    echo = new Echo();
  }

  @After
  public void tearDown() throws Exception{
    field = (fs.getClass()).getDeclaredField("fs");
    field.setAccessible(true);

    // Setting fs parameter to null
    field.set(null, null);
  }

  @Test
  public void testExecuteCommandNoRedirection() {
    assertEquals("Test",echo.executeCommand("echo \"Test\"", false));

  }

  @Test
  public void testExecuteCommandWithRedirection1(){

    echo.executeCommand("echo \"This is the content of file1\"> File1", true);

        /* The fileGetter method here is that exact copy of the echo command used in the actual
        file system :)
         */
    StringBuilder actualContents = mfs.fileGetter();

    assertTrue(FileSystem.getRoot().getFile("File1") instanceof File);
    assertEquals("This is the content of file1", actualContents.toString());

  }
}
