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

public class Tree extends Commands{
  /**
   * Prints the file system in the format of a tree with appropriate
   * indentation
   *
   * @param arguments list of arguments given by the user
   * @param isAngle boolean that checks if the redirection operator is present
   *               or not
   * @return A string that is to be used in commands to print the tree.
   */
  public String executeCommand(String[] arguments, boolean isAngle) {

    String s = FileSystem.buildTree(FileSystem.root, 0,
            new StringBuilder(), false);
    if(!isAngle)
    {
      return s;
    }
    else
    {
      Redirection.redirect(arguments, s);
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
  public String getDocumentation(String[] arguments, boolean isAngle){
    String content = "tree\nPrints the structure of the whole mock " +
            "file system\n";
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
