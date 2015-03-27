/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

/**
 * Structure class representing an Terrain
 * @author fvgou_000
 */
public class Terrain 
{
    private final String newLine = "\r\n";
    int id = -1;
    String nom = null;
    String adresse = null;
    
    /**
     * Default Constructor
     */
    public Terrain(){}
    
    /**
     * Parametric Constructor
     * @param id The ID representing this Terrain
     * @param nom The name of this Terrain
     * @param adresse The Terrain Address
     */
    public Terrain(int id, String nom, String adresse)
    {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
    }
    
    /**
     * Return a string representing the object
     * @return A Terrain as a String
     */
    public String toString()
    {
        String val = "ID: " + Integer.toString(id) + newLine;
        val += "   Nom: " + nom + newLine;
        val += "   Adresse: " + adresse + newLine;
        return val;
    }
    
}
