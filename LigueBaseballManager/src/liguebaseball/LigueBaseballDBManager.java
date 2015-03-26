/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

import java.sql.SQLException;



public class LigueBaseballDBManager extends DBHandler
{
    
    public LigueBaseballDBManager(String userID, String motDePasse, String baseDeDonnees) throws SQLException
    {
        super(userID, motDePasse, baseDeDonnees);
    }
    
    void creerEquipe(String equipeNom, String nomTerrain, String adresseTerrain)
    {
        
    }
    
    void afficherEquipes()
    {
        
    }
    
    void supprimerEquipe(String equipeNom)
    {
        
    }
    
    void creerJoueur(String joueurNom, String joueurPrenom, String equipeNom, String numero, String dateDebut)
    {
        
    }
    
    void afficherJoueursEquipe(String equipeNom)
    {
        
    }
    
    void supprimerJoueur(String joueurNom, String joueurPrenom)
    {
        
    }
    
    void creerMatch(String matchDate, String matchHeure, String equipeNomLocal, String equipeNomVisiteur)
    {
        
    }
    
    void creerArbitre()
    {
        
    }
    
    void afficherArbitres()
    {
        
    }

    void arbitrerMatch(String matchDate, String matchHeure, String equipeNomLocal, String equipeNomVisiteur, String pointsLocal, String pointsVisiteur)
    {
        
    }
    
    void entrerResultatMatch(String matchDate, String matchHeure, String nomEquipeLocal, String nomEquipeVisiteur, String pointsLocal, String pointsVisiteur)
    {
        
    }
    
    void afficherResultatsDate(String aPartirDe)
    {
        
    }
    
    void afficherResultats(String EquipeNom)
    {
        
    }
}
