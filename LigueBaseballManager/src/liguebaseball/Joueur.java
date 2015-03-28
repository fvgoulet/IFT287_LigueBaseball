/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

/**
 * Structure class representing a Joueur
 *
 * @author fvgou_000
 */
public class Joueur
{

    /**
     * Newline Wrapper
     */
    protected final String newLine = "\r\n";

    int id = -1;
    String nom = null;
    String prenom = null;

    /**
     * Default Constructor
     */
    public Joueur()
    {
    }

    /**
     *
     * @param id The Joueur ID
     * @param nom The Joueur last Name
     * @param prenom The Joueur first Name
     */
    public Joueur(int id, String nom, String prenom)
    {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    /**
     * Return a string representing the object
     *
     * @return A Joueur as a String
     */
    public String toString()
    {
        String val = "ID: " + Integer.toString(id) + newLine;
        val += "   Nom: " + nom + newLine;
        val += "   Prenom: " + prenom + newLine;
        return val;
    }
}
