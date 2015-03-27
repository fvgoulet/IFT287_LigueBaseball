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
public class TerrainHandler 
{
    private PreparedStatement stmtLastID;
    private PreparedStatement stmtExiste;
    private PreparedStatement stmtExisteNom;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtDelete;
    private PreparedStatement stmtGetAll;
    private Connexion conn;


    /**
     * Parametric Constructor
     * @param conn A valid opened connection
     * @throws SQLException If any error happens during a transaction with the DB
     */
    public TerrainHandler(Connexion conn) throws SQLException 
    {
        this.conn = conn;
        stmtLastID = conn.getConnection().prepareStatement("select max(terrainid) from terrain");
        stmtExiste = conn.getConnection().prepareStatement("select terrainid, terrainnom, terrainadresse from terrain where terrainid = ?");
        stmtExisteNom = conn.getConnection().prepareStatement("select terrainid, terrainnom, terrainadresse from terrain where terrainnom = ?");
        stmtInsert = conn.getConnection().prepareStatement("insert into terrain (terrainid, terrainnom, terrainadresse) values (?,?,?)");
        stmtDelete = conn.getConnection().prepareStatement("delete from terrain where terrainid = ?");
        stmtGetAll = conn.getConnection().prepareStatement("select * from terrain");
    }
    
    /**
     * Get the maximum value for Terrain ID
     * @return The maximum value for Terrain ID
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
     * Check if the given Terrain exists
     * @param id The Terrain ID to check
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
     * Check if the given Terrain exists
     * @param nom The Name to check
     * @return True if it was found
     * @throws SQLException If there is any error with the connection to the DB
     */
    public boolean existe(String nom) throws SQLException 
    {
        stmtExisteNom.setString(1, nom);
        ResultSet result = stmtExisteNom.executeQuery();
        boolean exist = result.next();
        result.close();
        return exist;
    }

    /**
     * Obtain the Terrain represented by idEquipe
     * @param id The Terrain ID to obtain
     * @return The Terrain represented by the given Terrain ID
     * @throws SQLException If there is any error with the connection to the DB
     */
    public Terrain getTerrain(int id) throws SQLException 
    {
        stmtExiste.setInt(1, id);
        ResultSet result = stmtExiste.executeQuery();
        if (result.next()) 
        {
            Terrain temp = new Terrain();
            temp.id = id;
            temp.nom = result.getString(2);
            temp.adresse = result.getString(3);
            result.close();
            return temp;
        } 
        else 
        {
            return null;
        }
    }
    
    /**
     * Obtain the Terrain represented by a certain name
     * @param nom The name to obtain
     * @return The Terrain represented by the given Terrain ID
     * @throws SQLException If there is any error with the connection to the DB
     */
    public Terrain getTerrain(String nom) throws SQLException 
    {
        stmtExisteNom.setString(1, nom);
        ResultSet result = stmtExisteNom.executeQuery();
        if (result.next()) 
        {
            Terrain temp = new Terrain();
            temp.id = result.getInt(1);
            temp.nom = nom;
            temp.adresse = result.getString(3);
            result.close();
            return temp;
        } 
        else 
        {
            return null;
        }
    }
    
    /**
     * Get all Terrain in table Terrain
     * @return All Terrain found in the DB
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
     * Insert the defined Terrain to the DB
     * @param id The Terrain ID to insert
     * @param terrainNom The name of the Terrain
     * @param terrainAdresse The adress of the Terrain
     * @throws SQLException If there is any error with the connection to the DB
     */
    public void inserer(int id, String terrainNom, String terrainAdresse) throws SQLException 
    {
        stmtInsert.setInt(1, id);
        stmtInsert.setString(2, terrainNom);
        stmtInsert.setString(3, terrainAdresse);
        stmtInsert.executeUpdate();
    }
    

    /**
     * Remove the Terrain represented by the given idEquipe
     * @param id The Terrain ID to delete
     * @return The number of Terrain removed
     * @throws SQLException 
     */
    public int supprimer(int id) throws SQLException 
    {     
        stmtDelete.setInt(1, id);
        return stmtDelete.executeUpdate();
    }
}
