/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

/**
 * Structure class representing a JoueurParticipe
 *
 * @author fvgou_000
 */
public class JoueurParticipe extends Joueur
{

    String commentaire = null;
    int matchid = -1;

    /**
     * Default Constructor
     */
    public JoueurParticipe()
    {
    }

    /**
     * Parametric Constructor
     *
     * @param id A valid Joueur ID
     * @param nom The Last Name of the Joueur
     * @param prenom The First Name
     * @param matchid A valid Match ID
     * @param commentaire Commentary of his performance during the Match
     */
    public JoueurParticipe(int id, String nom, String prenom, int matchid, String commentaire)
    {
        super(id, nom, prenom);
        this.matchid = matchid;
        this.commentaire = commentaire;
    }

    /**
     * Return a string representing the object
     *
     * @return A JoueurParticipe as a String
     */
    @Override
    public String toString()
    {
        String val = super.toString();
        val += "   Match ID: " + Integer.toString(matchid) + newLine;
        val += "   Commentaire: " + commentaire + newLine;
        return val;
    }
}
