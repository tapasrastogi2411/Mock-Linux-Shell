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

import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Represents a directory in the JShell filesystem. Takes in a name and a
 * parent, and lets you add files and directories as children.
 */
public class Directory{

  /**
   * The parent of this dir
   */
  private Directory parent;

  /**
   * The name of this dir
   */
  private String name;

  /**
   * The list of files that's in this directory
   */
  private ArrayList<File> files = new ArrayList<>();

  /**
   * The list of directories that's in this directory
   */
  private ArrayList<Directory> dirs = new ArrayList<>();

  /**
   * The path of this directory
   */
  private String path;

  /**
   * Creates a new directory, this one of our parametrized constructor for
   * this class
   *
   * @param parent - A pointer to the parent dir. Can be changed later.
   * @param name   - The name of the directory. Must only contain legal chars.
   */
  public Directory(Directory parent, String name) {
    this.name = name;
    this.parent = parent;
    this.path = parent.getPath() + "/" + name;
    parent.addDirectory(this);
  }

  /**
   * Constructs a directory with only the name given
   * @param name - A string that will be the directory's name
   */
  public Directory(String name) {
    this.name = name;
    this.parent = this;
    this.path = "";
  }

  /**
   * Another constructor, but now it has three input arguments
   *
   * @param path - String containing path to the directory we are creating
   * @param workingDir - The current working directory
   * @param root - the root directory of our fileSystem
   */
  public Directory(String path, Directory workingDir, Directory root)
  {
    String dirName = path.substring(path.lastIndexOf("/") + 1);
    String parentPath = path.substring(0, path.lastIndexOf("/") + 1);

    // get the parent from the parent path
    Directory parent =
            getDirectoryFromPath(parentPath, workingDir, root);
    this.setName(dirName);
    // set the parent and add this file to it
    this.setParent(parent);
    assert parent != null;
    parent.addDirectory(this);
  }

  /**
   * Sets the directory name
   * @param name - The name of the directory that we want to set to
   */
  public void setName(String name){
    this.name = name;
  }

  /**
   * Gets the name of the directory
   *
   * @return - The name of the directory as a string
   */
  public String getName() {
    return this.name;
  }

  /**
   * Sets the name of the parent directory
   *
   * @param parent - The name of the parent directory that we want to set
   */
  public void setParent(Directory parent) {
    this.parent = parent;
  }

  /**
   * Gets the name of the parent directory
   *
   * @return - returns the name of the parent directory
   */
  public Directory getParent() {
    return this.parent;
  }

  /**
   * Returns the full absolute path of the directory
   *
   * @return - the full absolute path of the directory
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
   * Returns the directory as specified by the given name.
   *
   * @param name  - the name of the directory that we want to get
   * @return The directory if one exists such that its name matches the give
   * name. Otherwise, returns null.
   */
  public Directory getDir(String name) {
    // "." references this dir
    if (name.equals(".")) {
      return this;
    }
    // ".." references parent
    else if (name.equals("..")) {
      return this.parent;
    }
    // if non trivial, loop through contents to find directory
    else {
      for (Directory dir : dirs) {
        if (dir.getName().equals(name)) {
          return dir;
        }
      }
    }

    return null;
  }

  /**
   * Returns the file as specified by the given name.
   *
   * @param name -The name of the file that you to return back
   * @return The file if one exists with the given name. Null otherwise.
   */
  public File getFile(String name) {
    for (File file : files) {
      if (file.getName().equals(name)) {
        return file;
      }
    }
    return null;
  }

  /**
   * Returns a sorted string arraylist of the dir contents
   *
   * @return The ArrayList that has the files and dirs of this dir
   */
  public ArrayList<String> getContents() {
    // start with an empty list
    ArrayList<String> output = new ArrayList<>();

    // add all the file names
    for (File f : files) {
      output.add(f.getName());
    }
    // add all the dir names
    for (Directory d : dirs) {
      output.add(d.getName());
    }
    return output;
  }

  /**
   * Returns a sorted string arraylist of the dir contents
   *
   * @return The ArrayList that has the files and dirs of this dir
   */
  public ArrayList<String> getContentsPath() {
    // start with an empty list
    ArrayList<String> output = new ArrayList<>();

    // add all the file names
    for (File f : files) {
      output.add(f.getPath());
    }
    // add all the dir names
    for (Directory d : dirs) {
      output.add(d.getPath());
    }
    return output;
  }

  /**
   * Gets the whole files array
   *
   * @return Returns all the files as a Content object
   */
  public ArrayList<File> getFiles() {
    return this.files;
  }

  /**
   * Gets the whole dirs array
   *
   * @return Returns all the dirs as a Content object
   */
  public ArrayList<Directory> getDirs() {
    return this.dirs;
  }

  /**
   * Finds a directory, given a path. The path provided must point to a
   * directory.
   *
   * @param workingDir - the directory from which to start relative paths
   * @param path - the path string from which to extract a directory
   * @param root - the root of our FileSystem
   * @return - the directory we wanted from the path
   */
  public static Directory getDirectoryFromPath(String path,
                                               Directory workingDir,
                                               Directory root) {
    // Trim the path to start
    path = path.trim();

    Directory current;
    // If our path is empty or it doesn't start with slash
    if (path.length() == 0 || path.charAt(0) != '/') {
      // start from the working dir
      current = workingDir;
    }
    // if the first char is a slash
    else {
      // start from the root dir
      current = root;
    }

    // split the path by slashes
    String[] splitPath = path.split("/");

    // loop through the dir names
    for (String child : splitPath) {
      // empty string is not a dir
      if (!child.isEmpty()) {
        // try to find the directory
        Directory tempDir = current.getDir(child);

        if (tempDir == null) {
          return null;

        } else {
          // if we found a directory, proceed with parsing
          current = tempDir;
        }
      }
    }

    // if we've made it to the end, then `current` is a valid dir
    return current;
  }

  /**
   * Adds a supplied child directory to dir of this directory
   *
   * @param dir The directory to be added to this directory's dirs
   */
  public void addDirectory(Directory dir) {
    dirs.add(dir);
  }

  /**
   * Adds a file to files of this directory
   *
   * @param file The file to be added to this directory's dirs
   */
  public void addFile(File file) {
    files.add(file);
  }

  /**
   * Deletes a file from files of this directory
   *
   * @param file The child file to be delete from this directory's files
   */
  public void deleteFile(File file) {
    for(File childFile : files) {
      if(childFile.getName().equals(file.getName())) {
        files.remove(file);
        break;
      }
    }
  }

  /**
   * Deletes a directory from dirs of this directory
   *
   * @param dir The directory to be deleted to this directory's dirs
   */
  public void deleteDirectory(Directory dir) {
    for(Directory childDir : dirs) {
      if(childDir.getName().equals(dir.getName())) {
        dirs.remove(dir);
        break;
      }
    }
  }

  /**
   * Sets the list of dirs to new given directories
   *
   * @param directories - The list of directories that we want to set as
   *                    children directories of the current directory
   */
  public void setDirs(ArrayList<Directory> directories) {
    this.dirs = directories;
  }

  /**
   * Gets the dirs of the current directory
   */
  public ArrayList<Directory> getDirsList() {
    return this.dirs;
  }

  /**
   * Sets the list of files to given files
   *
   * @param files - The list of files that we want to set as
   *                    children files of the current directory
   */
  public void setFiles(ArrayList<File> files) {
    this.files = files;
  }

  /**
   * Gets the files of the current directory
   */
  public ArrayList<File> getFilesList() {
    return this.files;
  }
}