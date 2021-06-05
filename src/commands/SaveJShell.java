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

package commands;

import java.io.*;
import java.io.File;
import java.util.ArrayList;

public class SaveJShell extends Commands implements java.io.Serializable{

  /**
   * An instance variable that contains all the path of directories in the file system
   */
  private ArrayList<String> directoryList = new ArrayList<>();

  /**
   * An instance variable that contains all the path of files and their contents in the file system
   */
  private ArrayList<String> fileList = new ArrayList<>();

  /**
   * Saves the JShell and its contents into file
   *
   * @param file A string that contains that name of the file that the JShell
   *             will be saved into
   */
  @Override
  public String executeCommand(String file) {

    FileSystem.buildListBreadthFirst(FileSystem.root, directoryList, fileList);

    String s = buildString(); // string to be put into file

    File file1 = new File(file); // the file
    if(!file1.exists()) { // case for when the file provided doesn't exist
      try {
        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(file));

        out.writeObject(s);

        out.close();
      } catch (IOException e) {
        return "Error: Something went wrong in creating " + file;
      }
    }
    else if(file1.delete()) { // case when the file provided already exists
      try {
        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(file));

        out.writeObject(s);

        out.close();

      } catch (IOException e) {
        return "Error: Something went wrong when overwriting "
                + file;
      }
    } else {
      return "Error: Something went wrong when deleting existing" +
              " content of " + file;
    }
    return "";
  }

  /**
   * A helper function to build the string that will be put into the file
   * given by the user
   *
   * @return - A string that contains the tree, history, and directory stack
   * of the file system
   */
  private String buildString() {
    // building the string to store in the file
    String s = "";

    // the directory list portion of the string
    s+= "DirectoryList: \n";
    for(String directoryPaths: directoryList) {
      s += "mkdir " + directoryPaths + "\n";
    }

    // the file list portion of the string
    s+= "FileList: \n";
    for(String file: fileList) {
      s+= "echo " + file + "\n";
    }

    // the stack portion of the string
    s+= "Stack: \n";
    for(String stack: FileSystem.directoryStack) {
      s += "$ " + stack + "\n";
    }

    // the history portion of the string
    s += "History: \n";
    for(String input: PromptUserInput.inputHistory) {
      s += input + "\n";
    }

    // the tree portion of the string
    s+= "Tree: \n";
    s += FileSystem.buildTree(FileSystem.root, 0, new StringBuilder(),
            true);

    return s;
  }

  /**
   * Prints out the documentation(syntax, information of what this command
   * does) for the the user
   *
   * @param arguments list of arguments given by the user
   * @param isAngle boolean that checks if the redirection operator is
   *                present or not
   */
  @Override
  public String getDocumentation(String[] arguments, boolean isAngle) {
    String content = "saveJShell FileName\n" + "Saves the JShell and " +
            "all of its contents into FileName" +
            "so that the user can access these contents again in the" +
            " future" +
            "even after the JShell has been terminated\n";
    if(!isAngle)
    {
      return content;
    }
    else
    {
      return Redirection.redirect(arguments, content);
    }
  }
}
