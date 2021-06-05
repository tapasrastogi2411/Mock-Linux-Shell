package commands;

import java.net.URL;
import java.net.URLConnection;
import java.io.*;

public class Curl extends Commands{

  /**
   *Retrieve the File at that URL and add it to the current working directory.
   *
   * @param arguments the list of strings, each of which are arguments
   *                  given by the user
   */
  @Override
  public String executeCommand(String[] arguments)
  {
    URL url;
    try{
      //get URL content
      url = new URL(arguments[1]);
      URLConnection connection = url.openConnection();

      // open the stream and put it into BufferedReader
      BufferedReader br = new BufferedReader(
              new InputStreamReader(connection.getInputStream()));

      //extracting the last part of the URL so that we get the item name
      String itemName = arguments[1].
              substring(arguments[1].lastIndexOf("/") + 1);

      // As we cannot have "." in file names, we need to strip that off
      itemName = itemName.replaceAll("\\.", "");

      // check if file already exists with same name

      if(!(File.exists(FileSystem.currentDirectory.getPath() + "/"
                      + itemName, FileSystem.currentDirectory,
              FileSystem.root)))
      {
        File file = new File(itemName, FileSystem.currentDirectory);

        StringBuilder sb = new StringBuilder();
        String inputLine;
        while ((inputLine = br.readLine()) != null) {
          sb.append(inputLine);
          sb.append("\n");
        }
        file.setContents(sb.toString());
        br.close();
        return "";
      }
      else
      {
        return "File already exists!";
      }

    } catch (IOException e) {
      return "curl: Invalid URL";
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
  public String getDocumentation(String[] arguments, boolean isAngle)
  {
    String content = "curl URL\nRetrieves the file at that URL and " +
            "adds it to the current working directory.\n";
    if(!isAngle)
    {
      return content;
    }
    else
    {
      return Redirection.redirect(arguments,content);
    }

  }
}
