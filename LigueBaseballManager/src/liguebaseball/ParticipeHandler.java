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
public class ParticipeHandler 
{
    private PreparedStatement stmtMatchesByJoueur;
    private PreparedStatement stmtJoueursByMatch;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtDelete;
    private PreparedStatement stmtDelete2;


    /**
     * Parametric Constructor
     * @param conn A valid opened connection
     * @throws SQLException If any error happens during a transaction with the DB
     */
    public ParticipeHandler(Connexion conn) throws SQLException 
    {
        stmtMatchesByJoueur = conn.getConnection().prepareStatement("select * from match where matchid in (select joueurid from participe where joueurid = ?)");
        stmtJoueursByMatch = conn.getConnection().prepareStatement("select j.joueurid, j.joueurnom, j.joueurprenom, p.commentaireperformance from joueur j inner join participe p on j.joueurid = p.joueurid where p.matchid = 1");
        stmtInsert = conn.getConnection().prepareStatement("insert into participe (joueurid, matchid, commentaireperformance) values (?,?,?)");
        stmtDelete = conn.getConnection().prepareStatement("delete from participe where joueurid = ?");
        stmtDelete2 = conn.getConnection().prepareStatement("delete from faitpartie where matchid = ?");
    }

    /**
     * Get all Matches this Joueur JoueurID has assisted
     * @param JoueurID The Joueur ID to search for
     * @return All Matches this Joueur has assisted
     * @throws SQLException If there is any error with the connection to the DB
     */
    public ArrayList<Equipe> getEquipesByJoueur(int JoueurID) throws SQLException 
    {
        ArrayList<Equipe> equipes = new ArrayList();
        stmtMatchesByJoueur.setInt(1, JoueurID);
        ResultSet result = stmtMatchesByJoueur.executeQuery();
        while(result.next())
        {
            Equipe temp = new Equipe();
            temp.id = result.getInt(1);
            temp.idTerrain = result.getInt(2);
            temp.nom = result.getString(3);
            equipes.add(temp);
        }
        result.close();
        return equipes;
    } 
    
    /**
     * Get all JoueurParticipe that were in this Match Match ID
     * @param matchID The Match ID to search for
     * @return All JoueurParticipe played in given Match ID
     * @throws SQLException If there is any error with the connection to the DB
     */
    public ArrayList<JoueurParticipe> getJoueursByEquipe(int matchID) throws SQLException 
    {
        ArrayList<JoueurParticipe> joueurs = new ArrayList();
        stmtJoueursByMatch.setInt(1, matchID);
        ResultSet result = stmtJoueursByMatch.executeQuery();
        while (result.next()) 
        {
            JoueurParticipe temp = new JoueurParticipe();
            temp.id = result.getInt(1);
            temp.nom = result.getString(2);
            temp.prenom = result.getString(3);
            joueurs.add(temp);
        }
        result.close();
        return joueurs;
    }
    
     /**
     * Insert the defined Participe to the DB
     * @param joueurID A Joueur ID to insert
     * @param equipeID A Match ID to insert
     * @param commentairePerformance A performance commentary
     * @throws SQLException If there is any error with the connection to the DB
     */
    public void inserer(int joueurID, int equipeID, String commentairePerformance) throws SQLException 
    {
        stmtInsert.setInt(1, joueurID);
        stmtInsert.setInt(2, equipeID);
        stmtInsert.setString(3, commentairePerformance);
        stmtInsert.executeUpdate();
    }

    /**
     * Remove the Participe represented by the given Joueur ID
     * @param joueurID The Joueur ID to delete
     * @return The number of Participe removed
     * @throws SQLException If there is any error with the connection to the DB 
     */
    public int supprimer(int joueurID) throws SQLException 
    {     
        stmtDelete.setInt(1, joueurID);
        return stmtDelete.executeUpdate();
    }
    
    /**
     * Remove the Participe represented by the given Match ID
     * @param matchID The Match ID to delete
     * @return The number of Participe removed
     * @throws SQLException If there is any error with the connection to the DB 
     */
    public int supprimer2(int matchID) throws SQLException 
    {     
        stmtDelete2.setInt(1, matchID);
        return stmtDelete2.executeUpdate();
    }
}
