/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class dedicated to handle DB requests for table Equipe
 * @author fvgou_000
 */
public class EquipeHandler 
{
    private PreparedStatement stmtExiste;
    private PreparedStatement stmtExisteNom;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtUpdate;
    private PreparedStatement stmtDelete;
    private PreparedStatement stmtGetAll;
    private Connexion cx;


    /**
     * 
     * @param cx A valid opened connection
     * @throws SQLException If any error happens during a transaction with the DB
     */
    public EquipeHandler(Connexion cx) throws SQLException 
    {
        this.cx = cx;
        stmtExiste = cx.getConnection().prepareStatement("select equipeid, terrainid, equipenom from equipe where equipeid = ?");
        stmtExisteNom = cx.getConnection().prepareStatement("select equipeid, terrainid, equipenom from equipe where equipenom = ?");
        stmtInsert = cx.getConnection().prepareStatement("insert into equipe (equipeid, terrainid, equipenom) "
                + "values (?,?,?)");
        stmtUpdate = cx.getConnection().prepareStatement("update equipe set terrainid = ?, equipenom = ? "
                + "where equipeid = ?");
        stmtDelete = cx.getConnection().prepareStatement("delete from equipe where equipeid = ?");
        stmtGetAll = cx.getConnection().prepareStatement("select * from equipes");
    }

    /**
     * Check if the given Equipe exists
     * @param idEquipe The EquipeID to check
     * @return True if it was found
     * @throws SQLException If there is any error with the connection to the DB
     */
    public boolean existe(int id) throws SQLException 
    {
        stmtExiste.setInt(1, id);
        ResultSet rset = stmtExiste.executeQuery();
        boolean equipeExiste = rset.next();
        rset.close();
        return equipeExiste;
    }

    /**
     * Obtain the Equipe represented by idEquipe
     * @param id The EquipeID to obtain
     * @return The Equipe represented by the given idEquipe
     * @throws SQLException If there is any error with the connection to the DB
     */
    public Equipe getEquipe(int id) throws SQLException 
    {
        stmtExiste.setInt(1, id);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next()) 
        {
            Equipe equipe = new Equipe();
            equipe.id = id;
            equipe.idTerrain = rset.getInt(2);
            equipe.nom = rset.getString(3);
            rset.close();
            return equipe;
        } 
        else 
        {
            return null;
        }
    }
    
    /**
     * Obtain the Equipe represented by a certain name
     * @param nom The name to obtain
     * @return The Equipe represented by the given idEquipe
     * @throws SQLException If there is any error with the connection to the DB
     */
    public Equipe getEquipe(String nom) throws SQLException 
    {
        stmtExiste.setString(3, nom);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next()) 
        {
            Equipe equipe = new Equipe();
            equipe.id = rset.getInt(1);
            equipe.idTerrain = rset.getInt(2);
            equipe.nom = nom;
            rset.close();
            return equipe;
        } 
        else 
        {
            return null;
        }
    }
    
    /**
     * Get all Equipes in table Equipe
     * @return All Equipes found in the DB
     * @throws SQLException If there is any error with the connection to the DB
     */
    public ArrayList<Equipe> getAll() throws SQLException
    {
        ArrayList<Equipe> equipes = new ArrayList();
        ResultSet result = stmtGetAll.executeQuery();
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
     * Insert the defined Equipe to the DB
     * @param id The EquipeID to insert
     * @param idTerrain A valid terrainId
     * @param nom The name of the Equipe
     * @throws SQLException If there is any error with the connection to the DB
     */
    public void inserer(int id, int idTerrain, String nom) throws SQLException 
    {
        stmtInsert.setInt(1, id);
        stmtInsert.setInt(2, idTerrain);
        stmtInsert.setString(3, nom);
        stmtInsert.executeUpdate();
    }

    /**
     * Remove the Equipe represented by the given idEquipe
     * @param id
     * @return The number of Equipe removed
     * @throws SQLException 
     */
    public int supprimer(int id) throws SQLException 
    {
        stmtDelete.setInt(1, id);
        return stmtDelete.executeUpdate();
    }
}
