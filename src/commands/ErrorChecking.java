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

public class ErrorChecking {

  /**
   * A boolean that keeps track if the userInput contains ">" or ">>"
   */
  protected boolean isAngle = false;

  /**
   * A list of string that keeps hold the appropriate splited userInput
   */
  protected String[] command = new String[0];

  /**
   * This function makes sure that the passed in command in valid (ie. one of the
   * given ones in the 2B assignment"
   *
   * @param command hold the command entered by the user
   * @return A boolean that returns if the command id valid or not
   */
  public boolean validateCommand(String command) {

    return command.equals("exit") || command.equals("mkdir") ||
            command.equals("cd") ||
            command.equals("ls") || command.equals("pwd") ||
            command.equals("pushd") || command.equals("popd") ||
            command.equals("history") || command.equals("cat") ||
            command.equals("echo") || command.equals("man") ||
            command.equals("rm") || command.equals("cp") ||
            command.equals("mv") || command.equals("curl") ||
            command.equals("saveJShell") ||
            command.equals("loadJShell") || command.equals("search")
            || command.equals("tree");
  }

  /**
   * This helper function makes sure that the passed in command in valid
   * (ie. one of the given ones in the 2B assignment"
   *
   * @param userInput A string entered by the user in the mock shell.
   * @param temp      This is a temporary ArrayList that is used in splitting
   *                  the userInput properly
   * @param temp2     This is a temporary ArrayList that is used in splitting
   *                  the userInput properly
   * @param temp3     This is a temporary ArrayList that is used in splitting
   *                  the userInput properly
   * @return String [] commands that holds the appropriately separated String
   * Array.
   */
  private String[] checkAngleHelper(String userInput, ArrayList<String> temp,
                                    ArrayList<String> temp2,
                                    ArrayList<String> temp3) {
    String[] command;
    temp.add(userInput.substring(userInput.indexOf(">")));

    if (temp.get(0).contains(" ")) {
      String[] x = temp.get(0).split(" ");
      for (String s : x) {
        temp2.add(s);
      }
    } else {
      temp2.add(temp.get(0));
    }

    temp3.add(temp.get(1).substring(0, temp.get(1).lastIndexOf(">") + 1));
    temp3.add(temp.get(1).substring(temp.get(1).lastIndexOf(">") + 1));

    command = new String[temp2.size() + temp3.size()];
    int i = 0;
    for (String s : temp2) {
      command[i] = s.trim();
      i++;
    }
    for (String s : temp3) {
      command[i] = s.trim();
      i++;
    }
    return command;
  }

  /**
   * This another helper function makes sure that the passed in command in
   * valid (ie. one of the given ones in the 2B assignment"
   *
   * @param userInput A string entered by the user in the mock shell.
   * @param temp      This is a temporary ArrayList that is used in splitting
   *                  the userInput properly
   * @param temp2     This is a temporary ArrayList that is used in splitting
   *                  the userInput properly
   * @param temp3     This is a temporary ArrayList that is used in splitting
   *                  the userInput properly
   * @return String [] commands that holds the appropriately separated String
   * Array.
   */
  private String[] checkAngleHelper2(String userInput, ArrayList<String> temp,
                                     ArrayList<String> temp2,
                                     ArrayList<String> temp3) {
    String[] command;
    temp.add(userInput.substring(0, userInput.lastIndexOf(">")));
    command = checkAngleHelper(userInput, temp, temp2, temp3);
    return command;
  }

  /**
   * This function checks if the provided command by the user is a valid
   * command or not.
   * valid = belongs to the set of commands given in the assignment handout.
   *
   * @param userInput A string entered by the user in the mock shell.
   * @return A boolean that tells the user if the enter command is a valid
   * command or not. Returns true, if it is a valid command,
   * and false otherwise.
   */
  public String[] checkCommand(String userInput) {

    ArrayList<String> temp = new ArrayList<>();
    ArrayList<String> temp2 = new ArrayList<>();
    ArrayList<String> temp3 = new ArrayList<>();

    if (!userInput.contains(">")) {
      userInput = userInput.replaceAll("( )+", " ");
      command = userInput.split(" ");
    } else {
      userInput = userInput.replaceAll("( )+", " ");
      if (count(userInput, '>') <= 2) {

        if (userInput.trim().contains("> >")) {
          System.out.println("Invalid Syntax for Redirection");
          return null;
        } else if (userInput.contains(">>")) {
          temp.add(userInput.substring(0, userInput.indexOf(">")));
          command = checkAngleHelper(userInput, temp, temp2, temp3);
        } else if (userInput.contains(">")) {
          command = checkAngleHelper2(userInput, temp, temp2, temp3);
        }
      } else {
        System.out.println("Invalid Syntax for Redirection");
        return null;
      }
    }

    if (command.length >= 3) {
      // checks if redirection operator is present in the userInput
      if (userInput.contains(">") && (command[command.length - 2].equals(">")
              || command[command.length - 2].equals(" > ")
              || command[command.length - 2].equals(">>")
              || command[command.length - 2].equals(" >> "))
              && !(checkIllegalCharacter(command[command.length - 1]))
      ) {
        isAngle = true;
      }
    }
    return command;
  }

  private boolean checkRedirect(String[] command) {
    boolean isValid = true;
    switch (command[0]) {
      case "pwd":
      case "tree":
        if (command.length != 1 && command.length != 3) {
          isValid = false;
          System.out.println(command[0] + ": Wrong number of" +
                  " arguments");
        }
        break;
      case "cat":
        if (command.length < 2) {
          isValid = false;
          System.out.println(command[0] + ": Wrong number of" +
                  " arguments");
        }
        break;
      case "history":
        if (command.length != 1 && command.length != 2 &&
                (!isAngle || command.length != 3) &&
                (!isAngle || command.length != 4)) {
          isValid = false;
          System.out.println(command[0] + ": Wrong number of" +
                  " arguments");
        }
        break;
      case "man":
        if (command.length != 2 && command.length != 4) {
          isValid = false;
          System.out.println(command[0] + ": Wrong number of" +
                  " arguments");
        }
        break;
    }
    return isValid;
  }

  private boolean checkSearch(String[] command) {
    boolean isValid;
    isValid = false;

    if ((command[command.length - 2].contains(">") && command.length < 8)
            || command.length < 6) {
      System.out.println(command[0] + ": Wrong number of arguments");
      return false;
    }
    int i;
    for (i = 1; i < command.length; i++) {
      if (command[i - 1].equals("-type") &&
              (command[i].equals("d") || command[i].equals("f"))
              && (command[i + 1].equals("-name"))
              && (command[i + 2].startsWith("\"")
              && command[i + 2].endsWith("\""))) {
        isValid = true;
        break;
      }
    }
    if (i == command.length) {
      System.out.println("Invalid format of search command!");
    }
    return isValid;
  }

  /**
   * This method checks if the user had entered correct number of arguments
   * depending on the
   * command which makes a command valid.
   *
   * @param userInput A string entered by the user in the mock shell.
   * @return A boolean that tells the user if the number of arguments for
   * command is valid or not. Returns true, if it is valid number of
   * arguments, and false otherwise.
   */

  public boolean checkNumOfArguments(String userInput) {
    boolean isValid = true;
    // special check for echo before checking any other commands
    if (isEcho(userInput)) {
      return echoCheck(userInput);
    }
    command = checkCommand(userInput);
    if (command != null) {
      if (!validateCommand(command[0])) {
        System.out.println("Invalid command");
        return false;
      } else {
        for (String s : command) {
          if (s.equals("> >")) {
            System.out.println("Invalid syntax for redirection");
            return false;
          }
        }
      }

      switch (command[0]) {
        case "pwd":
        case "cat":
        case "history":
        case "man":
        case "tree":
          isValid = checkRedirect(command);
          break;
        case "exit":
        case "popd":
          if (command.length != 1
                  && (!isAngle || command.length != 3)) {
            isValid = false;
            System.out.println(command[0] + ": Wrong number " +
                    "of arguments");
          }
          break;
        // These are the commands with exactly 1 argument
        case "rm":
        case "cd":
        case "curl":
        case "pushd":
        case "saveJShell":
        case "loadJShell":
          if (command.length != 2 && (!isAngle
                  || command.length != 4)) {
            isValid = false;
            System.out.println(command[0] + ": Wrong number " +
                    "of arguments");
          }
          break;
        // These are the commands with at least 1 argument
        // not checking for redirection because there is no
        // restriction on number of args.
        case "mkdir":
          if (command.length < 2) {
            isValid = false;
            System.out.println(command[0] + ": Wrong number " +
                    "of arguments");
          }
          break;
        //These are the commands with exactly two arguments
        case "mv":
        case "cp":
          if (command.length != 3 && (!isAngle
                  || command.length != 5)) {
            isValid = false;
            System.out.println(command[0] + ": Wrong number " +
                    "of arguments");
          }
          break;
        case "search":
          isValid = checkSearch(command);
          break;
      }
    }
    return isValid;
  }

  /**
   * A helper function to determine whether the user potentially typed in the echo commnad
   *
   * @param userInput - A string that contains the full user input
   * @return - A boolean that determines whether the user potentially typed in the echo command
   */
  public boolean isEcho (String userInput){
    if (userInput.length() < 4) {
      return false;
    }
    return validateEcho(userInput);
  }

  /**
   * A helper function that validates whether the first 4 characters of
   * the user input is echo in order to determine whether to run
   * echoCheck
   *
   * @param userInput - A string that contains the full user input
   * @return - A boolean that determines whether the first 4 characters
   * of userInput is echo
   */
  public boolean validateEcho (String userInput){
    return userInput.charAt(0) == 'e' &&
            userInput.charAt(1) == 'c' &&
            userInput.charAt(2) == 'h' &&
            userInput.charAt(3) == 'o';
  }

  /**
   * A helper function that checks the echo command case specifically as
   * it involves quotations that makes it differ from other redirection
   * commands.
   *
   * @param userInput - A string that contains the full user input
   * @return - A boolean that determines whether the format for an echo
   * command is valid or not
   */
  public boolean echoCheck (String userInput){
    userInput = userInput.replaceAll("\\s", "");
    String[] quotesCheck = userInput.split("\"");
    boolean isValid = true;
    String command = "echo";

    // checking for missing operand or invalid command
    if (userInput.length() == 4) {
      System.out.println(command + ": Missing operand");
      return false;
    }
    else if (!(userInput.charAt(4) == ' ' || userInput
            .charAt(4) == '\"')) {
      System.out.println(command + ": Invalid command");
      return false;
    }
    // checking for quotes in a string
    else if (count(userInput, '"') > 2) {
      System.out.println(command +
              ": invalid string " +
              "(double quotations inside string)");
      isValid = false;
    }
    // checking for missing quotation mark
    else if (count(userInput, '"') < 2) {
      System.out.println(command +
              ": invalid string (needs both quotations marks)");
      isValid = false;
    }
    // checking for missing string
    else if (userInput.trim().charAt(4) != '"' && userInput.trim()
            .charAt(5) != '"') {
      System.out.println(command +
              ": invalid string (needs quotations marks)");
      isValid = false;
    }
    // checking for illegal characters in file name
    else if (quotesCheck.length > 2) {
      isAngle = true;
      String path = userInput.
              substring(userInput.lastIndexOf(">") + 1);

      if(quotesCheck[2].contains("> >")) {
        System.out.println(command + ":invalid syntax");
        isValid = false;
      }

      if (count(quotesCheck[quotesCheck.length - 1],
              '>') > 2) {
        System.out.println(command +
                ": invalid file name (illegal characters)");
        isValid = false;
      } else if (path.length() > 0 && path.charAt(0) == ' ') {
        String pathWithSpace = path.substring(1);
        if (checkIllegalCharacter(pathWithSpace)) {
          System.out.println(command +
                  ": invalid file name (illegal characters)");
          isValid = false;
        }
      } else if (checkIllegalCharacter(path)) {
        System.out.println(command +
                ": invalid file name (illegal characters)");
        isValid = false;
      }
    }
    this.command = quotesCheck;
    return isValid;
  }

  /**
   * Returns the number of times character appears in input
   *
   * @param input A string in which the user inputs
   * @param character A char in which its appearance in input will be
   *                  counted
   * @return the number of times character appears in input
   */
  public int count (String input,char character){
    int countChar = 0;

    for (int i = 0; i < input.length(); i++) {
      if (input.charAt(i) == character) {
        countChar++;
      }
    }

    return countChar;
  }

  /**
   * Returns a boolean indicating whether string contains illegal characters
   * which are: '/', '.', ' ', '!', '@', '#', '$', '%', '^', '&', '*',
   * '(', ')', '{', '}', '~', '|', '<', '>', '?'
   *
   * @param string a string that will be checked to determine whether it
   *               contains illegal character
   * @return a boolean indicating whether string contains illegal character.
   * Returns true if the string contains any of the above mentioned
   * characters, and false otherwise.
   */
  public boolean checkIllegalCharacter (String string) {
    return string.contains(".") ||
            string.contains(" ") || string.contains("!") ||
            string.contains("@") || string.contains("#") ||
            string.contains("$") || string.contains("%") ||
            string.contains("&") || string.contains("*") ||
            string.contains("(") || string.contains(")") ||
            string.contains("{") || string.contains("}") ||
            string.contains("~") || string.contains("|") ||
            string.contains("<") || string.contains(">") ||
            string.contains("?") || string.contains("\"");
  }

  /**
   * Returns void, but this function passes the user input to the commands
   * class if the command entered by the user is valid and if each command
   * has correct number of arguments. Otherwise it prints an appropriate
   * error message on the mock shell.
   *
   * @param userInput A string entered by the user in the mock shell.
   */
  public void overallCheck (String userInput) throws IOException {

    if (checkNumOfArguments(userInput)) {
      Commands cmd = new Commands(userInput, command, isAngle);
      cmd.execute();
      isAngle = false;
    }
  }
}