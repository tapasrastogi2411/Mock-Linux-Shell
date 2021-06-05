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

public class PushDirectory extends Commands {

  /**
   * Saves the current working directory by pushing it on the stack, so it
   * can be accessed later on using the popd command. After saving the
   * current working directory, the new given directory becomes the current
   * working directory of the program.
   *
   * @param dir A directory that is to be made this shells current working
   *            directory.
   */
  @Override
  public String executeCommand(String dir){
    Directory temp = FileSystem.root.getDirectoryFromPath(dir,
            FileSystem.currentDirectory,
            FileSystem.root);
    if(temp != null) {
      FileSystem.directoryStack.push(
              FileSystem.currentDirectory.getPath());
      ChangeDirectory cd = new ChangeDirectory();
      cd.executeCommand("cd " + dir);
      return "";
    }
    else {
      return "Invalid directory; doesn't exist";
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
    String content = "pushd DIR\n" + "The pushd command saves the " +
            "old current working" +
            " directory in directory stack so that it can be returned" +
            " to" + " at any time\n" + "The pushd must be consistent " +
            "as per the Last In" + " First Out behavior of a stack\n";
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