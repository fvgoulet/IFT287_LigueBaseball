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
     * @param 
     */
    public Terrain(int id, String nom, String adresse)
    {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
    }
    
}
