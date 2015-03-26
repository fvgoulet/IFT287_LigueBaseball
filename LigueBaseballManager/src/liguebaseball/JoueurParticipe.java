/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

/**
 *
 * @author fvgou_000
 */
public class JoueurParticipe extends Joueur 
{
    String commentaire = null;
    int matchid = -1;
    
    public JoueurParticipe(){}
    
    public JoueurParticipe(int id, String nom, String prenom, int matchid, String commentaire)
    {
        super(id, nom, prenom);
        this.matchid = matchid;
        this.commentaire = commentaire;
    }
    
    /**
     * Return a string representing the object
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
