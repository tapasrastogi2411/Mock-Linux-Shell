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

public class PrintWorkingDirectory extends Commands {

  /**
   * Prints the current working directory which is stored in the File System
   *
   * @param arguments List of arguments
   * @param isAngle boolean that checks if the redirection operator is
   *                present or not
   */
  @Override
  public String executeCommand(String[] arguments, boolean isAngle) {

    if(FileSystem.currentDirectory == FileSystem.root) {
      if(isAngle == false)
      {
        return FileSystem.currentDirectory.getPath() + "/";
      }
      else
      {
        return Redirection.redirect(arguments,
                FileSystem.currentDirectory.getPath() + "/");
      }
    }
    else {
      if(isAngle == false)
      {
        return FileSystem.currentDirectory.getPath();
      }
      else
      {
        return Redirection.redirect(arguments,
                FileSystem.currentDirectory.getPath());
      }
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
    String content = "pwd\nPrints the current working directory" +
            "(including the whole path).\n";
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

