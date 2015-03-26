/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

import java.sql.SQLException;

/**
 *
 * @author fvgou_000
 */
public class MenuHandler 
{
    DBHandler db;
    EquipeHandler equipeHandler;

    public MenuHandler(String[] args) throws SQLException
    {
        db = new DBHandler(args[0], args[1], args[2]);
        equipeHandler = new EquipeHandler(db.getConnexion());
    }
    
    void Start() throws Exception
    {
        
    }
    
    private void creerEquipe(String equipeNom, String nomTerrain, String adresseTerrain)
    {
        
    }
    
    private void afficherEquipes()
    {
        
    }
    
    private void supprimerEquipe(String equipeNom)
    {
        
    }
    
    private void creerJoueur(String joueurNom, String joueurPrenom, String equipeNom, String numero, String dateDebut)
    {
        
    }
    
    private void afficherJoueursEquipe(String equipeNom)
    {
        
    }
    
    private void supprimerJoueur(String joueurNom, String joueurPrenom)
    {
        
    }
    
    private void creerMatch(String matchDate, String matchHeure, String equipeNomLocal, String equipeNomVisiteur)
    {
        
    }
    
    private void creerArbitre()
    {
        
    }
    
    private void afficherArbitres()
    {
        
    }

    private void arbitrerMatch(String matchDate, String matchHeure, String equipeNomLocal, String equipeNomVisiteur, String pointsLocal, String pointsVisiteur)
    {
        
    }
    
    private void entrerResultatMatch(String matchDate, String matchHeure, String nomEquipeLocal, String nomEquipeVisiteur, String pointsLocal, String pointsVisiteur)
    {
        
    }
    
    private void afficherResultatsDate(String aPartirDe)
    {
        
    }
    
    private void afficherResultats(String EquipeNom)
    {
        
    }
    
    
}
