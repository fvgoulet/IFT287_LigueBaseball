package liguebaseball;

import java.sql.*;

/**
 * Gestionnaire d'une connexion avec une BD relationnelle via JDBC.
 *
 * <pre>
 * Marc Frappier - 83 427 378
 * Universit� de Sherbrooke
 * version 2.0 - 13 novembre 2004
 * ift287 - exploitation de bases de donn�es
 *
 * Ce programme ouvre une connexion avec une BD via JDBC.
 * La methode serveursSupportes() indique les serveurs support�s.
 *
 * Pr�-condition
 *   le driver JDBC appropri� doit �tre accessible.
 *
 * Post-condition
 *   la connexion est ouverte en mode autocommit false et s�rialisable,
 *   (s'il est support� par le serveur).
 * </pre>
 */
public class Connexion
{

    private Connection conn;

    /**
     * Open the connection to a DB
     *
     * @param serveur serveur SQL de la BD
     * @param bd The BD name
     * @param user The user who will connect
     * @param pass the passwork of the user
     * @throws SQLException in case something went wrong with the connection
     */
    public Connexion(String serveur, String bd, String user, String pass) throws SQLException
    {
        Driver d;
        try
        {
            if (serveur.equals("local"))
            {
                d = (Driver) Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
                DriverManager.registerDriver(d);
                conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:" + bd, user, pass);
            }
            if (serveur.equals("sti"))
            {
                d = (Driver) Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
                DriverManager.registerDriver(d);
                conn = DriverManager.getConnection("jdbc:oracle:thin:@io.usherbrooke.ca:1521:" + bd, user, pass);
            }
            else if (serveur.equals("postgres"))
            {
                d = (Driver) Class.forName("org.postgresql.Driver").newInstance();
                DriverManager.registerDriver(d);
                conn = DriverManager.getConnection("jdbc:postgresql:" + bd, user, pass);
            }

            // mettre en mode de commit manuel
            conn.setAutoCommit(false);

            // mettre en mode serialisable si possible
            // (plus haut niveau d'integrite l'acces concurrent aux donnees)
            DatabaseMetaData dbmd = conn.getMetaData();
            if (dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE))
            {
                conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                System.out.println("Ouverture de la connexion en mode s�rialisable :\n Estampille " + System.currentTimeMillis() + " " + conn);
            }
            else
            {
                System.out.println("Ouverture de la connexion en mode read committed (default) :\n Heure " + System.currentTimeMillis() + " " + conn);
            }
        }// try
        catch (SQLException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
            throw new SQLException("JDBC Driver non instanci�");
        }
    }

    /**
     * Close the connection
     *
     * @throws SQLException in case something went wrong with the connection
     */
    public void fermer() throws SQLException
    {
        conn.close();
        System.out.println("Connexion fermee" + " " + conn);
    }

    /**
     * Commit current changes
     *
     * @throws SQLException in case something went wrong with the connection
     */
    public void commit() throws SQLException
    {
        conn.commit();
    }

    /**
     * Rollback changes
     *
     * @throws SQLException in case something went wrong with the connection
     */
    public void rollback() throws SQLException
    {
        conn.rollback();
    }

    /**
     * Retourne la Connection jdbc
     *
     * @return The connexion to JDBC
     */
    public Connection getConnection()
    {
        return conn;
    }

    /**
     * Retourne la liste des serveurs supportes par ce gestionnaire de
     * connexions
     *
     * @return A String representing the supported servers
     */
    public static String serveursSupportes()
    {
        return "local : Oracle installe localement 127.0.0.1\r\n"
                + "sti   : Oracle installe au Service des technologies de l'information\r\n"
                + "postgres : Postgres installe localement\r\n"
                + "access : Microsoft Access, installe localement et inscrit dans ODBC";
    }
}// Classe Connexion
