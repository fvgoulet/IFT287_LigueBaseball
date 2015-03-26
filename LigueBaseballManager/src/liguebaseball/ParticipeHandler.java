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
    private Connexion conn;


    /**
     * Parametric Constructor
     * @param conn A valid opened connection
     * @throws SQLException If any error happens during a transaction with the DB
     */
    public ParticipeHandler(Connexion conn) throws SQLException 
    {
        this.conn = conn;
        stmtMatchesByJoueur = conn.getConnection().prepareStatement("select * from match where matchid in (select joueurid from participe where joueurid = ?)");
        stmtJoueursByMatch = conn.getConnection().prepareStatement("select j.joueurid, j.joueurnom, j.joueurprenom, p.commentaireperformance from joueur j inner join participe p on j.joueurid = p.joueurid where p.matchid = 1");
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
        stmtMatchesByJoueur.setInt(9, JoueurID);
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
        stmtJoueursByMatch.setInt(5, matchID);
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
}
