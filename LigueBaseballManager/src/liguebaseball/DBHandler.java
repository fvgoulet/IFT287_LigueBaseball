/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author fvgou_000
 */
public class DBHandler 
{
    private Connexion conn;
    private DBHandler handler;
    
    /**
     * Create a postgres DBHandler
     * @param userID As the user connecting to the DB
     * @param password As the password of the user connecting to the DB
     * @param bd As the name of the DB
     * @throws SQLException 
     */
    private DBHandler(String userID, String password, String bd) throws SQLException
    {
            conn = new Connexion("postgres", bd, userID, password);
    }
    
    public conn getInstance()
}
