/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

/**
 * Structure class representing an Equipe
 *
 * @author fvgou_000
 */
public class Equipe
{

    private final String newLine = "\r\n";
    int id = -1;
    int idTerrain = -1;
    String nom = null;

    /**
     * Default Constructor
     */
    public Equipe()
    {
    }

    /**
     * Parametric Constructor
     *
     * @param id The ID representing this Equipe
     * @param terrainId The TerrainId associated with this Equipe
     * @param nom The name of this Equipe
     */
    public Equipe(int id, int terrainId, String nom)
    {
        this.id = id;
        this.idTerrain = terrainId;
        this.nom = nom;
    }

    /**
     * Return a string representing the object
     *
     * @return An Equipe as a String
     */
    public String toString()
    {
        String val = "ID: " + Integer.toString(id) + newLine;
        val += "   Terrain ID: " + Integer.toString(idTerrain) + newLine;
        val += "   Nom: " + nom + newLine;
        return val;
    }

    /**
     * Return a string representing the object
     *
     * @return An Equipe as a String without Terrain
     */
    public String toStringSample()
    {
        String val = "ID: " + Integer.toString(id) + newLine;
        val += "   Nom: " + nom + newLine;
        return val;
    }
}
