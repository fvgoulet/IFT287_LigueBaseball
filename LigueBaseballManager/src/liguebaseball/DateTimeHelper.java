package liguebaseball;

import java.text.*;
import java.util.Date;

/**
 * Class Helper for Date and Time objects
 * @author fvgou_000
 */
public class DateTimeHelper
{

    final static String DATE_FORMAT = "yyyy-MM-dd";
    final static String TIME_FORMAT = "HH:mm";
    private static SimpleDateFormat dateFormat;
    private static SimpleDateFormat timeFormat;

    static
    {
        dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setLenient(false);
        timeFormat = new SimpleDateFormat(TIME_FORMAT);
        timeFormat.setLenient(false);
    }

    /**
     * Convert a String to Date
     * @param dateString The Date String to convert from
     * @return The temp value represented by Date
     * @throws ParseException timeString Couldn't be parsed
     */
    public static java.sql.Date convertirDate(String dateString) throws ParseException
    {
        return convertFromDateToSQLDate(dateFormat.parse(dateString));
    }

    /**
     * Convert a String to Time
     * @param timeString The Time String to convert from
     * @return The temp value represented by Time
     * @throws ParseException timeString Couldn't be parsed
     */
    public static java.sql.Time convertirTime(String timeString) throws ParseException
    {
        return new java.sql.Time(timeFormat.parse(timeString).getTime());
    }

    /**
     * Convert a Date to String
     * @param date The Date to convert from
     * @return A string representation of a Date
     */
    public static String toString(Date date)
    {
        return dateFormat.format(date);
    }

    /**
     * Convert a Time to String
     * @param time The Time to convert from
     * @return A string representation of a Time
     */
    public static String toString(java.sql.Time time)
    {
        return timeFormat.format(time);
    }

    /**
     * Gets the Current Date
     * @return The current Date
     */
    public java.util.Date getCurrentDate()
    {
        return new java.util.Date();
    }
    
    /**
     * Gets the Current Time
     * @return The current Time
     */
    public java.sql.Time getCurrentTime()
    {
        return new java.sql.Time(getCurrentDate().getTime());
    }

    /**
     * Gets the Current SQL Date
     * @return The current SQL Date
     */
    public java.sql.Date getCurrentSQLDate()
    {
        java.util.Date now = new java.util.Date();
        return new java.sql.Date(now.getTime());
    }

    /**
     * SQL Date to Date converter
     * @param date the Date to convert to SQL Date
     * @return A SQL Date
     */
    public static java.sql.Date convertFromDateToSQLDate(java.util.Date date)
    {
        return new java.sql.Date(date.getTime());
    }

    /**
     * Date SQL Date converter
     * @param date the SQL Date to convert to Date
     * @return A Date
     */
    public static java.util.Date convertFromSQLDateToDate(java.sql.Date date)
    {
        return (java.util.Date) date;
    }

    /**
     * Verify if the string date could be parsed as a Date
     * @param date The date String to parse
     * @return True if it could be parsed
     */
    public static boolean isDateValid(String date)
    {
        try
        {
            dateFormat.parse(date);
            return true;
        }
        catch (ParseException ex)
        {
            return false;
        }
    }
    
    /**
     * Verify if the string date could be parsed as a Time
     * @param time The time String to parse
     * @return True if it could be parsed
     */
    public static boolean isTimeValid(String time)
    {
        try
        {
            timeFormat.parse(time);
            return true;
        }
        catch (ParseException ex)
        {
            return false;
        }
    }
    
    /**
     * Gets a Standard Date String
     * @return A standard Date String
     */
    public static String getDateTimeString()
    {
        Date date = new java.util.Date();
        String dateTimeString = Integer.toString(date.getYear());
        dateTimeString += Integer.toString(date.getMonth());
        dateTimeString += Integer.toString(date.getDay());
        dateTimeString += "T";
        dateTimeString += Integer.toString(date.getHours());
        dateTimeString += Integer.toString(date.getMinutes());
        dateTimeString += Integer.toString(date.getSeconds());
        return dateTimeString;
    }
}
