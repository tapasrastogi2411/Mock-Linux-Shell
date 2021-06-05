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
// help from no one in designing and
// debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************

package commands;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;

public class FileSystem {

  /**
   * Static variable for the singleton design of the FileSystem
   */
  private static FileSystem fs;

  /**
   * The instance variable which is a stack for holding and keeping track of
   * the directories
   */
  protected static Stack<String> directoryStack = new Stack<>();

  /**
   * The instance variable which keeps track of the current directory we are
   * working on
   */
  public static Directory currentDirectory;

  /**
   * The instance variable which holds the root of the fileSystem
   */
  protected static Directory root;

  /**
   * The following instance variable is used in implementing execution of the
   * tree command
   */
  protected static ArrayList<String> treeList = new ArrayList<>();

  /**
   * The default constructor for the fileSystem
   */
  public FileSystem() {
    root = new Directory("");
    currentDirectory = root;
  }

  /**
   * Factory method for using root variable of the FileSystem
   */
  public static Directory getRoot(){
    return root;
  }

  /**
   * Factory method for using the directoryStack of the FileSystem
   */
  public static Stack<String> getDirectoryStack(){
      return directoryStack;
  }

  /**
   * Changes current directory to parent of current directory if current
   * directory is not the root directory
   */
  public static String changeDirectoryToParentDirectory() {
      currentDirectory = currentDirectory.getParent();
      return "";
  }

  /**
   * Changes current directory to sub directory given by user
   */
  public static String changeDirectoryToSubDirectory(String subPath) {
    Directory temp = root.getDirectoryFromPath(subPath, currentDirectory, root);

    if(temp != null) {
      currentDirectory = temp;
      return "";
    }
    else{
      return "Directory does not exist.";
    }
  }

  /**
   * Traverses the whole mock file system recursively and records the whole
   * structure of the file system
   *
   * @param root - The current position in the file system
   * @param depth - The level at which we are in the file system
   * @param sb - The StringBuilder that records the structure of the tree
   * @param saveFileContent - A boolean indicating whether to include file
   *                       content in the tree
   *
   * @return The structure of the tree as a string
   */
  public static String buildTree(Directory root, int depth, StringBuilder sb,
                                 boolean saveFileContent) {

        /* At every call of this recursive function, depending on the depth,
         we know that we have to indent our inner directories
         */
    for (int i = 0; i < depth; i++) {
      sb.append("\t");
    }
    if (depth == 0) {
      sb.append("/");
    }

        /* After indenting we now have to get the name of the file/directory
        that we are currently at and format it correctly with a newline
        character
         */
    sb.append(root.getName()).append("\n");

        /* Now we have to loop through the children of the current directory
        that we are at and recursively call our traverse method to recurse
        deeper into the FileSystem structure
         */
    for (int i = 0; i < root.getContents().size(); i++) {

      String directoryToAdd = root.getContents().get(i);

      // A common file reaches this part correctly
      if (root.getDir(directoryToAdd) != null) {
        depth += 1;
        buildTree(root.getDir(directoryToAdd), depth, sb,
                saveFileContent);
        depth = depth - 1;
      } else {
        depth++;
        for (int ii = 0; ii < depth; ii++) {
          sb.append("\t");
        }
        depth--;
        sb.append(directoryToAdd).append("\n");
      }
    }
    return sb.toString();
  }

  /**
   * A function that builds the array list of strings for saveJShell
   *
   * @param tempRoot - The current directory that we are interested in
   * @param directoryList - A list containing all the directory paths in the file system
   * @param fileList - A list containing all the file paths in the file system
   */
  public static void buildListBreadthFirst(Directory tempRoot,
          ArrayList<String> directoryList,
          ArrayList<String> fileList)
  {
    // loop to get all the child directories
    for(Directory children: tempRoot.getDirs()) {
      directoryList.add(children.getPath());
    }

    // loop to get all the child files
    for(File children: tempRoot.getFiles()) {
      fileList.add("\"" + children.getContents() + "\" > " +
              children.getPath());
    }

    // loop to recursively go through each child directory
    for(Directory children: tempRoot.getDirs()) {
      Directory temp = root.getDirectoryFromPath(children.getPath(),
              tempRoot, root);

      // ensuring the path is a directory path and not a file path
      if(temp != null) {
        buildListBreadthFirst(temp, directoryList, fileList);
      }
    }
  }

  /**
   * Gets instance of file system to support multiple shells running on the
   * same file system
   *
   * @return FileSystem instance of file system
   */
  public static FileSystem getInstanceOfFileSystem() {
    // Check if a file system instance already exists
    if (fs == null) {

      // Create an instance if it doesn't exist
      fs = new FileSystem();
    }
    // Else return the instance
    return fs;
  }
}