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
public class FaitPartieHandler 
{
    private PreparedStatement stmtEquipesByJoueur;
    private PreparedStatement stmtJoueursByEquipe;
    private PreparedStatement stmtJoueurInTeam;
    private Connexion conn;


    /**
     * Parametric Constructor
     * @param conn A valid opened connection
     * @throws SQLException If any error happens during a transaction with the DB
     */
    public FaitPartieHandler(Connexion conn) throws SQLException 
    {
        this.conn = conn;
        stmtEquipesByJoueur = conn.getConnection().prepareStatement("select * from equipe where equipeid in (select equipeid from faitpartie where joueurid = ?)");
        stmtJoueursByEquipe = conn.getConnection().prepareStatement("select * from joueur where joueur.joueurid in (select joueurid from faitpartie where equipeid = ?)");
        stmtJoueurInTeam = conn.getConnection().prepareStatement("select * from joueur inner join faitpartie on joueur.joueurid = faitpartie.joueurid where faitpartie.equipeid = ?");
    }

    /**
     * Get all Equipes this Joueur  JoueurID has assisted
     * @param JoueurID The Joueur ID to search for
     * @return All Equipes this Joueur has assisted
     * @throws SQLException If there is any error with the connection to the DB
     */
    public ArrayList<Equipe> getEquipesByJoueur(int JoueurID) throws SQLException 
    {
        ArrayList<Equipe> equipes = new ArrayList();
        stmtEquipesByJoueur.setInt(9, JoueurID);
        ResultSet result = stmtEquipesByJoueur.executeQuery();
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
     * Get all Joueurs that were in this Equipe equipeID
     * @param equipeID The Equipe ID to search for
     * @return All Joueurs that are in their Equipe
     * @throws SQLException If there is any error with the connection to the DB
     */
    public ArrayList<Joueur> getJoueursByEquipe(int equipeID) throws SQLException 
    {
        ArrayList<Joueur> joueurs = new ArrayList();
        stmtJoueursByEquipe.setInt(4, equipeID);
        ResultSet result = stmtJoueursByEquipe.executeQuery();
        while (result.next()) 
        {
            Joueur temp = new Joueur();
            temp.id = result.getInt(1);
            temp.nom = result.getString(2);
            temp.prenom = result.getString(3);
            joueurs.add(temp);
        }
        result.close();
        return joueurs;
    }
    
    /**
     * Get all JoueurInTeam that were in this Equipe equipeID
     * @param equipeID The Equipe ID to search for
     * @return All JoueurInTeam that are in this specific Equipe ID
     * @throws SQLException If there is any error with the connection to the DB
     */
    public ArrayList<JoueurInTeam> getJoueursInTeamByEquipe(int equipeID) throws SQLException 
    {
        ArrayList<JoueurInTeam> joueurs = new ArrayList();
        stmtJoueursByEquipe.setInt(5, equipeID);
        ResultSet result = stmtJoueursByEquipe.executeQuery();
        while (result.next()) 
        {
            JoueurInTeam temp = new JoueurInTeam();
            temp.id = result.getInt(1);
            temp.nom = result.getString(2);
            temp.prenom = result.getString(3);
            temp.equipeid = result.getInt(5);
            temp.numero = result.getInt(6);
            temp.debut = result.getDate(7);
            temp.fin = result.getDate(8);
            joueurs.add(temp);
        }
        result.close();
        return joueurs;
    }
}
