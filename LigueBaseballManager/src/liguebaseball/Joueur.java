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
public class Joueur {
    int id = -1;
    String nom = null;
    String prenom = null;
    
    public Joueur(){}
    
    public Joueur(int id, String nom, String prenom)
    {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }
}
