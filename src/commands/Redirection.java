package commands;

public class Redirection {

  /**
   * Helps to redirect output from stdout to a respective file
   * @param arguments list of arguments taken as strings which is given by
   *                  the user
   * @param output the output which needs to be redirected from stdout to a
   *               respective file
   */
  public static String redirect(String[] arguments, String output) {

    if(arguments[arguments.length - 1].contains("/")) {

      // case when file name isn't provided (ie. path ends with the '/' character
      if(arguments[arguments.length-1].charAt(arguments[arguments.length-1].length() - 1) == '/') {
        return "Please provide file name at the end of path " +
                "(ie. don't end the path with the character '/'";
      }

      // getting the  path of the parent directory
      String parentPath = arguments[arguments.length - 1].substring(0,
              arguments[arguments.length - 1].lastIndexOf("/"));

      // getting the parent directory of the file in the given absolute path
      Directory parentDir
              = FileSystem.root.getDirectoryFromPath(parentPath.trim(),
              FileSystem.currentDirectory, FileSystem.root);

      // checking whether the given parent directory from the path exists in the file system
      if(parentDir == null) {
        return "Path provided doesn't exist";
      }
      else {
        File file = parentDir.getFile(
                arguments[arguments.length - 1].trim().
                        substring(arguments[arguments.length - 1].lastIndexOf("/")));

        // case for when the given file doesn't exist,
        // obtaining the file name by splitting over "/"
        if(file == null) {
          String[] slashCheck = arguments[arguments.length - 1].split("/");

          File newFile = new File(slashCheck[slashCheck.length - 1],
                  parentDir);
          newFile.setContents(output);
          return "";
        }

        // Appends or overwrites the string given by the user into the given file
        else {
          appendOrOverwriteHelper(arguments, file, output);
          return "";
        }
      }
    }

    // handles the relative path case
    else {
      File file = FileSystem.currentDirectory.getFile(
              arguments[arguments.length - 1].trim());

      // case for when the given file doesn't exist
      if(file == null) {
        File newFile = new File(arguments[arguments.length - 1].trim(),
                FileSystem.currentDirectory);
        newFile.setContents(output);
        return "";
      }
      // Appends or overwrites the string given by the user into the
      // given file
      else {
        appendOrOverwriteHelper(arguments, file, output);
        return "";
      }
    }
  }

  /**
   * A helper function that either appends or overwrites the string given
   * by user into file
   *
   * @param arguments - An array of strings of the user input splitted by
   *                  quotes
   * @param file - A file that the string will be either appended or
   *             overwritten into
   */
  private static void appendOrOverwriteHelper(String[] arguments, File file,
                                              String output) {
    // Appends the string given by the user into the given file
    if(arguments[arguments.length - 2].contains(">>")) {
      String fileContent = file.getContents();
      String newFileContent = fileContent + output;
      file.setContents(newFileContent);
    }
    // Overwrites the string given by the user into the given file
    else {
      file.setContents(output);
    }
  }
}
