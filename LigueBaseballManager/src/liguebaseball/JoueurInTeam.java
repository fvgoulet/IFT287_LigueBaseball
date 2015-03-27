/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

import java.sql.Date;

/**
 *
 * @author fvgou_000
 */
public class JoueurInTeam extends Joueur 
{
    int equipeid;
    int numero;
    Date debut;
    Date fin;
    
    public JoueurInTeam(){}
    
    public JoueurInTeam(int id, String nom, String prenom, int equipeid, int numero, Date debut, Date fin)
    {
        super(id, nom, prenom);
        this.equipeid = equipeid;
        this.numero = numero;
        this.debut = debut;
        this.fin = fin;
    }
    
    /**
     * Return a string representing the object
     * @return A JoueurInTeam as a String
     */
    @Override
    public String toString()
    {
        String val = super.toString();
        val += "   Equipe ID: " + Integer.toString(equipeid) + newLine;
        val += "   Numero: " + Integer.toString(numero) + newLine;
        val += "   Date debut: " + DateTimeHelper.toString(debut) + newLine;
        val += "   Date fin: " + DateTimeHelper.toString(fin) + newLine;
        return val;
    }
}
