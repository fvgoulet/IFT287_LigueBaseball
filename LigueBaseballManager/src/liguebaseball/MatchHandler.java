/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

import java.sql.*;

/**
 * Permet d'effectuer les accès à la table livre.
 * <pre>
 *
 * Marc Frappier - 83 427 378
 * Université de Sherbrooke
 * version 2.0 - 13 novembre 2004
 * ift287 - exploitation de bases de données
 *
 * Cette classe gère tous les accès à la table Match.
 *
 *</pre>
 */
public class MatchHandler {

    private PreparedStatement stmtExiste;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtUpdate;
    private PreparedStatement stmtDelete;
     private PreparedStatement stmtLastID;
    private Connexion cx;


    public MatchHandler(Connexion cx) throws SQLException {

        this.cx = cx;
        stmtLastID = cx.getConnection().prepareStatement("select max(matchid) from match");
        stmtExiste = cx.getConnection().prepareStatement("select matchid, equipelocal, equipevisiteur, terrainid, matchdate, matchheure, pointslocal, pointsvisiteur from match where matchid = ?");
        stmtInsert = cx.getConnection().prepareStatement("insert into match (matchid, equipelocal, equipevisiteur, terrainid, matchdate, matchheure, pointslocal, pointsvisiteur) "
                + "values (?,?,?,?,?,?,?,?)");
        stmtUpdate = cx.getConnection().prepareStatement("update match set joueurnom = ?, joueurprenom = ? "
                + "where joueurid = ?");
        stmtDelete = cx.getConnection().prepareStatement("delete from match where matchid = ?");
    }


    public Connexion getConnexion() {

        return cx;
    }


    public boolean existe(int matchid) throws SQLException {

        stmtExiste.setInt(1, matchid);
        ResultSet rset = stmtExiste.executeQuery();
        boolean matchExiste = rset.next();
        rset.close();
        return matchExiste;
    }

    public Joueur getmatch(int joueurid) throws SQLException 
    {
        stmtExiste.setInt(1, joueurid);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next()) 
        {
            Joueur joueur = new Joueur();
            joueur.joueurid = joueurid;
            joueur.joueurnom = rset.getString(2);
            joueur.joueurprenom = rset.getString(3);
            rset.close();
            return joueur;
        } 
        else 
        {
            return null;
        }
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
    
    public void inserer(int matchid, int equipelocal, int equipevisiteur, int terrainid, String matchdate, String matchheure, int pointslocal, int pointsvisiteur) throws SQLException 
    {
        stmtInsert.setInt(1, matchid);
        stmtInsert.setInt(2, equipelocal);
        stmtInsert.setInt(3, equipevisiteur);
        stmtInsert.setInt(4, terrainid);
        stmtInsert.setString(5, matchdate);
        stmtInsert.setString(6, matchheure);
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
