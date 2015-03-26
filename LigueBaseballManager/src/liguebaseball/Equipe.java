/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

/**
 * Structure class representing an Equipe
 * @author fvgou_000
 */
public class Equipe {
    int idEquipe;
    int idTerrain;
    String equipeNom;
    
    /**
     * Default Constructor
     */
    public Equipe(){}
    
    /**
     * Parametric Constructor
     * @param equipeId The equipeId representing this Equipe
     * @param terrainId The TerrainId associated with this Equipe
     * @param nomEquipe The name os this Equipe
     */
    public Equipe(int equipeId, int terrainId, String nomEquipe)
    {
        this.idEquipe = equipeId;
        this.idTerrain = terrainId;
        this.equipeNom = nomEquipe;
    }
}
