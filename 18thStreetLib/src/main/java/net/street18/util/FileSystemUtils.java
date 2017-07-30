//Copyright ©2005 ABN AMRO Services Company, Inc.  All rights reserved.
package net.street18.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import net.street18.exception.l8thStreetException;

/**
 * Set of static methods for basic filesystem operations.
 * 
 * Copyright ©2005 ABN AMRO Services Company, Inc.  All rights reserved.
 *
 * author Greg Sandell
 */
public final class FileSystemUtils {
    private static final Logger LOGGER = Logger.getLogger(FileSystemUtils.class);

    /**
     * Private construct to prevent instantion of this utility class.
     *
     * author Greg Sandell
     */
    private FileSystemUtils()
    {
    }
    /**
     * Creates a file and returns an OutputStreamWriter object.
     * @param fileLocation The full path to the file.
     * @param fileName The file name.
     * @return OutputStreamWriter object for the file.
     */
    public static OutputStreamWriter createFile(final String fileLocation, final String fileName) {
        OutputStreamWriter os = null;
        try {
            os = new OutputStreamWriter(new FileOutputStream(fileLocation + fileName));
        }
          catch (IOException e)    {
            LOGGER.error( "createFile(String):  There was an error creating the file ["
                    + fileLocation + fileName + "]\n" + e.getMessage());
          }
         return os;
    }

    /**
     * Add String data to an open file where input is a String object.
     * @param os OutputStreamWriter object of an open file.
     * @param data The String data to add.
     * 
     * author Greg Sandell
     */
    public static void addToFile(OutputStreamWriter os, String data)  {
        try {
            os.write(data.toString());
        }
          catch (IOException e)   {
            LOGGER.error("addToFile(OutputStreamWriter, String):  There was an error adding the following data ["
                    + data.toString()  + "]\n" + e.getMessage());
          }
    }

    /**
     * Add String data to an open file, where input is a StringBuffer object.
     * @param os OutputStreamWriter object for an open file.
     * @param data StringBuffer of data to add.
     * 
     * author Greg Sandell
     */
    public static void addToFile(final OutputStreamWriter os, final StringBuffer data){
        addToFile(os, data.toString());
    }

    /**
     * Close an open file.
     * @param os OutputStreamWriter object for an open file.
     * 
     * author Greg Sandell
     */
    public static void closeFile(final OutputStreamWriter os) {
        try {
            os.flush();
            os.close();
        }
          catch (IOException e) {
            LOGGER.error("closeFile(OutputStreamWriter):  There was an error closing the file\n"
                    + e.getMessage());
          }
    }
    /**
     * Read all the lines from a file into a single String.
     * @param fileObj A File object for an open file.
     * @return The contents of the file as a String.
     * 
     * author Greg Sandell
     */
    public static String getStringFromFileObj(final File fileObj) {
        StringBuffer sb = new StringBuffer();
        sb.append("");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileObj));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null)  {
                    break; // end of file
                }
                sb.append(line);
            }
        }
          catch (Exception e) {
            LOGGER.error("Error reading file [" + fileObj.getAbsolutePath() + "]");
            e.printStackTrace();
            return ("");
          }
        return (sb.toString());
    }

    /**
     * Takes a path to a file on a website, opens the file and returns the contents of the file as a 
     * single String.
     * The path to the file is broken into two parts:  the fully-qualified path to the location of
     * the web root on the local machine, and relative path to the file from there.
     *
     * @param webappsRealPath Fully-qualified path to the location of the web root on the local machine
     * @param filePath        Relative path to the file from the web root
     * @return A String containing the entire file's contents.
     * 
     * author Greg Sandell
     */
    public static String getStringFromFile(String webappsRealPath, final String filePath) throws l8thStreetException {
      if (webappsRealPath == null)  {
        throw new l8thStreetException("Null argument provided for webappsRealPath");
      }
      String fullPath = webappsRealPath + filePath;
      return (getStringFromFile(fullPath));
  }
  public static String getStringFromFile(final String fullFilePath) throws l8thStreetException {
      LOGGER.info("Reading file [" + fullFilePath + "] from disk");
      File fileObj = new File(fullFilePath);
      if (!isReadable(fileObj))  {
        throw new l8thStreetException("The file [" + fullFilePath + "] is not readable.");
      }
      return (getStringFromFileObj(fileObj));
  }

    /**
     * Checks to see if a file Object corresponds to a readable file.
     *
     * @param fileObj A File object for a file (not opened).
     * @return Boolean indicating if file is readable.
     * 
     * author Greg Sandell
     */
    public static boolean isReadable(final File fileObj) throws l8thStreetException {
        boolean result = false;
        String errString = "File [" + fileObj.getAbsolutePath() + "] is ";
        if (fileObj.exists()) {
            if (fileObj.isFile())  {
                if (fileObj.canRead())  {
                    result = true;
                }
                else {
                    throw new l8thStreetException(errString + "not readable");
                }
            }
            else  {
                if (fileObj.isDirectory()){
                    throw new l8thStreetException(errString + "a directory, not a file");
                }
                else  {
                    throw new l8thStreetException(errString + "not a file");
                }
            }
        }
        else {
            throw new l8thStreetException(errString + "non-existent");
        }
        return (result);
    }

    /**
     * Web-page friendly (i.e. in HTML) diagnosis of a File Object
     *
     * @param fileObj A File object for a file.
     * @return  A String of diagnositic information.
     * 
     * author Greg Sandell
     */
    public static String diagnose(final File fileObj)
    {
        String s = "";
        // naming
        s += "NAME is  " + fileObj.getName() + "<br/>";
        s += "PATH is  " + fileObj.getPath() + "<br/>";
        s += "FULL is  " + fileObj.getAbsolutePath() + "<br/>";
        s += "..   is  " + fileObj.getParent() + "<br/>";
        s += "exists?  " + fileObj.exists() + "<br/>";
        s += "read?    " + fileObj.canRead() + "<br/>";
        s += "write?   " + fileObj.canWrite() + "<br/>";
        s += "file?    " + fileObj.isFile() + "<br/>";
        s += "dir?     " + fileObj.isDirectory() + "<br/>";
        s += "absdir?  " + fileObj.isAbsolute() + "<br/>";
        s += "modified " + new Date(fileObj.lastModified()) + "<br/>";
        s += "size     " + fileObj.length() + "<br/>";
        if (fileObj.isDirectory())
        {
            s += fileObj.getName() + " is a directory<br/>";
        }
        else if (fileObj.isFile())
        {
            s += fileObj.getName() + " is a file<br/>";
        }
        else
        {
            s += fileObj.getName() + " is neither a directory nor a fileObj.<br/>";
        }
        return (s);
    }

    /**
     * Make a FileList object for the given directory and extension restrictions.
     * @param directoryName The directory to search for files.
     * @param extensionFilterList A List of extensions to look for.
     * @return List of files.
     * 
     * author Greg Sandell
     */     /*
    public static ArrayList makeFileList(
        final String directoryName,
        final ArrayList extensionFilterList)
    {
        FileList fileList = new FileList(directoryName, extensionFilterList);
        return (fileList);
    }
             */
}
