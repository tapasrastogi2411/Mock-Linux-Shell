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

public class MakeDirectory extends Commands {

  /**
   * Takes in an array of paths from the arguments and attempts to create
   * directories at each of the given paths
   *
   * @param arguments - Arguments from the user as a list of strings
   *
   */
  @Override
  public String executeCommand(String[] arguments, boolean isAngle) {

    int len;

    if(isAngle)
    {
      len = arguments.length - 2;
    }
    else
    {
      len = arguments.length;
    }

    for(int i = 1; i < len; i++) {
      if(arguments[i].contains("/")) {
        Directory temp = Directory.getDirectoryFromPath(arguments[i],
                FileSystem.currentDirectory, FileSystem.root);

                /* if temp == null, then temp doesn't exist in file system,
                and the path doesn't point to any directory
                 */
        if(temp == null) {
          String[] pathCheck = arguments[i].split("/");
          String parentName = arguments[i].substring(0,
                  arguments[i].lastIndexOf("/"));

          Directory parentOfGiven =
                  Directory.getDirectoryFromPath(
                          parentName,
                          FileSystem.currentDirectory,
                          FileSystem.root);

          if(!checkIllegalCharacter(pathCheck[pathCheck.length-1])) {
            try{
              Directory dir = new Directory(parentOfGiven,
                      pathCheck[pathCheck.length-1]);
            }
            catch (Exception e)
            {
              return "parent doesn't exist";
            }
          }
          else {
            return "Invalid directory name: contains" +
                    " illegal characters";
          }
        }
        else {
          return "Directory already exists";
        }
      }

      else if(!checkIllegalCharacter(arguments[i])) {
        for(String s: FileSystem.currentDirectory.getContents()) {
          if(arguments[i].equals(s)) {
            return "Directory already exists";
          }
        }

        Directory dir = new Directory(FileSystem.currentDirectory,
                arguments[i]);
      }
      else if(checkIllegalCharacter(arguments[i])) {
        return "Invalid directory name: contains illegal" +
                " characters";
      }
    }
    return "";
  }

  /**
   * Returns a boolean indicating whether string contains illegal characters
   * which are: '/', '.', ' ', '!', '@', '#', '$', '%', '^', '&', '*',
   * '(', ')', '{', '}', '~', '|', '<', '>', '?'
   *
   * @param string a string that will be checked to determine whether it
   *               contains illegal character
   * @return a boolean indicating whether string contains illegal character
   */
  public boolean checkIllegalCharacter(String string) {

    return string.contains(".") ||
            string.contains(" ") || string.contains("!") ||
            string.contains("@") || string.contains("#") ||
            string.contains("$") || string.contains("%") ||
            string.contains("&") || string.contains("*") ||
            string.contains("(") || string.contains(")") ||
            string.contains("{") || string.contains("}") ||
            string.contains("~") || string.contains("|") ||
            string.contains("<") || string.contains(">") ||
            string.contains("?");
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
    String content = "mkdir DIR1 DIR2\n" + "This command takes in " +
            "two arguments only. Create" +
            " directories, each of which may be relative to the current " +
            "directory or may be a full path.\n";
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