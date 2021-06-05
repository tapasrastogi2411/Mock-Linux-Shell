package test;

import org.junit.*;
import static org.junit.Assert.*;
import commands.*;

import java.lang.reflect.Field;

public class PrintDocumentationTest {

  /**
   * Define instance of a FileSystem so that we can use it for testing
   */
  private static FileSystem fs;


  /**
   * Variable for using the concept of reflection in testing
   */
  Field field;

  /**
   * The PrintDocumentation variable so that we can test its variable
   */
  PrintDocumentation man;


  @Before
  public void setUp() {
    fs = FileSystem.getInstanceOfFileSystem();
    man = new PrintDocumentation();
  }

  @After
  public void tearDown() throws Exception {
    field = (fs.getClass()).getDeclaredField("fs");
    field.setAccessible(true);

    // Setting fs parameter to null
    field.set(null, null);
  }

  @Test
  public void testExecuteCommand0() {

    String[] arguments = {"man", "cat"};
    String actual = man.executeCommand(arguments, false);

    String expected = "cat FILE1 [FILE2 …]\n" +
            "Display the contents of FILE1 and other files (i.e. File2 ….) concatenated" +
            " in the shell\n" +
            "Three line breaks are used to separate contents of one file from another file\n";

    assertEquals(expected, actual);

  }
}
