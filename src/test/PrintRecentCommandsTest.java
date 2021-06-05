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

public class PrintRecentCommandsTest {

  /**
   * Define instance of a FileSystem so that we can use it for testing
   */
  private static FileSystem fs;

  /**
   * Variable for using the concept of reflection in testing
   */
  Field field;

  /**
   * History/PrintRecentCommands variable to use for testing
   */
  PrintRecentCommands history;

  @Before
  public void setUp() {
    fs = FileSystem.getInstanceOfFileSystem();
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
    PromptUserInput.inputHistory.add("1. mkdir dir1 dir2");
    PromptUserInput.inputHistory.add("2. cd dir1");
    PromptUserInput.inputHistory.add("3. echo \"Hey\" > File1");
    PromptUserInput.inputHistory.add("4. ls");
    PromptUserInput.inputHistory.add("5. history");

    String[] arguments = new String[1];
    arguments[0] = "history";
    int argumentLength = 1;
    history = new PrintRecentCommands(arguments, argumentLength);

    String actual = history.executeCommand(arguments, false);
    String expected =  "1. mkdir dir1 dir2\n" +
            "2. cd dir1\n" +
            "3. echo \"Hey\" > File1\n" +
            "4. ls\n" +
            "5. history\n";
    assertEquals(expected, actual);


  }
}
