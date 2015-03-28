/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

import java.sql.Date;

/**
 * Structure class representing a JoueurInTeam
 *
 * @author fvgou_000
 */
public class JoueurInTeam extends Joueur
{

    int equipeid;
    int numero;
    Date debut;
    Date fin;

    /**
     * Default Constructor
     */
    public JoueurInTeam()
    {
    }

    /**
     * Parametric Constructor
     *
     * @param id A valid Joueur ID
     * @param nom The Last Name of the Joueur
     * @param prenom The First Name
     * @param equipeid A valid Equipe ID
     * @param numero The number of the Joueur in this Equipe
     * @param debut Start Date
     * @param fin End Date
     */
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
     *
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
