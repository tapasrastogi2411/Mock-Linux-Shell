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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PromptUserInput {

  /**
   * an instance variable that keeps track of the input history in a FIFO
   * structure
   */
  public static ArrayList<String> inputHistory = new ArrayList<>();

  /**
   * a counter in order to keep track of the number of elements in the queue
   */
  public static int totalNumberOfCommands = 0;

  /**
   * Keeps a continual input prompt until an exit command is given
   */
  public void executeContinualInput() throws IOException {
    // errorChecking instance to pass the commands to
    ErrorChecking errorCheck = new ErrorChecking();
    // scanner and input variables to get the commands from the user
    Scanner scanner = new Scanner(System.in);
    String input;

    // the while loop that continually prompts the user for input
    // only stops when the exit command is called
    while(true) {
      if(FileSystem.currentDirectory == FileSystem.root) {
        System.out.print(FileSystem.root.getName() +  "/ ");
      }
      else {
        System.out.print(FileSystem.currentDirectory.getPath() + " ");
      }
      input = scanner.nextLine();
      totalNumberOfCommands++;
      inputHistory.add(totalNumberOfCommands + ". " + input);
      errorCheck.overallCheck(input.trim());
    }
  }
}

