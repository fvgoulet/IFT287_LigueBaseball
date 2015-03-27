/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

/**
 * Structure class representing an Joueur
 * @author fvgou_000
 */
public class Joueur
{
    protected final String newLine = "\r\n";
    int id = -1;
    String nom = null;
    String prenom = null;

    /**
     * Default Constructor
     */
    public Joueur(){}

    /**
     *
     * @param id id of joueur
     * @param nom name of joueur
     * @param prenom first name of Joueur
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
