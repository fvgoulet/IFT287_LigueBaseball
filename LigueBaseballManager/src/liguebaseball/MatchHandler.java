/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class dedicated to handle DB requests for table Match
 * @author fvgou_000
 */
public class MatchHandler
{

    private PreparedStatement stmtExiste;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtUpdate;
    private PreparedStatement stmtDelete;
    private PreparedStatement stmtLastID;
    private PreparedStatement stmtGetId;
    private PreparedStatement stmtMatchesByEquipe;
    private PreparedStatement stmtMatchesByDate;

    /**
     * Parametric Constructor
     * @param conn A valid opened connection
     * @throws SQLException If any error happens during a transaction with the DB
     */
    public MatchHandler(Connexion conn) throws SQLException
    {
        stmtLastID = conn.getConnection().prepareStatement("select max(matchid) from match");
        stmtExiste = conn.getConnection().prepareStatement("select matchid, equipelocal, equipevisiteur, terrainid, matchdate, matchheure, pointslocal, pointsvisiteur from match where matchid = ?");
        stmtGetId = conn.getConnection().prepareStatement("select matchid from match where equipelocal = ? AND equipevisiteur = ? AND matchdate = ? AND matchheure =  ?");
        stmtInsert = conn.getConnection().prepareStatement("insert into match (matchid, equipelocal, equipevisiteur, terrainid, matchdate, matchheure, pointslocal, pointsvisiteur) values (?,?,?,?,?,?,?,?)");
        stmtUpdate = conn.getConnection().prepareStatement("update match set joueurnom = ?, joueurprenom = ? where joueurid = ?");
        stmtDelete = conn.getConnection().prepareStatement("delete from match where matchid = ?");
        stmtMatchesByEquipe = conn.getConnection().prepareStatement("select * from match where equipelocal = ? or equipevisiteur = ?");
        stmtMatchesByDate = conn.getConnection().prepareStatement("select * from match where matchdate >= ? order by matchdate");
    }

    /** Check is a certain match exists
     * @param matchid A valid Match ID
     * @return If the match has been found
     * @throws SQLException If there is any error with the connection to the DB
     */
    public boolean existe(int matchid) throws SQLException
    {
        stmtExiste.setInt(1, matchid);
        ResultSet result = stmtExiste.executeQuery();
        boolean matchExiste = result.next();
        result.close();
        return matchExiste;
    }

    /**
     * Check is a certain match exists
     * @param equipelocal A valid Equipe ID
     * @param equipevisiteur A valid Equipe ID
     * @param date A valid Date
     * @param heure A valid Time
     * @return If the match has been found
     * @throws SQLException If there is any error with the connection to the DB
     */
    public boolean existeMatch(int equipelocal, int equipevisiteur, Date date, Time heure) throws SQLException
    {
        stmtGetId.setInt(1, equipelocal);
        stmtGetId.setInt(2, equipevisiteur);
        stmtGetId.setDate(3, date);
        stmtGetId.setTime(4, heure);
        ResultSet result = stmtGetId.executeQuery();
        boolean matchExiste = result.next();
        result.close();
        return matchExiste;
    }

    /**
     * Gets the ID of a certain match
     * @param equipelocal A valid Equipe ID
     * @param equipevisiteur A valid Equipe ID
     * @param matchdate A valid Date
     * @param matchheure A valid Time
     * @return The ID of this match
     * @throws SQLException If there is any error with the connection to the DB
     */
    public int getId(int equipelocal, int equipevisiteur, Date matchdate, Time matchheure) throws SQLException
    {
        stmtGetId.setInt(1, equipelocal);
        stmtGetId.setInt(2, equipevisiteur);
        stmtGetId.setDate(3, matchdate);
        stmtGetId.setTime(4, matchheure);
        int id = -1;
        ResultSet result = stmtGetId.executeQuery();
        if(result.next());
        {
            id = result.getInt((1));
        }
        result.close();
        return id;
    }

    /**
     * Return the match represented by a valid Match ID
     * @param matchID A valid Match ID
     * @return The Match Represented by the given Match ID
     * @throws SQLException If there is any error with the connection to the DB
     */
    public Match getmatch(int matchID) throws SQLException
    {
        stmtExiste.setInt(1, matchID);
        ResultSet result = stmtExiste.executeQuery();
        Match temp = null;
        if (result.next())
        {
            temp = new Match();
            temp.id = matchID;
            temp.equipelocal = result.getInt(2);
            temp.equipevisiteur = result.getInt(3);
            temp.terrainid = result.getInt(4);
            temp.date = result.getDate(5);
            temp.heure = result.getTime(6);
            temp.pointslocal = result.getInt(7);
            temp.pointsvisiteur = result.getInt(8);
            result.close();
        }
        return temp;
    }
    
    /**
     * Get all Matches where a given Equipe ID can be found
     * @param equipeID The Equipe ID to search for
     * @return All Matches where the given Equipe ID can be found
     * @throws SQLException If there is any error with the connection to the DB
     */
    public ArrayList<Match> getMatchesByEquipe(int equipeID) throws SQLException 
    {
        ArrayList<Match> Match = new ArrayList();
        stmtMatchesByEquipe.setInt(1, equipeID);
        stmtMatchesByEquipe.setInt(1, equipeID);
        ResultSet result = stmtMatchesByEquipe.executeQuery();
        while (result.next()) 
        {
            Match temp = new Match();
            temp.id = result.getInt(1);
            temp.equipelocal = result.getInt(2);
            temp.equipevisiteur = result.getInt(3);
            temp.terrainid = result.getInt(4);
            temp.date = result.getDate(5);
            temp.heure = result.getTime(6);
            temp.pointslocal = result.getInt(7);
            temp.pointsvisiteur = result.getInt(8);
            result.close();
            Match.add(temp);
        }
        result.close();
        return Match;
    }
    
    /**
     * Get all Matches after a certain Date
     * @param date The Date to compare to
     * @return All Matches that happens after the given Date
     * @throws SQLException If there is any error with the connection to the DB
     */
    public ArrayList<Match> getMatchesByDate(Date date) throws SQLException 
    {
        ArrayList<Match> Match = new ArrayList();
        stmtMatchesByDate.setDate(1, date);
        ResultSet result = stmtMatchesByDate.executeQuery();
        while (result.next()) 
        {
            Match temp = new Match();
            temp.id = result.getInt(1);
            temp.equipelocal = result.getInt(2);
            temp.equipevisiteur = result.getInt(3);
            temp.terrainid = result.getInt(4);
            temp.date = result.getDate(5);
            temp.heure = result.getTime(6);
            temp.pointslocal = result.getInt(7);
            temp.pointsvisiteur = result.getInt(8);
            result.close();
            Match.add(temp);
        }
        result.close();
        return Match;
    }

    /* Get the maximum value for Match ID
     * @return The maximum value for Match ID
     * @throws SQLException If there is any error with the connection to the DB
     */
    public int getLastID() throws SQLException
    {
        ResultSet result = stmtLastID.executeQuery();
        int maxID = 0;
        if (result.next())
        {
            maxID = result.getInt(1);
        }
        result.close();
        return maxID;
    }

    /**
     * Insert a new Match in the DB
     * @param id A valid Match ID
     * @param equipelocal A valid Equipe ID
     * @param equipevisiteur A valid Equipe ID
     * @param terrainid A valid Terrain ID
     * @param date The date of the Match
     * @param heure The Time of the Match
     * @param pointslocal Scored points made by the local Equipe
     * @param pointsvisiteur Scored points made by the visiteur Equipe
     * @throws SQLException If there is any error with the connection to the DB
     */
    public void inserer(int id, int equipelocal, int equipevisiteur, int terrainid, Date date, Time heure, int pointslocal, int pointsvisiteur) throws SQLException
    {
        stmtInsert.setInt(1, id);
        stmtInsert.setInt(2, equipelocal);
        stmtInsert.setInt(3, equipevisiteur);
        stmtInsert.setInt(4, terrainid);
        stmtInsert.setDate(5, date);
        stmtInsert.setTime(6, heure);
        stmtInsert.setInt(7, pointslocal);
        stmtInsert.setInt(8, pointsvisiteur);
        stmtInsert.executeUpdate();
    }

    /**
     *
     * @param matchid Id of the player to delete
     * @return
     * @throws SQLException
     */
    public int supprimer(int matchid) throws SQLException
    {
        stmtDelete.setInt(1, matchid);
        return stmtDelete.executeUpdate();
    }
}
