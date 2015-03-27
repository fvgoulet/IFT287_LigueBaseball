package liguebaseball;

import java.text.*;
import java.util.Date;

/**
 * Permet de valider le format d'une date en YYYY-MM-DD et de la convertir en un
 * objet Date.
 *
 * <pre>
 *
 *  Marc Frappier - 83 427 378
 *  Université de Sherbrooke
 *  version 2.0 - 13 novembre 2004
 *  ift287 - exploitation de bases de données
 *
 *
 * </pre>
 */
public class DateTimeHelper 
{
    final static String DATE_FORMAT = "yyyy-MM-dd";
    private static SimpleDateFormat dateFormat;
    private static SimpleDateFormat timeFormat;

    static 
    {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        timeFormat = new SimpleDateFormat("HH:mm:ss");
    }

    /**
     * Convertit une String du format YYYY-MM-DD en un objet de la classe Date.
     */
    public java.sql.Date convertirDate(String dateString) throws ParseException 
    {
        return convertFromDateToSQLDate(dateFormat.parse(dateString));
    }
    
    public java.sql.Time convertirTime(String timeString) throws ParseException
    {
        return new java.sql.Time(timeFormat.parse(timeString).getTime());
    }

    public static String toString(Date date) 
    {
        return dateFormat.format(date);
    }
    
    public static String toString(java.sql.Time time) 
    {
        return timeFormat.format(time);
    }
    
    public java.util.Date getCurrentDate()
    {
        return new java.util.Date();
    }
    
    public java.sql.Date getCurrentSQLDate()
    {
        java.util.Date now = new java.util.Date();
        return new java.sql.Date(now.getTime());
    }
    
    public java.sql.Date convertFromDateToSQLDate(java.util.Date date)
    {
        return new java.sql.Date(date.getTime());
    }
    
    public java.util.Date convertFromSQLDateToDate(java.sql.Date date)
    {
        return (java.util.Date)date;
    }
    
    public static boolean isDateValid(String date) 
{
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
}
    
    
}
