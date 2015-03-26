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
public class ArbitreHandler 
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
     * 
     * @param conn A valid opened connection
     * @throws SQLException If any error happens during a transaction with the DB
     */
    public ArbitreHandler(Connexion conn) throws SQLException
    {
        this.cx = conn;
        stmtLastID = conn.getConnection().prepareStatement("select max(arbitreid) from arbitre");
        stmtExiste = conn.getConnection().prepareStatement("select arbitreid, arbitrenom, arbitreprenom from arbitre where arbitreid = ?");
        stmtExisteNom = conn.getConnection().prepareStatement("select arbitreid, arbitrenom, arbitreprenom from arbitre where arbitrenom = ? and arbitreprenom = ?");
        stmtInsert = conn.getConnection().prepareStatement("insert into arbitre (arbitreid, arbitrenom, arbitreprenom) values (?,?,?)");
        stmtUpdate = conn.getConnection().prepareStatement("update arbitre set arbitrenom = ?, arbitreprenom = ? where arbitreid = ?");
        stmtDelete = conn.getConnection().prepareStatement("delete from arbitre where arbitreid = ?");
        stmtGetAll = conn.getConnection().prepareStatement("select * from arbitre order by arbitrenom");
    }
    
    /**
     * Get the maximum value for Arbitre ID
     * @return The maximum value for Arbitre ID
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
     * Check if the given Arbitre exists
     * @param id The Arbitre ID to check
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
     * Check if the given Arbitre exists
     * @param nom The last name of the Arbitre
     * @param prenom The first name of the Arbitre
     * @return True if it was found
     * @throws SQLException If there is any error with the connection to the DB
     */
    public boolean existe(String nom, String prenom) throws SQLException
    {
        stmtExisteNom.setString(2, nom);
        stmtExisteNom.setString(3, prenom);
        ResultSet result = stmtExisteNom.executeQuery();
        boolean exist = result.next();
        result.close();
        return exist;
    }

    /**
     * Obtain the Arbitre represented by idEquipe
     * @param id The Arbitre ID to obtain
     * @return The Arbitre represented by the given id
     * @throws SQLException If there is any error with the connection to the DB
     */
    public Arbitre getArbitre(int id) throws SQLException
    {
        stmtExiste.setInt(1, id);
        ResultSet result = stmtExiste.executeQuery();
        if (result.next()) 
        {
            Arbitre arbitre = new Arbitre();
            arbitre.id = id;
            arbitre.nom = result.getString(2);
            arbitre.prenom = result.getString(3);
            result.close();
            return arbitre;
        } 
        else 
        {
            return null;
        }
    }
    
    /**
     * Obtain the Arbitre represented by a certain name
     * @param nom The last name of the Arbitre
     * @param prenom The first name of the Arbitre
     * @return The Arbitre represented by the given idEquipe
     * @throws SQLException If there is any error with the connection to the DB
     */
    public Arbitre getArbitre(String nom, String prenom) throws SQLException
    {
        stmtExisteNom.setString(2, nom);
        stmtExisteNom.setString(3, prenom);
        ResultSet result = stmtExisteNom.executeQuery();
        if (result.next()) 
        {
            Arbitre arbitre = new Arbitre();
            arbitre.id = result.getInt(1);
            arbitre.nom = nom;
            arbitre.prenom = prenom;
            result.close();
            return arbitre;
        } 
        else 
        {
            return null;
        }
    }
    
    /**
     * Get all Arbitre
     * @return All Arbitre found in the DB
     * @throws SQLException If there is any error with the connection to the DB
     */
    public ArrayList<Arbitre> getAll() throws SQLException
    {
        ArrayList<Arbitre> arbitres = new ArrayList();
        ResultSet result = stmtGetAll.executeQuery();
        while(result.next())
        {
            Arbitre temp = new Arbitre();
            temp.id = result.getInt(1);
            temp.nom = result.getString(2);
            temp.prenom = result.getString(3);
            arbitres.add(temp);
        }
        result.close();
        return arbitres;
    }

    /**
     * Insert the defined Arbitre to the DB
     * @param id The Arbitre ID to insert
     * @param nom The last name of the Arbitre
     * @param prenom The last name of the Arbitre
     * @throws SQLException If there is any error with the connection to the DB
     */
    public void inserer(int id, String nom, String prenom) throws SQLException
    {
        stmtInsert.setInt(1, id);
        stmtInsert.setString(2, nom);
        stmtInsert.setString(3, prenom);
        stmtInsert.executeUpdate();
    }
    
    /**
     * Modify the defined Arbitre
     * @param id The Arbitre ID to insert
     * @param nom The last name of the Arbitre
     * @param prenom The last name of the Arbitre
     * @return The number of Arbitre Modified
     * @throws SQLException If there is any error with the connection to the DB
     */
    public int modifier(int id, String nom, String prenom) throws SQLException
    {
        stmtUpdate.setInt(1,id);
        stmtUpdate.setString(2,nom);
        stmtUpdate.setString(3,prenom);
        return stmtUpdate.executeUpdate();
    }

    /**
     * Remove the Arbitre represented by the given idEquipe
     * @param id The Arbitre ID to delete
     * @return The number of Arbitre removed
     * @throws SQLException 
     */
    public int supprimer(int id) throws SQLException 
    {
        stmtDelete.setInt(1, id);
        return stmtDelete.executeUpdate();
    }
}
