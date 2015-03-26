/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

import java.sql.*;

/**
 * Permet d'effectuer les accès à la table Joueur.
 * <pre>
 *
 * Marc Frappier - 83 427 378
 * Université de Sherbrooke
 * version 2.0 - 13 novembre 2004
 * ift287 - exploitation de bases de données
 *
 * Cette classe gère tous les accès à la table joueur.
 *
 *</pre>
 */
public class JoueurHandler {

    private PreparedStatement stmtExiste;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtUpdate;
    private PreparedStatement stmtDelete;
    private PreparedStatement stmtLastID;
    private Connexion cx;


    public JoueurHandler(Connexion cx) throws SQLException {

        this.cx = cx;
        stmtLastID = cx.getConnection().prepareStatement("select max(joueurid) from joueur");
        stmtExiste = cx.getConnection().prepareStatement("select joueurid, joueurnom, joueurprenom from joueur where equipeid = ?");
        stmtInsert = cx.getConnection().prepareStatement("insert into joueur (joueurid, joueurnom, joueurprenom) "
                + "values (?,?,?)");
        stmtUpdate = cx.getConnection().prepareStatement("update joueur set joueurnom = ?, joueurprenom = ? "
                + "where joueurid = ?");
        stmtDelete = cx.getConnection().prepareStatement("delete from joueur where joueurid = ?");
    }


    public Connexion getConnexion() {

        return cx;
    }


    public boolean existe(int joueurid) throws SQLException {

        stmtExiste.setInt(1, joueurid);
        ResultSet rset = stmtExiste.executeQuery();
        boolean equipeExiste = rset.next();
        rset.close();
        return equipeExiste;
    }

    public Joueur getJoueur(int joueurid) throws SQLException 
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

    /**
     * Get the maximum value for Joueur ID
     * @return The maximum value for Joueur ID
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
    
    
    public void inserer(int joueurid, String joueurnom, String joueurprenom) throws SQLException 
    {
        stmtInsert.setInt(1, joueurid);
        stmtInsert.setString(2, joueurnom);
        stmtInsert.setString(3, joueurprenom);
        stmtInsert.executeUpdate();
    }

    public int supprimer(int idEquipe) throws SQLException 
    {
        stmtDelete.setInt(1, idEquipe);
        return stmtDelete.executeUpdate();
    }
}
