package commands;

public class Move extends Copy{

  /**
   * This function helps to execute the functions of mv command
   *
   * @param arguments An array that consists of the arguments from the user.
   * @param firstPathDir A directory obtained at the first path of the user
   *                     (if it exists)
   */
  private void removeFirstDir(String[] arguments, Directory firstPathDir) {
    Directory parent = firstPathDir.getParent();
    Remove rm = new Remove();
    rm.executeCommand(arguments);
    parent.deleteDirectory(firstPathDir);
  }

  /**
   * This function helps to execute the functions of mv command
   *
   * @param arguments An array that consists of the arguments from the user.
   * @param firstPathDir A directory obtained at the first path of the user
   *                     (if it exists)
   * @param secondPathDir A directory obtained at the second path of the user
   *                      (if it exists)
   * @param firstPathFile A file obtained at the first path of the user
   *                      (if it exists)
   * @param secondPathFile A file obtained at the second path of the user
   *                       (if it exists)
   */
  private void moveHelper(String[] arguments, Directory firstPathDir,
                          Directory secondPathDir, File firstPathFile,
                          File secondPathFile) {
    // mv existing_dir_path  existing_dir_path
    if (firstPathDir != null && secondPathDir != null &&
            firstPathFile == null && secondPathFile == null) {
      removeFirstDir(arguments, firstPathDir);
    }
    //mv existing_file_path1    existing_file_path2
    else if (firstPathDir == null && secondPathDir == null &&
            firstPathFile != null && secondPathFile != null) {
      Directory temp = firstPathFile.getParent();
      temp.deleteFile(firstPathFile);
    }
    //mv existing_file_path  existing_dir_path
    else if (firstPathDir == null && secondPathDir != null &&
            firstPathFile != null && secondPathFile == null) {
      Directory temp = firstPathFile.getParent();
      temp.deleteFile(firstPathFile);
    }
    else if(firstPathDir != null && secondPathDir == null &&
            firstPathFile == null && secondPathFile == null){
      removeFirstDir(arguments, firstPathDir);
    }
    //mv existing_file_path  new_file_path
    else if (firstPathDir == null && secondPathDir == null &&
            firstPathFile != null && secondPathFile == null) {
      Directory temp = firstPathFile.getParent();
      temp.deleteFile(firstPathFile);
    }
  }

  /**
   * This function allows the user to move items from old path to the new
   * path and deletes the item from the old file system.
   *
   * @param arguments An array that consists of the arguments from the user
   */
  @Override
  public String executeCommand(String[] arguments)
  {

    String s;
    s = super.executeCommand(arguments);
    if(s.equals("A directory can not be copied within itself") ||
            s.equals("A file can not be copied within itself") ||
            s.equals("Cannot operate on root")){

      return s;
    }
    Directory firstPathDir = Directory.getDirectoryFromPath(arguments[1],
            FileSystem.currentDirectory, FileSystem.root);
    Directory secondPathDir = Directory.getDirectoryFromPath(arguments[2],
            FileSystem.currentDirectory, FileSystem.root);
    File firstPathFile =
            File.getFileFromPath(arguments[1],
                    FileSystem.currentDirectory, FileSystem.root);
    File secondPathFile =
            File.getFileFromPath(arguments[2],
                    FileSystem.currentDirectory, FileSystem.root);

    moveHelper(arguments, firstPathDir, secondPathDir, firstPathFile,
            secondPathFile);
    return s;
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
    String content = "cp OLDPATH NEWPATH\n" + "This is a new command " +
            "in Assignment2B. Like " +
            "mv, " + "but don’t remove OLDPATH. If OLDPATHis a " +
            "directory, " + "recursively copy the contents.\n";
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

