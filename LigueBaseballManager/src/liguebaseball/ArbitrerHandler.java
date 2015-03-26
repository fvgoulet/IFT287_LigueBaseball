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
public class ArbitrerHandler 
{
    private PreparedStatement stmtMatchsByArbitre;
    private PreparedStatement stmtArbitresByMatch;
    private PreparedStatement stmtExisteNom;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtUpdate;
    private PreparedStatement stmtDelete;
    private PreparedStatement stmtGetAll;
    private Connexion conn;


    /**
     * Parametric Constructor
     * @param conn A valid opened connection
     * @throws SQLException If any error happens during a transaction with the DB
     */
    public ArbitrerHandler(Connexion conn) throws SQLException 
    {
        this.conn = conn;
        stmtMatchsByArbitre = conn.getConnection().prepareStatement("select matchid from arbitrer where arbitreid = ?");
        stmtArbitresByMatch = conn.getConnection().prepareStatement("select arbitreid from arbitrer where arbitreid = ?");
        stmtExisteNom = conn.getConnection().prepareStatement("select equipeid, terrainid, equipenom from arbitrer where equipenom = ?");
        stmtInsert = conn.getConnection().prepareStatement("insert into arbitrer (equipeid, terrainid, equipenom) values (?,?,?)");
        stmtUpdate = conn.getConnection().prepareStatement("update arbitrer set terrainid = ?, equipenom = ? where equipeid = ?");
        stmtDelete = conn.getConnection().prepareStatement("delete from arbitrer where equipeid = ?");
        stmtGetAll = conn.getConnection().prepareStatement("select * from arbitrer");
    }

    /**
     * Obtain the Equipe represented by idEquipe
     * @param id The Equipe ID to obtain
     * @return The Equipe represented by the given idEquipe
     * @throws SQLException If there is any error with the connection to the DB
     */
    public ArrayList<Match> getMatchsByArbitre(int arbitreid) throws SQLException 
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
