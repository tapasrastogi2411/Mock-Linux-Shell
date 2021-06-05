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

public class SearchTest {

  /**
   * Define instance of a FileSystem so that we can use it for testing
   */
  private static FileSystem fs;

  /**
   * Variable for using the concept of reflection in testing
   */
  Field field;

  /**
   * Search variable to test its methods
   */
  Search search;

  /**
   * MakeDirectory variable to build the FileSystem to test the Search methods
   */
  MakeDirectory mkdir;

  @Before
  public void setUp() {
    fs = FileSystem.getInstanceOfFileSystem();
    search = new Search();
    mkdir = new MakeDirectory();
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

    String[] arguments = {"mkdir",  "dir1", "dir2",  "dir1/sub_dir1",  "dir1/sub_dir2"};
    mkdir.executeCommand(arguments, false);
    String[] command = {"search",  "/dir1",  "-type",  "d",  "-name",  "\"sub_dir2\""};

    String actual = search.executeCommand(command, false);

    String expected = "/dir1/sub_dir2\n";

    assertEquals(expected, actual);
  }


}
