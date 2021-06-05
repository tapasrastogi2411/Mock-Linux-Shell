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

public class Concatenate extends Commands{

  /**
   * Helps to print the contents of the file, where contents of each file is
   * separated by three
   * lines
   *  @param fileName - Name of the file
   * @param isAngle  - boolean that checks if the redirection operator is
   * @return
   */
  public StringBuilder catFile(File fileName, boolean isAngle) {

    StringBuilder sb = new StringBuilder();
    //File class is called to get the contents of the file
    String content = fileName.getContents();

    if(!isAngle)
    {
      // It then prints out the contents of the file through help of String
      // "content"
      String[] lines = content.split("\n");
      for(String line : lines)
        System.out.println(line);
    }
    else // isAngle is true
    {
      sb.append(content);
    }
    return sb;
  }

  /**
   * Helps to retrieve the File Object from the system by giving in the path
   * to it, gives an error if file does not exist
   *
   * @param fileName - Name of the file
   * @param isAngle  - boolean that checks if the redirection operator is
   *                   present or not
   */
  public StringBuilder fileGetter(String fileName, boolean isAngle) {
    try {
      // Gets the file from the path given
      File received = File.getFileFromPath(fileName,
              FileSystem.currentDirectory, FileSystem.root);
      // File found is now printed its contents
      StringBuilder sb = catFile(received, isAngle);
      return sb;
    } catch (Exception e) {
      // Error message when file is not found
      System.out.println("File does not exist");
      return null; // we dont want to continue if file doesn't exist
    }
  }

  /**
   * This is the main executable function that is called by the Commands class
   * It calls other functions in order to print the contents of the file in
   * the right format
   *
   * @param arguments - An array of Strings that consists of the arguments
   *                 from the user
   * @param isAngle - boolean that checks if the redirection operator is
   *                  present or not
   */
  @Override
  public String executeCommand(String[] arguments, boolean isAngle)
  {
    StringBuilder temp1 = new StringBuilder();
    StringBuilder temp2 = new StringBuilder();
    // cat file file2 file3 file5 etc > blah
    for(int i = 1; i < arguments.length; i++)
    {
      String currFile = arguments[i];

      if(!isAngle)
      {
        if(i != 1)
        {
          // Spacing for every file content - 3 line breaks
          System.out.print("\n\n\n");
        }
        // Prints the file from directory
        temp1 = fileGetter(currFile, isAngle);
        if(temp1 == null) {
          break;
        }
      }
      else // isAngle is true " > " eg: cat file1 > file2
      {
        if(!arguments[i].contains(">") && i < arguments.length - 2)
        {
          temp1 = fileGetter(currFile, isAngle);
          if(temp1 == null){
            break;
          }
          temp2 = temp2.append(temp1.toString());
        }
      }
    }
    if(isAngle)
    {
      return Redirection.redirect(arguments, temp2.toString());
    }
    return temp2.toString();
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
  public String getDocumentation(String[] arguments, boolean isAngle)
  {
    String content = "cat FILE1 [FILE2 …]\nDisplay the contents of " +
            "FILE1 and other files (i.e. File2 ….) concatenated in " +
            "the shell\nThree line breaks are used to separate " +
            "contents of one file from another file\n";
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
