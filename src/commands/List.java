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
import java.util.ArrayList;

public class List extends Commands {

  /**
   * Returns an array of Strings with the root added at the last argument
   * in the argument List
   * @param root The path of the root
   * @param arguments Array of arguments given by the user
   * @param len The initial length of arguments array
   * @return an array of Strings with root added at the last index
   */
  public static String[] addRoot(String root, String[] arguments, int len)
  {
    int i;
    // create a new array of size n+1
    String newarr[] = new String[len + 1];
    for (i = 0; i < len; i++)
      newarr[i] = arguments[i];
    newarr[len] = root;
    return newarr;
  }

  /**
   * Prints the content including directories and files of the directory
   * given by the user in the format of a path in the mock file system.
   *
   * @param arguments An array that consists of the arguments from the user
   */
  @Override
  public String executeCommand(String[] arguments, StringBuilder sb) {

    int x;
    Directory tempDir;

    if(arguments.length == 1) {
      ArrayList<String> children =
              FileSystem.currentDirectory.getContents();

      for(String child : children) {
        sb.append(child + "\n");
      }
      return sb.toString();
    }

    else {
      if(arguments[1].equals("-R") && arguments.length == 2)
      {
        arguments = addRoot("/", arguments, arguments.length);
        x = 2;
      }
      else if(arguments[1].equals("-R")){
        x = 2;
      }
      else
      {
        x = 1;
      }
      for(int i = x; i < arguments.length; i++) {

        tempDir =
                Directory.getDirectoryFromPath(arguments[i],
                        FileSystem.currentDirectory, FileSystem.root);
        File tempFile =
                File.getFileFromPath(arguments[1],
                        FileSystem.currentDirectory, FileSystem.root);

        if(tempDir != null) {
          ArrayList<String> children = tempDir.getContents();

          if(children != null){

            if(tempDir.getName().equals(""))
            {
              sb.append(tempDir.getName() + "/" + ": ");
            }
            else
            {
              sb.append(tempDir.getName() + ": ");
            }

            for(String child : children) {
              sb.append(child + " ");
            }

            sb.append("\n");
          }

        }

        else if(tempFile != null) {
          sb.append(tempFile.getName() + "\n");
        }

        else {
          sb.append("Argument given is neither directory " +
                  "nor file");
        }

        if(arguments[1].equals("-R"))
        {
          if(tempDir != null)
          {
            ArrayList<String> childrenPath = tempDir.getContentsPath();

            if(childrenPath != null)
            {
              for(int j = 0; j < childrenPath.size(); j++) //
              // looping through each children path
              {
                String[] list = {"ls", "-R",
                        childrenPath.get(j)};

                String s = executeCommand(list,
                        new StringBuilder());
                if(!s.equals("Argument given is neither " +
                        "directory " +
                        "nor file"))
                {
                  sb.append(s);
                }
              }
            }
          }
        }
      }
      return sb.toString();
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
    String content = "ls [PATH]…]\n" + "If no path is given, then " +
            "print the content of " + "the current working " +
            "directory\n" + "Otherwise, for each path given, print " +
            "the content"+ " of that directory If p specifies a file," +
            " then the given " + "path and if the given path " +
            "specifies a directory, then " + "print the content of " +
            "that directory with an extra new line\n" + "If the given" +
            " path does not exist then prints " + "'Invalid Command'\n";
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
