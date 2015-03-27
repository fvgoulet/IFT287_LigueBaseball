/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

import java.sql.*;


public class MatchHandler {

    private PreparedStatement stmtExiste;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtUpdate;
    private PreparedStatement stmtDelete;
     private PreparedStatement stmtLastID;
     private PreparedStatement stmtGetId; 


    public MatchHandler(Connexion cx) throws SQLException 
    {
        stmtLastID = cx.getConnection().prepareStatement("select max(matchid) from match");
        stmtExiste = cx.getConnection().prepareStatement("select matchid, equipelocal, equipevisiteur, terrainid, matchdate, matchheure, pointslocal, pointsvisiteur from match where matchid = ?");
        stmtGetId  = cx.getConnection().prepareStatement("select matchid from match where equipelocal = ? AND equipevisiteur = ? AND matchdate = ? AND matchheure =  ?");
        stmtInsert = cx.getConnection().prepareStatement("insert into match (matchid, equipelocal, equipevisiteur, terrainid, matchdate, matchheure, pointslocal, pointsvisiteur) values (?,?,?,?,?,?,?,?)");
        stmtUpdate = cx.getConnection().prepareStatement("update match set joueurnom = ?, joueurprenom = ? where joueurid = ?");
        stmtDelete = cx.getConnection().prepareStatement("delete from match where matchid = ?");
    }

    public boolean existe(int matchid) throws SQLException 
    {
        stmtExiste.setInt(1, matchid);
        ResultSet result = stmtExiste.executeQuery();
        boolean matchExiste = result.next();
        result.close();
        return matchExiste;
    }
   public boolean existeMatch(int equipelocal, int equipevisiteur, Date matchdate, Time matchheure) throws SQLException 
    {
        stmtGetId.setInt(1, equipelocal);
        stmtGetId.setInt(2, equipevisiteur);
        stmtGetId.setDate(3, matchdate);
        stmtGetId.setTime(4, matchheure);
        ResultSet result = stmtGetId.executeQuery();
        boolean matchExiste = result.next();
        result.close();
        return matchExiste;
    }
   
    public int getId(int equipelocal, int equipevisiteur, Date matchdate, Time matchheure) throws SQLException 
    {
        stmtGetId.setInt(1, equipelocal);
        stmtGetId.setInt(2, equipevisiteur);
        stmtGetId.setDate(3, matchdate);
        stmtGetId.setTime(4, matchheure);
        ResultSet result = stmtGetId.executeQuery();
        boolean matchExiste = result.next();
        int id = result.getInt((1));
        result.close();
        return id;
    }

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
            temp.heure = result.getDate(6);
            temp.pointslocal = result.getInt(7);
            temp.pointsvisiteur = result.getInt(8);
            result.close();
        }
        return temp;
    }

  /* Get the maximum value for Match ID
     * @return The maximum value for Match ID
     * @throws SQLException If there is any error with the connection to the DB
     */
    public int getLastID() throws SQLException
    {
        ResultSet result = stmtLastID.executeQuery();
        int maxID = 0;
        if(result.next())
        {
            maxID = result.getInt(1);
        }
        result.close();
        return maxID;
    }
    
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

    public int supprimer(int matchid) throws SQLException 
    {
        stmtDelete.setInt(1, matchid);
        return stmtDelete.executeUpdate();
    }
}
