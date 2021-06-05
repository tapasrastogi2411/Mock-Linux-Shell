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

public class PrintRecentCommands extends Commands {

  /**
   * Instance variable that contains arguments as a list of strings
   */
  protected String[] arguments;

  /**
   * Instance variable that contains the length of the argument as an integer
   */
  protected int argumentLength;

  /**
   * This is a parametrize constructor that takes two different arguments
   * and creates an instance of PrintRecentCommands.
   *
   * @param arguments is a list string that holds both the command inputted
   *                  by the user and the number (if provided) in the format
   *                  of a string.
   * @param argumentLength is an int holding the length of arguments
   */
  public PrintRecentCommands(String[] arguments, int argumentLength){
    this.arguments = arguments;
    this.argumentLength = argumentLength;
  }

  /**
   * If the user does not provide an int after the history command, then this
   * function prints all the commands that has been entered in the shell.
   * If the number of commands is given then this command prints that number
   * of recently entered commands.
   * @param arguments List of arguments
   * @param isAngle boolean that checks if the redirection operator is
   *        present or not
   */
  @Override
  public String executeCommand(String[] arguments, boolean isAngle) {
    StringBuilder sb = new StringBuilder();
    if(argumentLength == 1 || argumentLength == 3)
    {
      for (String s : PromptUserInput.inputHistory)
      {
        sb.append(s + "\n");
      }
      if(isAngle)
      {
        return Redirection.redirect(arguments, sb.toString());
      }
      return sb.toString();
    }
    else if(argumentLength == 2 || argumentLength == 4)
    {
      int i = 1;
      int x = 0;

      try{
        x = Integer.parseInt(arguments[1]);
      }
      catch(Exception e){
        return "Second Argument of history must be" +
                " an integer";
      }

      int total = PromptUserInput.inputHistory.size();
      if(x > total){
        for (String s : PromptUserInput.inputHistory)
        {
          sb.append(s + "\n");
        }
        if(isAngle)
        {
          return Redirection.redirect(arguments, sb.toString());
        }
        return sb.toString();
      }
      else if(x < 0){
        return "history: -1: invalid option";
      }
      else{
        x = total-x;
        for (i = x; i < total; i++)
        {
          sb.append(PromptUserInput.inputHistory.get(i) + "\n");
        }
        if(isAngle)
        {
          return Redirection.redirect(arguments, sb.toString());
        }
        return sb.toString();
      }
    }
    return "Something went wrong in History";
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
    String content = "history [number]\n" + "Prints all the recent " +
            "commands with a new line " +
            "followed if the number of commands are not specified\n" +
            "If the number of commands is given then this " +
            "command prints that number of recently entered commands" +
            ".\n";
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
