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
import java.lang.reflect.Field;

public class TreeTest {

  /**
   * Define instance of a FileSystem so that we can use it for testing
   */
  private static FileSystem fs;

  /**
   * Variable for using the concept of reflection in testing
   */
  Field field;

  /**
   * MakeDirectory object to use for testing its methods
   */
  Tree tree;

  /**
   * MakeDirectory object to use for testing the tree methods
   */

  MakeDirectory mkdir;

  /**
   * Echo object to use for testing the tree methods
   */

  Echo echo;

  @Before
  public void setUp(){
    fs = FileSystem.getInstanceOfFileSystem();

    tree = new Tree();
    mkdir = new MakeDirectory();
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
  public void testExecuteCommand() {

    String userInput = "echo \"Hello World\" > File1";

    String[] arguments1 = new String[3];
    arguments1[0] = "mkdir";
    arguments1[1] = "dir1";
    arguments1[2] = "dir2";

    String expected = "/\n" +
            "\t" + "File1\n" +
            "\t" + "dir1\n" +
            "\t"+ "dir2\n";

    echo.executeCommand(userInput, true);
    mkdir.executeCommand(arguments1, false);

    String contents = tree.executeCommand(arguments1, false);

    assertEquals(expected, contents);

  }

}
