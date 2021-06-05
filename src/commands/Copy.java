package commands;

public class Copy extends Commands{

  /**
   * This function copies a existing file into a non-existing file.
   *
   * @param arguments An array that consists of the arguments from the user.
   * @param firstPathFile A existing file that is at the first path given by
   *                      the user while calling the cp/mv command.
   */
  private StringBuilder existingFileToNonExistingFile(String[] arguments,
                                                      File firstPathFile) {
    StringBuilder sb = new StringBuilder();
    // split the full path into parent path and file name
    String fileName = arguments[2].substring(
            arguments[2].lastIndexOf("/") + 1);
    String parentPath = arguments[2].substring(0,
            arguments[2].lastIndexOf("/") + 1);
    // get the parent from the parent path
    Directory parent = Directory.getDirectoryFromPath(parentPath,
            FileSystem.currentDirectory, FileSystem.root);
    File newSecondPathFile;

    if(parent != null){
      newSecondPathFile = new File(fileName, parent);
      newSecondPathFile.setContents(firstPathFile.getContents());
    }
    else{
      sb.append("The parent of the given second File does " +
              "not exist in the file system." + "\n");
    }
    return sb;
  }

  /**
   * This function copies a existing directory into a non-existing directory.
   *
   * @param firstPathDir  A directory that is to be copied into the
   *                      secondPathDir
   * @param secondPathDir A directory in which firstPatDir directory is
   *                      copied into
   */
  private void setDirsPath(Directory firstPathDir,
                           Directory secondPathDir) {
    Directory newDir;

    for(Directory dir : firstPathDir.getDirsList()){
      newDir =  new Directory(secondPathDir, dir.getName());
      for(File file : dir.getFilesList()){
        File f = new File(file.getName(), newDir);
        f.setContents(file.getContents());
      }
      setDirsPath(dir, newDir);
    }
  }

  /**
   * This function copies a existing directory into a non-existing directory.
   *
   * @param secondPath A string that is the path of the second non-exiting
   *                    directory provided by the user.
   * @param firstPathDir A existing directory that is at the first path given
   *                     by the user while calling the cp/mv command.
   */
  private StringBuilder existingDirToNonExistingDir(String secondPath,
                                                    Directory firstPathDir) {

    StringBuilder sb = new StringBuilder();
    String directoryName = secondPath.substring(
            secondPath.lastIndexOf("/") + 1);
    String parentPath = secondPath.substring(0,
            secondPath.lastIndexOf("/") + 1);
    // get the parent from the parent path
    Directory parent = Directory.getDirectoryFromPath(parentPath,
            FileSystem.currentDirectory, FileSystem.root);

    if(parent != null){
      Directory newSecondPathDir = new Directory(parent, directoryName);
      Directory newDir = new Directory(newSecondPathDir, firstPathDir.getName());

      for(File file : firstPathDir.getFilesList()){
        File f = new File(file.getName(), newDir);
        f.setContents(file.getContents());
      }
      setDirsPath(firstPathDir, newDir);
    }
    else{
      sb.append("The parent of the given second directory does "
              + "not exist in the file system." + "\n");
    }
    return sb;
  }

  /**
   * This function copies a existing file into a existing directory.
   *
   * @param arguments An array that consists of the arguments from the user.
   * @param firstPathFile A file obtained at the first path of the user
   * @param secondPathDir A file obtained at the second path of the user
   */
  private void existingFileToExistingDir(String[] arguments,
                                         Directory secondPathDir,
                                         File firstPathFile) {
    secondPathDir.addFile(firstPathFile);
    firstPathFile.setParent(secondPathDir);
    firstPathFile.setPath(secondPathDir.getPath() + "/" +
            firstPathFile.getName());
  }

  /**
   * This function helps to execute the functions of cp command
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
  private StringBuilder copyHelper(String[] arguments, Directory firstPathDir,
                                   Directory secondPathDir, File firstPathFile,
                                   File secondPathFile) {


    StringBuilder sb = new StringBuilder();

    // cp existing_dir_path  existing_dir_path
    if(firstPathDir != null && secondPathDir != null &&
            firstPathFile == null && secondPathFile == null){
      Directory newDir = new Directory(secondPathDir,
              firstPathDir.getName());

      for(File file : firstPathDir.getFilesList()){
        File f = new File(file.getName(), newDir);
        f.setContents(file.getContents());
      }
      setDirsPath(firstPathDir, newDir);
    }
    //cp existing_file_path1    existing_file_path2
    else if(firstPathDir == null && secondPathDir == null &&
            firstPathFile != null && secondPathFile != null){
      secondPathFile.setContents(firstPathFile.getContents());
      secondPathFile.setPath(firstPathFile.getParent().getPath() + "/"
              + secondPathFile.getName());
    }
    //cp existing_file_path  existing_dir_path
    else if(firstPathDir == null && secondPathDir != null &&
            firstPathFile != null && secondPathFile == null){
      existingFileToExistingDir(arguments, secondPathDir, firstPathFile);
    }
    //cp existing_file_path  new_file_path
    else if(firstPathDir == null && secondPathDir == null &&
            firstPathFile != null && secondPathFile == null){
      sb = existingFileToNonExistingFile(arguments, firstPathFile);
    }
    //cp existing_dir_path   new_dir_path
    else if(firstPathDir != null && secondPathDir == null &&
            firstPathFile == null && secondPathFile == null){
      sb = existingDirToNonExistingDir(arguments[2], firstPathDir);
    }
    //cp existing_dir_path   existing_file_path
    else if(firstPathDir != null && secondPathDir == null &&
            firstPathFile == null && secondPathFile != null){
      sb.append( "You can not copy a directory into a file." + "\n");
    }
    else if(firstPathDir == null){
      sb.append("The first argument does not exist in the " +
              "mock file system." + "\n");
    }

    return sb;
  }

  /**
   * This function allows the user to move items from old path to the new
   * path.
   *
   * @param arguments An array that consists of the arguments from the user
   * @return A string that is used for JunitTesting
   */
  @Override
  public String executeCommand(String[] arguments)
  {

    StringBuilder sb;

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
    if(firstPathFile == null && secondPathFile == null &&(
            firstPathDir != null && secondPathDir != null &&
                    firstPathDir.getPath().equals(secondPathDir.getPath()))
    ){
      return "A directory can not be copied within itself";
    }
    else if(firstPathDir == null && secondPathDir == null &&
            (firstPathFile != null && secondPathFile != null) &&
            firstPathFile.getPath().equals(secondPathFile.getPath())){
      return "A file can not be copied within itself";
    }
    else if(arguments[1].equals("/")){
      return "Cannot operate on root";
    }
    else{
      sb = copyHelper(arguments, firstPathDir,
              secondPathDir,
              firstPathFile, secondPathFile);
    }
    return sb.toString();
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
    String content = "cp OLDPATH NEWPATH\nCopy the given item from " +
            "OLDPATH to NEWPATH. If OLDPATH is a directory, " +
            "recursively copy the contents.\n";
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
