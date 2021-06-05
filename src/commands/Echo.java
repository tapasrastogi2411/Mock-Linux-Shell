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

public class Echo extends Commands{

  /**
   * Appends STRING provided by the user into OUTFILE provided by the user
   * where both STRING and OUTFILE are located in userInput
   *
   * @param userInput A string that consists of the full input from the user
   */
  @Override
  public String executeCommand(String userInput, boolean isAngle) {
    // The variables that split the user input that are used to separate cases
    String[] quotesCheck = userInput.split("\"");
    String[] splitBracket = userInput.split(">");

    //handles case for when redirection is not needed
    if(!isAngle) {
      //System.out.println(quotesCheck[1]);
      return quotesCheck[1];
    }
    // handles the absolute path case
    else if(splitBracket[splitBracket.length - 1].contains("/")) {
      // case when file name isn't provided (ie. path ends with the '/'
      // character
      if(userInput.charAt(userInput.length() - 1) == '/') {
        return "Please provide file name at the end of path " +
                "(ie. don't end the path with the character '/'";
      }

      // getting the  path of the parent directory
      String parentPath = splitBracket[splitBracket.length - 1].substring(0,
              splitBracket[splitBracket.length - 1].lastIndexOf("/"));
      // getting the parent directory of the file in the given absolute path
      Directory parentDir
              = FileSystem.root.getDirectoryFromPath(parentPath.trim(),
              FileSystem.currentDirectory, FileSystem.root);

      // checking whether the given parent directory from the path exists in
      // the file system
      if(parentDir == null) {
        return "Path provided doesn't exist";
      }
      else {
        File file = parentDir.getFile(
                splitBracket[splitBracket.length - 1].trim().
                        substring(splitBracket[splitBracket.length - 1]
                                .lastIndexOf("/")));
        // case for when the given file doesn't exist,
        // obtaining the file name by splitting over "/"
        if(file == null) {
          String[] slashCheck = splitBracket[splitBracket.length - 1]
                  .split("/");

          File newFile = new File(slashCheck[slashCheck.length - 1],
                  parentDir);
          newFile.setContents(quotesCheck[1]);
        }
        // Appends or overwrites the string given by the user into the given
        // file
        else {
          appendOrOverwriteHelper(quotesCheck, file);
        }
      }
    }
    // handles the relative path case
    else {
      File file = FileSystem.currentDirectory.getFile(
              splitBracket[splitBracket.length - 1].trim());

      // case for when the given file doesn't exist
      if(file == null) {
        File newFile = new File(splitBracket[splitBracket.length - 1].trim(),
                FileSystem.currentDirectory);
        newFile.setContents(quotesCheck[1]);
      }
      // Appends or overwrites the string given by the user into the given file
      else appendOrOverwriteHelper(quotesCheck, file);
    }
    return "";
  }

  /**
   * A helper function that either appends or overwrites the string given by user into file
   *
   * @param quotesCheck - An array of strings of the user input splitted by quotes
   * @param file - A file that the string will be either appended or overwritten into
   */
  private void appendOrOverwriteHelper(String[] quotesCheck, File file) {
    // Appends the string given by the user into the given file
    if(quotesCheck[quotesCheck.length - 1].contains(">>")) {
      String fileContent = file.getContents();
      String newFileContent = fileContent + quotesCheck[1];
      file.setContents(newFileContent);
    }
    // Overwrites the string given by the user into the given file
    else {
      file.setContents(quotesCheck[1]);
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
    String content = "echo STRING>>OUTFILE\n" + "If OUTFILE exists " +
            "appends STRING into OUTFILE\n" + "If OUTFILE doesn't " +
            "exist, " + "then create a file OUTFILE and append " +
            "STRING into the newly created OUTFILE\n" + "If OUTFILE " +
            "is not provided, " + "then print STRING in the " +
            "shell\n\n" + "echo STRING>OUTFILE\n" + "If OUTFILE " +
            "exists, " + "delete preexisting content in OUTFILE," +
            "then appends STRING into OUTFILE\n" + "If OUTFILE " +
            "doesn't exist, " + "then create a file OUTFILE and " +
            "append " + "STRING into the newly created OUTFILE\n" +
            "If OUTFILE is not provided, " + "then print STRING in " +
            "the shell\n";
    if(!isAngle)
    {
      return content;
    }
    else
    {
      return Redirection.redirect(arguments,content);
    }
  }
}
