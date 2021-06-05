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

public class File {

  /**
   * The directory that this file is in
   */
  private Directory parent;

  /**
   * The name of this file
   */
  private String name;

  /**
   * The content of this file
   */
  private String contents;

  /**
   * The path of this file
   */
  private String path;

  /**
   * Creates a new file with a name inside of a directory
   *
   * @param name - all files must have names
   * @param parent - all files must be in directories
   */
  public File(String name, Directory parent) {
    this.name = name;
    this.parent = parent;
    this.contents = "";
    this.path = parent.getPath() + "/" + name;
    this.parent.addFile(this);
  }

  /**
   * Constructor for providing a path instead of a parent
   *
   * @param path the path to the file we want to create
   * @param workingDir the working directory (for relative paths)
   * @param root the root directory (for absolute paths)
   */
  public File(String path, Directory workingDir, Directory root)
  {
    // split the full path into parent path and file name
    String fileName = path.substring(path.lastIndexOf("/") + 1);
    String parentPath = path.substring(0, path.lastIndexOf("/") + 1);
    // get the parent from the parent path
    Directory parent =
            Directory.getDirectoryFromPath(parentPath, workingDir, root);

    // set the name
    this.name = fileName;

    // set the parent and add this file to it
    this.parent = parent;
    parent.addFile(this);
  }

  /**
   * Sets the contents of the file
   *
   * @param contents - the string which holds the content of the file to be
   *                 set
   */
  public void setContents(String contents) {
    this.contents = contents;
  }

  /**
   * Returns the contents of the file
   *
   * @return - the file content
   */
  public String getContents() {
    return this.contents;
  }

  /**
   * The name of the file is set
   *
   * @param name - file name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * The file name is returned
   *
   * @return - files name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns the parent of the file
   *
   * @return - current working directory
   */
  public Directory getParent() {
    return this.parent;
  }

  /**
   * Sets the files parent so location
   *
   * @param parent - the directory being sent
   */
  public void setParent(Directory parent) {
    this.parent = parent;
  }

  /**
   * Returns the full absolute path of the file
   *
   * @return - the path of this file instance
   */
  public String getPath() {
    return this.path;
  }

  /**
   * Sets the path to the path given by the user
   *
   * @param path - A string which is the path of the this file
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * Returns a boolean stating if the given path points to an existing file.
   *
   * @param path - the path we need to check whether it exists or not
   * @param workingDir -  the current directory in which the file to check is
   *                  in.
   * @return - True if the path does point to an existing file,
   * false otherwise
   */
  public static boolean exists(String path, Directory workingDir,
                               Directory root) {
    path = path.trim();
    String fileName = path.substring(path.lastIndexOf("/") + 1);
    String parentPath = path.substring(0, path.lastIndexOf("/") + 1);

    // get the parent directory
    try {
      Directory parent =
              Directory.getDirectoryFromPath(parentPath, workingDir,
                      root);
      // check for the file inside
      File file = parent.getFile(fileName);
      // return true if the file exists
      return file != null; // Simplified the if statement here
    } catch (Exception ex) {
      // if the parse fails, then the file doesn't exist
      return false;
    }
  }


  /**
   * Returns a file from a given path (if it exists)
   *
   * @param path - the path from which we have to get a file
   * @param workingDir - the current working directory we are in
   * @return - the file extracted from the input path
   */
  public static File getFileFromPath(String path, Directory workingDir,
                                     Directory root){
    path = path.trim();
    String fileName = path.substring(path.lastIndexOf("/") + 1);
    String parentPath = path.substring(0, path.lastIndexOf("/") + 1);

    // get the parent directory
    Directory parent =
            Directory.getDirectoryFromPath(parentPath, workingDir, root);
    // check for the file inside
    if(parent != null){
      return parent.getFile(fileName);
    }
    return null;

  }
}