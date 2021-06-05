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

public class PrintDocumentation extends Commands {

  /**
   * Prints the documentation(s) of the command(s) provided
   * by the user
   *
   * @param arguments An array of string that contains the command(s)
   *                  that the user wants the documentation(s) for
   */
  @Override
  public String executeCommand(String[] arguments, boolean isAngle) {

    if(arguments[1].equals("exit")) {
      Exit exit = new Exit();
      return exit.getDocumentation(arguments, isAngle);
    }
    else if(arguments[1].equals("mkdir")) {
      MakeDirectory makeDir = new MakeDirectory();
      return makeDir.getDocumentation(arguments, isAngle);
    }
    else if(arguments[1].equals("cd")) {
      ChangeDirectory changeDir = new ChangeDirectory();
      return changeDir.getDocumentation(arguments, isAngle);
    }
    else if(arguments[1].equals("ls")) {
      List list = new List();
      return list.getDocumentation(arguments, isAngle);
    }
    else if(arguments[1].equals("pwd")) {
      PrintWorkingDirectory pwd = new PrintWorkingDirectory();
      return pwd.getDocumentation(arguments, isAngle);
    }
    else if(arguments[1].equals("pushd")) {
      PushDirectory pushDirectory = new PushDirectory();
      return pushDirectory.getDocumentation(arguments, isAngle);
    }
    else if(arguments[1].equals("popd")) {
      PopDirectory popDirectory = new PopDirectory();
      return popDirectory.getDocumentation(arguments, isAngle);
    }
    else if(arguments[1].equals("history")) {
      PrintRecentCommands history =
              new PrintRecentCommands(arguments, arguments.length);
      return history.getDocumentation(arguments, isAngle);
    }
    else if(arguments[1].equals("cat")) {
      Concatenate cat = new Concatenate();
      return cat.getDocumentation(arguments, isAngle);
    }
    else if(arguments[1].equals("echo")) {
      Echo echo = new Echo();
      return echo.getDocumentation(arguments, isAngle);
    }
    else if(arguments[1].equals("man")) {
      return getDocumentation(arguments, isAngle);
    }
    else if(arguments[1].equals("curl")) {
      Curl curl = new Curl();
      return curl.getDocumentation(arguments, isAngle);
    }
    else if(arguments[1].equals("cp")){
      Copy cp = new Copy();
      return cp.getDocumentation(arguments, isAngle);
    }
    else if(arguments[1].equals("search")){
      Search search = new Search();
      return search.getDocumentation(arguments, isAngle);
    }
    else if(arguments[1].equals("tree")){
      Tree tree = new Tree();
      return tree.getDocumentation(arguments, isAngle);
    }
    else{
      return "This command doesn't exist";
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
    String content = "man CMD\nPrint documentation for " +
            "CMD";
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
