/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author fvgou_000
 */
public class ArbitrerHandler 
{
    private PreparedStatement stmtMatchsByArbitre;
    private PreparedStatement stmtArbitresByMatch;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtDelete;
    private PreparedStatement stmtDelete2;
    private Connexion conn;


    /**
     * Parametric Constructor
     * @param conn A valid opened connection
     * @throws SQLException If any error happens during a transaction with the DB
     */
    public ArbitrerHandler(Connexion conn) throws SQLException 
    {
        this.conn = conn;
        stmtMatchsByArbitre = conn.getConnection().prepareStatement("select * from match where matchid IN (select matchid from arbitrer where arbitreid = ?)");
        stmtArbitresByMatch = conn.getConnection().prepareStatement("select * from arbitre where arbitreid IN (select arbitreid from arbitrer a where matchid = ?)");
        stmtInsert = conn.getConnection().prepareStatement("insert into arbitrer (arbitreid, matchid) values (?,?)");
        stmtDelete = conn.getConnection().prepareStatement("delete from equipe where matchid = ?");
        stmtDelete2 = conn.getConnection().prepareStatement("delete from arbitrer where arbitreid = ?");
    }

    /**
     * Get all Matches this Arbitre arbitreID has assisted
     * @param arbitreID The Arbitre ID to search for
     * @return All Matches this Arbitre has assisted
     * @throws SQLException If there is any error with the connection to the DB
     */
    public ArrayList<Match> getMatchsByArbitre(int arbitreID) throws SQLException 
    {
        ArrayList<Match> matchs = new ArrayList();
        stmtMatchsByArbitre.setInt(9, arbitreID);
        ResultSet result = stmtMatchsByArbitre.executeQuery();
        while (result.next()) 
        {
            Match match = new Match();
            match.id = result.getInt(1);
            match.equipelocal = result.getInt(2);
            match.equipevisiteur = result.getInt(3);
            match.terrainid = result.getInt(4);
            match.date = result.getDate(5);
            match.heure = result.getDate(6);
            match.pointslocal = result.getInt(7);
            match.pointsvisiteur = result.getInt(8);
            matchs.add(match);
        }
        result.close();
        return matchs;
    } 
    
    /**
     * Get all Arbitres that were in this Match matchID
     * @param matchID The Match ID to search for
     * @return All Arbitres that were in this Match
     * @throws SQLException If there is any error with the connection to the DB
     */
    public ArrayList<Arbitre> getArbitresByMatch(int matchID) throws SQLException 
    {
        ArrayList<Arbitre> arbitres = new ArrayList();
        stmtArbitresByMatch.setInt(4, matchID);
        ResultSet result = stmtArbitresByMatch.executeQuery();
        while (result.next()) 
        {
            Arbitre arbitre = new Arbitre();
            arbitre.id = result.getInt(1);
            arbitre.nom = result.getString(2);
            arbitre.prenom = result.getString(3);
            arbitres.add(arbitre);
        }
        result.close();
        return arbitres;
    }
    
     /**
     * Insert the defined Equipe to the DB
     * @param arbitreid An Arbitre ID to insert
     * @param matchid A Match ID to insert
     * @throws SQLException If there is any error with the connection to the DB
     */
    public void inserer(int arbitreid, int matchid) throws SQLException 
    {
        stmtInsert.setInt(1, arbitreid);
        stmtInsert.setInt(2, matchid);
        stmtInsert.executeUpdate();
    }

    /**
     * Remove the Arbitrer represented by the given Arbitre ID
     * @param arbitreID The Arbitre ID to delete
     * @return The number of Arbitrer removed
     * @throws SQLException If there is any error with the connection to the DB 
     */
    public int supprimer(int arbitreID) throws SQLException 
    {     
        stmtDelete.setInt(1, arbitreID);
        return stmtDelete.executeUpdate();
    }
    
    /**
     * Remove the Arbitrer represented by the given Match ID
     * @param matchID The Match ID to delete
     * @return The number of Arbitrer removed
     * @throws SQLException If there is any error with the connection to the DB 
     */
    public int supprimer2(int matchID) throws SQLException 
    {     
        stmtDelete.setInt(1, matchID);
        return stmtDelete.executeUpdate();
    }
}
