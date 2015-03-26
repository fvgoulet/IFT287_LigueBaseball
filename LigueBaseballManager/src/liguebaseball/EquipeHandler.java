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
    private PreparedStatement stmtLastID;
    private PreparedStatement stmtExiste;
    private PreparedStatement stmtExisteNom;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtUpdate;
    private PreparedStatement stmtDelete;
    private PreparedStatement stmtGetAll;
    private Connexion cx;


    /**
     * Parametric Constructor
     * @param conn A valid opened connection
     * @throws SQLException If any error happens during a transaction with the DB
     */
    public EquipeHandler(Connexion conn) throws SQLException 
    {
        this.cx = conn;
        stmtLastID = conn.getConnection().prepareStatement("select max(equipeid) from equipe");
        stmtExiste = conn.getConnection().prepareStatement("select equipeid, terrainid, equipenom from equipe where equipeid = ?");
        stmtExisteNom = conn.getConnection().prepareStatement("select equipeid, terrainid, equipenom from equipe where equipenom = ?");
        stmtInsert = conn.getConnection().prepareStatement("insert into equipe (equipeid, terrainid, equipenom) "
                + "values (?,?,?)");
        stmtUpdate = conn.getConnection().prepareStatement("update equipe set terrainid = ?, equipenom = ? "
                + "where equipeid = ?");
        stmtDelete = conn.getConnection().prepareStatement("delete from equipe where equipeid = ?");
        stmtGetAll = conn.getConnection().prepareStatement("select * from equipe");
    }
    
    /**
     * Get the maximum value for Equipe ID
     * @return The maximum value for Equipe ID
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

    /**
     * Check if the given Equipe exists
     * @param id The Equipe ID to check
     * @return True if it was found
     * @throws SQLException If there is any error with the connection to the DB
     */
    public boolean existe(int id) throws SQLException 
    {
        stmtExiste.setInt(1, id);
        ResultSet result = stmtExiste.executeQuery();
        boolean exist = result.next();
        result.close();
        return exist;
    }
    
    /**
     * Check if the given Equipe exists
     * @param nom The Name to check
     * @return True if it was found
     * @throws SQLException If there is any error with the connection to the DB
     */
    public boolean existe(String nom) throws SQLException 
    {
        stmtExisteNom.setString(3, nom);
        ResultSet result = stmtExisteNom.executeQuery();
        boolean exist = result.next();
        result.close();
        return exist;
    }

    /**
     * Obtain the Equipe represented by idEquipe
     * @param id The Equipe ID to obtain
     * @return The Equipe represented by the given idEquipe
     * @throws SQLException If there is any error with the connection to the DB
     */
    public Equipe getEquipe(int id) throws SQLException 
    {
        stmtExiste.setInt(1, id);
        ResultSet result = stmtExiste.executeQuery();
        if (result.next()) 
        {
            Equipe equipe = new Equipe();
            equipe.id = id;
            equipe.idTerrain = result.getInt(2);
            equipe.nom = result.getString(3);
            result.close();
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
        stmtExisteNom.setString(3, nom);
        ResultSet result = stmtExisteNom.executeQuery();
        if (result.next()) 
        {
            Equipe equipe = new Equipe();
            equipe.id = result.getInt(1);
            equipe.idTerrain = result.getInt(2);
            equipe.nom = nom;
            result.close();
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
     * @param id The Equipe ID to insert
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
     * Modify the defined Equipe
     * @param id The Equipe ID to insert
     * @param idTerrain A valid terrainId
     * @param nom The name of the Equipe
     * @return The number of Equipe Modified
     * @throws SQLException If there is any error with the connection to the DB
     */
    public int modifier(int id, int idTerrain, String nom) throws SQLException
    {
        stmtUpdate.setInt(1,id);
        stmtUpdate.setInt(2,idTerrain);
        stmtUpdate.setString(3,nom);
        return stmtUpdate.executeUpdate();
    }

    /**
     * Remove the Equipe represented by the given idEquipe
     * @param id The Equipe ID to delete
     * @return The number of Equipe removed
     * @throws SQLException 
     */
    public int supprimer(int id) throws SQLException 
    {
        stmtDelete.setInt(1, id);
        return stmtDelete.executeUpdate();
    }
}
