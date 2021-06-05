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
import java.util.Stack;

public class LoadJShellTest {

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
  LoadJShell loadJShell;

  /**
   * Tree variable so that we can test its methods
   */
  Tree tree;

  /**
   * PrintRecentCommands variable so that we can test LoadJShell methods
   */
  PrintRecentCommands history;



  @Before
  public void setUp() {
    fs = FileSystem.getInstanceOfFileSystem();
    mkdir = new MakeDirectory();
    echo = new Echo();
    pushd = new PushDirectory();
    popd = new PopDirectory();
    saveJShell = new SaveJShell();
    tree = new Tree();
    loadJShell = new LoadJShell();
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

    loadJShell.executeCommand("C:\\Users\\tapas\\rastog32\\TestForSave.txt");

    String actual = FileSystem.buildTree(FileSystem.getRoot(), 0, new StringBuilder(), false);

    String expected = "/\n" +
            "\ta\n" +
            "\t\tFile1\n" +
            "\t\tb\n" +
            "\tc\n" +
            "\t\td\n" +
            "\t\t\tFile2\n";

    assertEquals(expected, actual); // Checked for tree here

    // History and stack

    PromptUserInput.inputHistory.add("1. mkdir a, a/b, c, c/d");
    PromptUserInput.inputHistory.add("2. echo \"String\" > /a/File1");
    PromptUserInput.inputHistory.add("3. echo \"String 2\" > /c/d/File2");
    PromptUserInput.inputHistory.add("4. pushd a/b");
    PromptUserInput.inputHistory.add("5. pushd /c");
    PromptUserInput.inputHistory.add("6. saveJShell C:\\Users\\tapas\\rastog32\\TestForSave.txt");
    PromptUserInput.inputHistory.add("7. loadJShell C:\\Users\\tapas\\rastog32\\TestForSave.txt");


    String[] arguments = new String[1];
    arguments[0] = "history";
    int argumentLength = 1;
    history = new PrintRecentCommands(arguments, argumentLength);

    String actualHistory = history.executeCommand(arguments, false);

    String expectedHistory =
            "1. mkdir a, a/b, c, c/d\n" +
                    "2. echo \"String\" > /a/File1\n" +
                    "3. echo \"String 2\" > /c/d/File2\n" +
                    "4. pushd a/b\n" +
                    "5. pushd /c\n" +
                    "6. saveJShell C:\\Users\\tapas\\rastog32\\TestForSave.txt\n" +
                    "7. loadJShell C:\\Users\\tapas\\rastog32\\TestForSave.txt\n";

    assertEquals(expectedHistory, actualHistory);

    // stack
    Stack<String> expectedStack = new Stack<>();

    expectedStack.push("/a/b");
    expectedStack.push("");

    Stack<String> actualStack = new Stack<>();

    while(!FileSystem.getDirectoryStack().isEmpty()){
      String elementToAdd = FileSystem.getDirectoryStack().lastElement();
      actualStack.push(elementToAdd);
      FileSystem.getDirectoryStack().pop();
    }

    assertEquals(expectedStack, actualStack);
  }
}
