/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class that handle file workflow
 * @author valf1701
 */
public class Logger 
{   
    private final BufferedWriter writer;
    private static final String newLine = "\r\n";
    
    /**
     * Parametric Constructor
     * @param fileName
     * @throws FileNotFoundException 
     */
    public Logger(String fileName) throws FileNotFoundException, IOException
    {
        writer = new BufferedWriter(new FileWriter(new File(fileName)));
    }
    
    /**
     * Method that gets the Current Directory as a string
     * @return The canonical path to the Current directory
     */
    public static String getCurrentDirectory() throws IOException
    {
        return new java.io.File( "." ).getCanonicalPath();
    }
    
    /**
     * Method that output value to the console and write to the log file
     * @param value What to Log
     */
    public void Log(String value) throws IOException
    {
        System.out.println(value);
        write(value);
    }
    
    /**
     * Method that output value to the console and write to the log file
     * @param value What to Log
     */
    public void LogTimeStamped(String value) throws IOException
    {
        System.out.println(value);
        write(value, getTimeStamp());
    }
    
    /**
     * Method that Write all lines in an ArrayList of String into the Log file
     * @param lines All lines to write to the file as an ArrayList of String
     * @throws FileNotFoundException In case the file
     * @throws IOException In case the file couldn't be written in
     */
    public void write(ArrayList<String> lines) throws FileNotFoundException, IOException
    {
        for(String line : lines)
        {
            writer.write(line);
            writer.newLine();
            writer.flush();
        }
    }
    
    /**
     * Method that Write a String into the Log file
     * @param content The String to write to the file
     * @throws FileNotFoundException In case the file
     * @throws IOException In case the file couldn't be written in
     */
    public void write(String content) throws FileNotFoundException, IOException
    {
        writer.write(content);
        writer.newLine();
        writer.flush();
    }
    
    /**
     * Method that Write all TimeStamped lines in an ArrayList of String into the Log file
     * @param lines All lines to write to the file as an ArrayList of String
     * @param timestamp A timestamp to put before the logging value
     * @throws FileNotFoundException In case the file
     * @throws IOException In case the file couldn't be written in
     */
    public void write(ArrayList<String> lines, String timestamp) throws FileNotFoundException, IOException
    {
        for(String line : lines)
        {
            writer.write(timestamp + "   " + line);
            writer.newLine();
            writer.flush();
        }
    }
    
    /**
     * Method that Write a TimeStamped String into the Log file
     * @param content The String to write to the file
     * @param timestamp A timestamp to put before the logging value
     * @throws FileNotFoundException In case the file
     * @throws IOException In case the file couldn't be written in
     */
    public void write(String content, String timestamp) throws FileNotFoundException, IOException
    {
        writer.write(timestamp + "   " + content);
        writer.newLine();
        writer.flush();
    }
    
    /**
     * Get a String representation of current time in a TimeStamp
     * @return A timestamp as a String
     */
    private static String getTimeStamp()
    {
        Date date = new java.util.Date();
        String dateTimeString = "[" + Integer.toString(date.getYear()) + "-";
        dateTimeString += Integer.toString(date.getMonth()) + "-";
        dateTimeString += Integer.toString(date.getDay()) + " ";
        dateTimeString += Integer.toString(date.getHours()) + ":";
        dateTimeString += Integer.toString(date.getMinutes())+ ":";
        dateTimeString += Integer.toString(date.getSeconds()) + "]";
        return dateTimeString;
    }
}

