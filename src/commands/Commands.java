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

public class Commands {

  /**
   * 	This is the original userInput any of the other classes need to get
   * 	access to it
   */
  protected String originalUserInput = "";

  /**
   * A array of strings that contains the arguments given by the user input
   */
  protected String [] arguments;

  /**
   * A string that holds the command given by the user
   */
  protected String command;

  /**
   * A string the holds the entire user input
   */
  protected String userInput;

  /**
   * A boolean that keeps track if the userInput contains ">" or ">>"
   */
  protected boolean isAngle;

  /**
   * Default constructor for the Commands class
   */
  public Commands() {
    this.arguments = null;
    this.command = "";
    this.userInput = "";
    this.isAngle = false;
  }

  /**
   * The constructor for Commands that is called whenever a command is given
   * by the user
   *
   * @param userInput The user input given by the user
   */
  public Commands(String userInput, String[] arguments, boolean isAngle) {
    this.originalUserInput = userInput;
    this.arguments = arguments;
    this.command = arguments[0];
    this.userInput = userInput.replaceAll("( )+", " ");
    this.isAngle = isAngle;
  }

  /**
   * Calls the subclasses based off of the user input and calls for them
   * to execute their respective functions
   */
  public void execute() throws IOException {
    String s = "";

    if(command.equals("rm")) {
      Remove remove = new Remove();
      s = remove.executeCommand(arguments);
    }
    else if(command.equals("mkdir")) {
      MakeDirectory makeDir = new MakeDirectory();
      s = makeDir.executeCommand(arguments, isAngle);
    }
    else if(command.equals("cd")) {
      ChangeDirectory changeDir = new ChangeDirectory();
      s = changeDir.executeCommand(userInput);
    }
    else if(command.equals("ls")) {
      List list = new List();
      s = list.executeCommand(arguments, new StringBuilder());
    }
    else if(command.equals("pwd")) {
      PrintWorkingDirectory pwd = new PrintWorkingDirectory();
      s = pwd.executeCommand(arguments, isAngle);
    }
    else if(command.equals("pushd")) {
      PushDirectory pushD = new PushDirectory();
      s = pushD.executeCommand(arguments[1]);
    }
    else if(command.equals("popd")) {
      PopDirectory popD = new PopDirectory();
      s = popD.executeCommand();
    }
    else if(command.equals("history")) {
      PrintRecentCommands history = new PrintRecentCommands(arguments,
              arguments.length);
      s = history.executeCommand(arguments, isAngle);
    }
    else if(command.equals("cat")) {
      Concatenate cat = new Concatenate();
      s = cat.executeCommand(arguments, isAngle);
    }
    else if(command.trim().equals("echo")) {
      Echo echo = new Echo();
      s = echo.executeCommand(originalUserInput, isAngle);
    }
    else if(command.equals("man")) {
      PrintDocumentation printDoc = new PrintDocumentation();
      s = printDoc.executeCommand(arguments, isAngle);
    }
    else if(command.equals("exit")){
      Exit exit = new Exit();
      s = exit.executeCommand();
    }
    else if(command.equals("cp")){
      Copy copy = new Copy();
      s = copy.executeCommand(arguments);
    }
    else if(command.equals("mv")){
      Move mv = new Move();
      s = mv.executeCommand(arguments);
    }
    else if(command.equals("curl")){
      Curl curl = new Curl();
      s = curl.executeCommand(arguments);
    }
    else if(command.equals("saveJShell")){
      SaveJShell saveJShell = new SaveJShell();
      s = saveJShell.executeCommand(arguments[1]);
    }
    else if(command.equals("loadJShell")){
      LoadJShell loadJShell = new LoadJShell();
      s = loadJShell.executeCommand(arguments[1]);
    }
    else if(command.equals("search")){
      Search search = new Search();
      s = search.executeCommand(arguments, isAngle);
    }
    // Added this block of code to account for the tree command
    else if(command.equals("tree")){
      Tree tree = new Tree();
      s = tree.executeCommand(arguments, isAngle);
    }

    if (!s.equals("")) {
      System.out.println(s);
    }
  }

  /**
   * Gets the Command that the user has entered
   *
   * @return - The command that the user has entered
   */
  public String getCommand() {
    return this.command;
  }

  /**
   * Prints the documentation of the command
   */
  public String getDocumentation(String[] arguments, boolean isAngle) {
    return "This is the documentation for the input command";
  }

  /**
   * Parent function that executes commands where subclasses override from,
   * specifically the ones that take in the array of strings as an argument
   *
   * @param arguments An array that consists of the arguments from the user
   */
  public String executeCommand(String[] arguments) {
    return "This is the function for the subclasses" +
            " to override from to execute their respective commands " +
            "where it receives an array of strings as its arguments";
  }

  /**
   * Parent function that executes commands where subclasses override from,
   * specifically the ones that take in the array of strings as an argument
   *
   * @param userInput A string that consists of the full input from the user
   */
  public String executeCommand(String userInput) throws IOException {
    return "This is the function for the subclasses" +
            " to override from to execute their respective commands " +
            "where it receives a string as its argument";
  }

  public String executeCommand(String[] arguments, boolean isAngle) {
    return "This is the function for the subclasses" +
            " to override from to execute their respective commands " +
            "where it receives a string as its arguments";
  }

  public String executeCommand(String userInput, boolean isAngle) {
    return "This is the function for the subclasses" +
            " to override from to execute their respective commands " +
            "where it receives a string as its arguments";
  }

  /**
   * Parent function that executes commands where subclasses override from
   */
  public String executeCommand() {
    return "This is the function for the subclasses" +
            " to override from to execute their respective commands";
  }

  public String executeCommand(String[] arguments, StringBuilder sb) {
    return "This is the function for the subclasses" +
            " to override from to execute their respective commands";
  }
}
