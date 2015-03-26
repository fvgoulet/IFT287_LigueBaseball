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
public class FormatDate 
{

    private static SimpleDateFormat formatAMJ;

    static 
    {
        formatAMJ = new SimpleDateFormat("yyyy-MM-dd");
        formatAMJ.setLenient(false);
    }

    public static void main(String[] args) throws ParseException 
    {
        for (int i = 0; i < args.length; i++) 
        {
            System.out.println(convertirDate(args[i]));
        }
    }

    /**
     * Convertit une String du format YYYY-MM-DD en un objet de la classe Date.
     */
    public static Date convertirDate(String dateString) throws ParseException 
    {
        return formatAMJ.parse(dateString);
    }

    public static String toString(Date date) 
    {
        return formatAMJ.format(date);
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
    
    public java.sql.Date convertFromDate(java.util.Date date)
    {
        return new java.sql.Date(date.getTime());
    }
    
    public java.util.Date convertFromDate(java.sql.Date date)
    {
        return (java.util.Date)date;
    }
}
