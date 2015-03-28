/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

import java.sql.*;

/**
 * Access to the table PJoueur
 */
public class JoueurHandler
{

    private PreparedStatement stmtExiste;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtUpdate;
    private PreparedStatement stmtDelete;
    private PreparedStatement stmtExisteNom;
    private PreparedStatement stmtDeleteFromFaitPartie;
    private PreparedStatement stmtDeleteFromParticipe;
    private PreparedStatement stmtLastID;

    /**
     *
     * @param conn Connexion
     * @throws SQLException Return true if it exists, false if it doesn't
     */
    public JoueurHandler(Connexion conn) throws SQLException
    {
        stmtLastID = conn.getConnection().prepareStatement("select max(joueurid) from joueur");
        stmtExiste = conn.getConnection().prepareStatement("select joueurid, joueurnom, joueurprenom from joueur where joueurid = ?");
        stmtInsert = conn.getConnection().prepareStatement("insert into joueur (joueurid, joueurnom, joueurprenom) values (?,?,?)");
        stmtExisteNom = conn.getConnection().prepareStatement("select joueurid, joueurnom, joueurprenom from joueur where joueurnom = ? and joueurprenom = ?");
        stmtUpdate = conn.getConnection().prepareStatement("update joueur set joueurnom = ?, joueurprenom = ? where joueurid = ?");
        stmtDelete = conn.getConnection().prepareStatement("delete from joueur where joueurid = ?");
        stmtDeleteFromFaitPartie = conn.getConnection().prepareStatement("delete from faitpartie where joueurid = ?");
        stmtDeleteFromParticipe = conn.getConnection().prepareStatement("delete from participe where joueurid = ?");

    }

    /**
     *
     * @param joueurid Joueur's ID
     * @return Return true if it exists, false if it doesn't
     * @throws SQLException If any error happens during a transaction with the
     * DB
     */
    public boolean existe(int joueurid) throws SQLException
    {

        stmtExiste.setInt(1, joueurid);
        ResultSet rset = stmtExiste.executeQuery();
        boolean equipeExiste = rset.next();
        rset.close();
        return equipeExiste;
    }

    /**
     *
     * @param nom Joueur's name
     * @param prenom joueur's prenom
     * @return Return true if it exists, false if it doesn't
     * @throws SQLException If any error happens during a transaction with the
     * DB
     */
    public boolean existeNom(String nom, String prenom) throws SQLException
    {

        stmtExisteNom.setString(1, nom);
        stmtExisteNom.setString(2, prenom);
        ResultSet rset = stmtExisteNom.executeQuery();
        boolean equipeExiste = rset.next();
        rset.close();
        return equipeExiste;
    }

    /**
     *
     * @param joueurid a joueur id
     * @return Player corresponding to the ID
     * @throws SQLException
     */
    public Joueur getJoueur(int joueurid) throws SQLException
    {
        stmtExiste.setInt(1, joueurid);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next())
        {
            Joueur joueur = new Joueur();
            joueur.id = joueurid;
            joueur.nom = rset.getString(2);
            joueur.prenom = rset.getString(3);
            rset.close();
            return joueur;
        }
        else
        {
            return null;
        }
    }

    /**
     * Get the maximum value for Joueur ID
     *
     * @param Nom Le nom du joueur
     * @param Prenom Le prenom du joueur
     * @return l'id du joueur
     * @throws SQLException If there is any error with the connection to the DB
     */
    public Joueur getJoueurId(String Nom, String Prenom) throws SQLException
    {
        stmtExisteNom.setString(1, Nom);
        stmtExisteNom.setString(2, Prenom);
        ResultSet rset = stmtExisteNom.executeQuery();
        if (rset.next())
        {
            Joueur joueur = new Joueur();
            joueur.id = rset.getInt(1);
            joueur.nom = Nom;
            joueur.prenom = Prenom;
            rset.close();
            return joueur;
        }
        else
        {
            return null;
        }
    }

    /**
     * Get the maximum value for Joueur ID
     *
     * @return The maximum value for Joueur ID
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
     *
     * @param joueurid l'id du joueur a inserer
     * @param joueurnom le nom du joueur
     * @param joueurprenom le prenom du joueur
     * @throws SQLException
     */
    public void inserer(int joueurid, String joueurnom, String joueurprenom) throws SQLException
    {
        stmtInsert.setInt(1, joueurid);
        stmtInsert.setString(2, joueurnom);
        stmtInsert.setString(3, joueurprenom);
        stmtInsert.executeUpdate();
    }

    /**
     *
     * @param joueurID
     * @return
     * @throws SQLException
     */
    public int supprimer(int joueurID) throws SQLException
    {
        stmtDeleteFromFaitPartie.setInt(1, joueurID);
        stmtDeleteFromFaitPartie.executeUpdate();
        stmtDeleteFromParticipe.setInt(1, joueurID);
        stmtDeleteFromParticipe.executeUpdate();
        stmtDelete.setInt(1, joueurID);
        return stmtDelete.executeUpdate();
    }
}
