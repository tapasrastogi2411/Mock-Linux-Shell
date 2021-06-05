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

import java.io.*;
import java.io.File;

public class LoadJShell extends Commands implements java.io.Serializable {

  /**
   * Loads specified JShell and its contents
   *
   * @param file A string that contains that name/path of the file
   *             that stores the JShell and its contents
   */
  @Override
  public String executeCommand(String file) {

    // ensuring that the user is only able to load JShell in the beginning
    // of running a new JShell
    if(PromptUserInput.inputHistory.size() != 1) {
      return "Cannot load JShell from " + file + " when this current " +
              "JShell is running";
    }

    File temp = new File(file);

    if(!temp.exists()) { // case when file provided doesn't exist
      return "File provided doesn't exit";
    }
    else {
      try {
        BufferedReader br = new BufferedReader(new FileReader(temp));
        String line;
        // clearing input history to load
        PromptUserInput.inputHistory.clear();
        PromptUserInput.totalNumberOfCommands--;

        // loop through when there is still things to be read
        while((line=br.readLine()) != null) {

          // case when rebuilding history
          if(history(line)) {
            if(!line.equals("\n")) {
              String input = line.substring(3);

              PromptUserInput.totalNumberOfCommands++;
              PromptUserInput.inputHistory.add(
                      PromptUserInput.totalNumberOfCommands
                              + ". " + input);

              // case when the line is a valid pushd command
              if(isPushD(line)) {
                String tempLine = line.substring(9);
                Directory dir = FileSystem.root
                        .getDirectoryFromPath(tempLine,
                                FileSystem.currentDirectory,
                                FileSystem.root);

                // case when the directory path given is a
                // valid directory
                if(dir != null) {
                  ErrorChecking e = new ErrorChecking();
                  e.overallCheck(line.substring(3));
                }
              }
            }
          }
          // case when making directory or file
          else if(line.contains("mkdir") || line.contains("echo")){
            ErrorChecking e = new ErrorChecking();
            e.overallCheck(line);
          }
        }
        PromptUserInput.totalNumberOfCommands++;
        PromptUserInput.inputHistory.add(PromptUserInput
                .totalNumberOfCommands + ". loadJShell " + file);
        br.close();

        return "";
      } catch (IOException e) {
        return "Error: Something went wrong when opening file";
      }
    }
  }

  /**
   * A helper function to determine whether the line read is part of the
   * history section
   *
   * @param line - A string that looks at a specific line in the file
   * @return A boolean indicating whether the line is apart of the
   * history section
   */
  private boolean history(String line) {
    return line.charAt(0) == '1' || line.charAt(0) == '2' ||
            line.charAt(0) == '3' || line.charAt(0) == '4' ||
            line.charAt(0) == '5' || line.charAt(0) == '6' ||
            line.charAt(0) == '7' || line.charAt(0) == '8' ||
            line.charAt(0) == '9';
  }

  /**
   * A helper function to determine whether the line is a pushd command
   *
   * @param line - A string that looks at a specific line in the file
   * @return A boolean indicating whether the line is a pushd command
   */
  private boolean isPushD(String line) {
    return line.contains("pushd ") && line.charAt(3) == 'p' &&
            line.charAt(4) == 'u' && line.charAt(5) == 's' &&
            line.charAt(6) == 'h' && line.charAt(7) == 'd' &&
            line.charAt(8) == ' ';
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
    String content = "loadJShell\n" + "Loads the JShell and all of " +
            "its contents" + "so that the user can access these " +
            "contents\n";
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
