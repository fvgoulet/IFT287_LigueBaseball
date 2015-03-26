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
    public boolean existe(int idEquipe) throws SQLException 
    {
        stmtExiste.setInt(1, idEquipe);
        ResultSet rset = stmtExiste.executeQuery();
        boolean equipeExiste = rset.next();
        rset.close();
        return equipeExiste;
    }

    /**
     * Obtain the Equipe represented by idEquipe
     * @param idEquipe The EquipeID to obtain
     * @return The Equipe represented by the given idEquipe
     * @throws SQLException If there is any error with the connection to the DB
     */
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
            temp.idEquipe = result.getInt(1);
            temp.idTerrain = result.getInt(2);
            temp.equipeNom = result.getString(3);
            equipes.add(temp);
        }
        result.close();
        return equipes;
    }

    /**
     * Insert the defined Equipe to the DB
     * @param idEquipe The EquipeID to insert
     * @param idTerrain A valid terrainId
     * @param equipeNom The name of the Equipe
     * @throws SQLException If there is any error with the connection to the DB
     */
    public void inserer(int idEquipe, int idTerrain, String equipeNom) throws SQLException 
    {
        stmtInsert.setInt(1, idEquipe);
        stmtInsert.setInt(2, idTerrain);
        stmtInsert.setString(3, equipeNom);
        stmtInsert.executeUpdate();
    }

    /**
     * Remove the Equipe represented by the given idEquipe
     * @param idEquipe
     * @return The number of Equipe removed
     * @throws SQLException 
     */
    public int supprimer(int idEquipe) throws SQLException 
    {
        stmtDelete.setInt(1, idEquipe);
        return stmtDelete.executeUpdate();
    }
}
