/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

/**
 * Structure class representing an Arbitre
 *
 * @author fvgou_000
 */
public class Arbitre
{

    private final String newLine = "\r\n";
    int id = -1;
    String nom = null;
    String prenom = null;

    /**
     * Default Constructor
     */
    public Arbitre()
    {
    }

    /**
     * Parametric Constructor
     *
     * @param id The ID representing this Arbitre
     * @param nom The last name of this Arbitre
     * @param prenom The first name if this Arbitre
     */
    public Arbitre(int id, String nom, String prenom)
    {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    /**
     * Return a string representing the object
     *
     * @return An Arbitre as a String
     */
    public String toString()
    {
        String val = "ID: " + Integer.toString(id) + newLine;
        val += "   Nom: " + nom + newLine;
        val += "   Prenom: " + prenom + newLine;
        return val;
    }

}
