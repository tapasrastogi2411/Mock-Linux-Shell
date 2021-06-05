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

public class ConcatenateTest {

  /**
   * Define instance of a FileSystem Interface so that we can use it for testing
   */
  private static MockFileSystem mfs;

  /**
   * Variable for using the concept of reflection in testing
   */
  Field field;

  /**
   * Concatenate object to be used for testing
   */
  Concatenate cat;


  @Before
  public void setUp(){
    cat = new Concatenate();
    mfs = MockFileSystem.getInstanceOfFileSystem();
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

        /* Uses the fileGetter method of the MockFileSystem to check cat command in isolation
        Here the fileGetter method of the MockFileSystem is the exact copy of our actual
        FileSystem Concatenate method for the one file case
         */
    StringBuilder actualContents = mfs.fileGetter();

    assertEquals("This is the content of file1", actualContents.toString());
  }
}
