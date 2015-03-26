/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

import java.sql.Date;

/**
 *
 * @author Tiger
 */
public class Match 
{
    private final String newLine = "\r\n";
    int id;
    int equipelocal;
    int equipevisiteur;
    int terrainid;
    Date date;
    Date heure;
    int pointslocal;
    int pointsvisiteur;
          

    public Match(){}
    
    public Match(int id, int equipelocal, int equipevisiteur, int terrainid, Date date, Date heure, int pointslocal, int pointsvisiteur)
    {
        this.id = id;
        this.equipelocal = equipelocal;
        this.equipevisiteur = equipevisiteur;
        this.terrainid = terrainid;
        this.date = date;
        this.heure = heure;
        this.pointslocal = pointslocal;
        this.pointsvisiteur = pointsvisiteur;
    }
    
    /**
     * Return a string representing the object
     * @return A Match as a String
     */
    public String toString()
    {
        String val = "ID: " + Integer.toString(id) + newLine;
        val += "   Equipe Local: " + Integer.toString(equipelocal) + newLine;
        val += "   Equipe Visiteur: " + Integer.toString(equipevisiteur) + newLine;
        val += "   Terrain ID: " + Integer.toString(terrainid) + newLine;
        val += "   Date: " + FormatDate.toString(date) + newLine;
        val += "   Heure: " + FormatDate.toString(heure) + newLine;
        val += "   Points Local: " + Integer.toString(pointslocal) + newLine;
        val += "   Points Visiteur: " + Integer.toString(pointsvisiteur) + newLine;
        return val;
    }
}
