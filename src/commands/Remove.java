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

public class Remove extends Commands{

  /**
   * Removes the specified directory given by the user from the file system
   *
   * @param input A list of string that contains the name or path of the
   *              directory that is to be removed
   */
  @Override
  public String executeCommand(String[] input) {

    Directory temp = FileSystem.root.getDirectoryFromPath(
            input[1],
            FileSystem.currentDirectory, FileSystem.root);
    if(temp == null) {
      return input[1] + " doesn't exist";
    }
    else {
      String[] pathCheck = FileSystem.currentDirectory.getPath()
              .split("/");
      for(String parentDirs : pathCheck) {
        if(parentDirs.equals(temp.getName())) {
          return "Cannot remove current directory " +
                  "or any of its parent directories from the filesystem";
        }
      }

      Directory parent = temp.getParent();
      parent.deleteDirectory(temp);
      return "";
    }
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
    String content = "rm DIR\n" + "Removes Dir from file system, " +
            "as well as its children\n";
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
