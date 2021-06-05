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

package Helper;

import commands.*;

import java.util.Stack;

public class MockFileSystem extends FileSystem{
  
  /**
   * Instance variable to hold the current working directory
   */
  public String currPwd = "/";

  /**
   * Static variable to hold the Mock FileSystem
   */
  private static MockFileSystem mfs;

  /**
   * Static variable to hold the directories for the fileSystem
   */
  public static Stack<String> directoryStack = new Stack<>();

  /**
   * Gets an the contents of the file from the entered mock path to be used for independent test
   *
   * @return - The contents of the file to be tested using the Mock FileSystem
   */

  public StringBuilder fileGetter() {
      try {
          StringBuilder sb = new StringBuilder();

          return sb.append("This is the content of file1");
      } catch (Exception e) {

          // Error message when file is not found
          System.out.println("File does not exist");
          return null; // we dont want to continue if file doesn't exist

      }
  }

  /**
   * Gets an instance of a Mock FileSystem to be used for testing
   *
   * @return - An instance of the Mock FileSystem as per the Singleton design pattern
   */
  public static MockFileSystem getInstanceOfFileSystem() {
    // Check if a file system instance already exists
    if (mfs == null) {

      // Create an instance if it doesn't exist
      mfs = new MockFileSystem();
    }

    // Else return the instance
    return mfs;
  }

  /**
   * Executes the Push Directory command, but in the Mock FileSystem
   *
   * @param dir - The directory that we want to change into
   * @return - The member of the stack that just got removed
   */

  public String executePushDCommand(String dir){
    Directory temp = Directory.getDirectoryFromPath(dir, MockFileSystem.currentDirectory,
            MockFileSystem.root);
    if(temp != null) {

      MockFileSystem.directoryStack.push(MockFileSystem.currentDirectory.getPath());
      mfs.executeCDCommand("cd " + dir);
      return "";
    }
    else {
      return "Invalid directory; doesn't exist";
    }
  }

  /**
   * Executes the Pop Directory command, but in the Mock File System
   *
   * @return - The directory that just got removed
   */
  public String executePopDCommand(){
    try{
      String popped = MockFileSystem.directoryStack.pop();
      if(popped.equals("")){
        mfs.executeCDCommand("cd " + "/");
      }
      else{
        mfs.executeCDCommand("cd " + popped);
      }
      return "";
    }
    catch (Exception e){
      return "popd: directory stack empty";
    }

  }

  /**
   *
   * @param input - The directory that you want to change into
   * @return - The new changed working directory
   */
  public String executeCDCommand(String input){
    String[] path = input.split(" ");

    if(!path[1].equals(".")) {
      // if path given is absolute path
      if(path[1].charAt(0) == '/') {
        return MockFileSystem.changeDirectoryToSubDirectory(path[1]);
      }
      // path is a relative path
      else {
        return MockFileSystem.changeDirectoryToSubDirectory
                (MockFileSystem.currentDirectory.getPath()
                        + "/" + path[1]);
      }
    }
    return "";
  }
}