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
public class Equipe {
    int idEquipe;
    int idTerrain;
    String equipeNom;
    
    public Equipe(){}
    
    public Equipe(int equipeId, int terrainId, String nomEquipe)
    {
        this.idEquipe = equipeId;
        this.idTerrain = terrainId;
        this.equipeNom = nomEquipe;
    }
}
