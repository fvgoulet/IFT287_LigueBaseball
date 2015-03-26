/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

/**
 *
 * @author Tiger
 */
public class Match {
    int matchid;
    int equipelocal;
    int equipevisiteur;
    int terrainid;
    String matchdate;
    String matchheure;
    int pointslocal;
    int pointsvisiteur;
          

    public Match(){}
    
    public Match(int matchid, int equipelocal, int equipevisiteur, int terrainid, String matchdate, String matchheure, int pointslocal, int pointsvisiteur)
    {
        this.matchid = matchid;
        this.equipelocal = equipelocal;
        this.equipevisiteur = equipevisiteur;
        this.terrainid = terrainid;
        this.matchdate = matchdate;
        this.matchheure = matchheure;
        this.pointslocal = pointslocal;
        this.pointsvisiteur = pointsvisiteur;
    }
}
