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
 * Cette classe gère tous les accès à la table livre.
 *
 *</pre>
 */
public class EquipeHandler {

    private PreparedStatement stmtExiste;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtUpdate;
    private PreparedStatement stmtDelete;
    private Connexion cx;


    public EquipeHandler(Connexion cx) throws SQLException {

        this.cx = cx;
        stmtExiste = cx.getConnection().prepareStatement("select equipeid, terrainid, equipenom from equipe where equipeid = ?");
        stmtInsert = cx.getConnection().prepareStatement("insert into equipe (equipeid, terrainid, equipenom) "
                + "values (?,?,?)");
        stmtUpdate = cx.getConnection().prepareStatement("update equipe set terrainid = ?, equipenom = ? "
                + "where equipeid = ?");
        stmtDelete = cx.getConnection().prepareStatement("delete from equipe where equipeid = ?");
    }


    public Connexion getConnexion() {

        return cx;
    }


    public boolean existe(int idEquipe) throws SQLException {

        stmtExiste.setInt(1, idEquipe);
        ResultSet rset = stmtExiste.executeQuery();
        boolean equipeExiste = rset.next();
        rset.close();
        return equipeExiste;
    }

    public Equipe getEquipe(int idEquipe) throws SQLException 
    {
        stmtExiste.setInt(1, idEquipe);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next()) 
        {
            Equipe equipe = new Equipe();
            equipe.idEquipe = idEquipe;
            equipe.idTerrain = rset.getInt(2);
            equipe.equipeNom = rset.getString(3);
            rset.close();
            return equipe;
        } 
        else 
        {
            return null;
        }
    }


    public void inserer(int idEquipe, int idTerrain, String equipeNom) throws SQLException 
    {
        stmtInsert.setInt(1, idEquipe);
        stmtInsert.setInt(2, idTerrain);
        stmtInsert.setString(3, equipeNom);
        stmtInsert.executeUpdate();
    }

    public int supprimer(int idEquipe) throws SQLException 
    {
        stmtDelete.setInt(1, idEquipe);
        return stmtDelete.executeUpdate();
    }
}
