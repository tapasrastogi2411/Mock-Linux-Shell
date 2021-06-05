package commands;

import java.util.Scanner;

public class Search extends Commands {

  /**
   * Responsible to implement the search command in order to search for
   * files/directories given
   * a particular name
   * @param arguments Array of Strings representing the arguments given by
   *                  the user
   * @param isAngle boolean that checks if the redirection operator is
   *      *                present or not
   */
  @Override
  public String executeCommand(String[] arguments, boolean isAngle)
  {
    StringBuilder sb = new StringBuilder();
    StringBuilder pathBuilder;
    Scanner scanner = null;
    int i;
    int start, end = 0, flag = 0;
    String[] expression;
    File newFile;
    Directory newDir;
    start = 1; // syntax is : search paths... -type [f/d] -name expression

    for(i = 1; i < arguments.length; i++)
    {
      if(arguments[i-1].equals("-type") &&
              (arguments[i].equals("d") || arguments[i].equals("f")))
      {
        end = i-1; // at -type
        break;
      }
    }

    expression = arguments[end + 3].split("\"");

    for(i = start; i < end; i++)
    {
      pathBuilder = new StringBuilder(); // re-initialize pathBuilder
      // as we need to empty it everytime a new path is encountered
      flag = 0;

      Directory dir = Directory.getDirectoryFromPath(arguments[i],
              FileSystem.currentDirectory, FileSystem.root);
      if(dir == null)
      {
        return "Invalid Path given!";
      }

      String tree = FileSystem.buildTree(dir,
              0, new StringBuilder(), false);

      scanner = new Scanner(tree);
      int spaces = 0; // start from root, spaces always starts from 0

      if(dir.getPath().equals(""))
      {
        pathBuilder.append(dir.getPath());
      }
      else
      {
        pathBuilder.append("//");
        pathBuilder.append(dir.getPath());
      }

      int numSpaces = 0;
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        //process the line

        String[] split = line.split("\t");
        numSpaces = split.length - 1;

        if(numSpaces != spaces)
        {
          if(numSpaces > spaces)
          {
            spaces = numSpaces;
            pathBuilder.append("/" + line.trim());
            // this is a path to a
            // directory/file now
          }
          else if(numSpaces < spaces)
          {
            String x;
            String path = pathBuilder.substring(2);

            int factor = spaces - numSpaces + 1;
            spaces = numSpaces;
            for(int j = 1; j <= factor; j++)
            {
              if(path.lastIndexOf("/") != 0) // eg : /a/file1
              {
                x = path.substring(0,
                        path.lastIndexOf(
                                "/"));
              }
              else
              {
                x = "";
              }

              pathBuilder = new StringBuilder();
              pathBuilder.insert(0, "/");
              pathBuilder.insert(1, "/");
              pathBuilder.append(x);
              path = x;
            }
            pathBuilder.append("/" + line.trim());
          }
        }
        else
        {
          if(pathBuilder.lastIndexOf("/") == 0 ||
                  pathBuilder.lastIndexOf("/") == 1 ||
                  pathBuilder.length() == 0)
          {
            pathBuilder = new StringBuilder();
            pathBuilder.append("/" + line.trim());
          }
          else
          {
            String x = pathBuilder.substring(0,
                    pathBuilder.lastIndexOf(
                            "/"));
            if(!x.equals("/"))
            {
              pathBuilder = new StringBuilder();
              pathBuilder.append(x);
              if(line.trim().indexOf("/") == 0)
              {
                pathBuilder.append(line.trim());
              }
              else
              {
                pathBuilder.append("/" + line.trim());
              }
            }
          }
        }

        if(arguments[end + 1].equals("f"))
        {
          String path = pathBuilder.toString().substring(2);

          newFile = File.getFileFromPath(path,
                  FileSystem.currentDirectory, FileSystem.root);
          if(newFile != null) // means its a file
          {
            if(newFile.getName().equals(expression[1]))
            {
              flag = 1;
              sb.append(newFile.getPath() + "\n");
            }
          }
        }
        else if(arguments[end + 1].equals("d"))
        {
          String path = pathBuilder.toString().substring(2);
          newDir =
                  Directory.getDirectoryFromPath
                          (path, FileSystem.currentDirectory,
                                  FileSystem.root);

          if(newDir != null) // means its a directory
          {
            if(newDir.getName().equals(expression[1]))
            {
              flag = 1;
              sb.append(newDir.getPath() + "\n");
            }
          }
        }
      }
      if(flag != 1)
      {
        if(arguments[end + 1].equals("d"))
        {
          if(dir.getName() == "")
          {
            sb.append("The directory " + expression[1] +
                    " does not exist within " + dir.getName() +
                    "/" + "\n");
          }
          else{
            sb.append("The directory " + expression[1] +
                    " does not exist within " + dir.getName() +
                    "\n");
          }
        }
        else if(arguments[end + 1].equals("f"))
        {
          if(dir.getName().equals(""))
          {
            sb.append("The file " + expression[1] +
                    " does not exist within " + dir.getName() +
                    "/" + "\n");
          }
          else{
            sb.append("The file " + expression[1] +
                    " does not exist within " + dir.getName() +
                    "\n");
          }
        }
      }
    }
    scanner.close();

    if(isAngle) {
      return Redirection.redirect(arguments, sb.toString());
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
    String content = "search path ... -type [f|d] -name expression\n" +
            "Search for the specific files/directories based " +
            "on the name and path " +
            "given.\n";
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
