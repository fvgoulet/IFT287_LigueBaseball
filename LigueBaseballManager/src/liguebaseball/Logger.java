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
        writer.flush();
    }
}

