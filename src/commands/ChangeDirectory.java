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

public class ChangeDirectory extends Commands{

  /**
   * Changes the current working directory into the directory provided
   * by the user
   *
   * @param input The user input that contains the absolute or relative path
   *             of the directory that the user wants to access
   */
  @Override
  public String executeCommand(String input) {
    // a variable that identifies the path given by the user
    String[] path = input.split(" ");

    // changes the current directory to its parent
    if(path[1].equals("..")) {
      return FileSystem.changeDirectoryToParentDirectory();
    }
    // changed the current directory to the given directory by the user if
    // the argument is not '.'
    else if(!path[1].equals(".")) {
      if(path[1].length() > 1) {
        if(path[1].charAt(0) == '/' && path[1].charAt(1) == '/')
        {
          return "Invalid Path";
        }
      }

      // if path given is absolute path
      if(path[1].charAt(0) == '/') {
        return FileSystem.changeDirectoryToSubDirectory(path[1]);
      }
      // path is a relative path
      else {
        return FileSystem.changeDirectoryToSubDirectory
                (FileSystem.currentDirectory.getPath()
                        + "/" + path[1]);
      }
    }
    return "";
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
    String content = "cd DIR\n" + "Change the current working " +
            "directory to DIR, " + "which may be relative" + " to the "
            + "current directory or may " + "be the full path\n" + "If"
            + " DIR is .. then it means it is the parent " +
            "directory\n" + "If DIR is . then it means it is the " +
            "current " + "directory\n";
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