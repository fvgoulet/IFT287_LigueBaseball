/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;
import java.io.*;
import java.sql.Date;
import java.util.StringTokenizer;
import java.text.*;
import java.sql.SQLException;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author fvgou_000
 */
public class MenuHandler 
{
    DBHandler db;
    BufferedReader reader;
    EquipeHandler equipeHandler;
    ArbitreHandler arbitreHandler;
    FaitPartieHandler faitpartieHandler;
    JoueurHandler joueurHandler;
    MatchHandler matchHandler;
    ArbitrerHandler arbitrerHandler;
    TerrainHandler terrainHandler;
    private static boolean lectureAuClavier = false;
    private static boolean end = false;

    public MenuHandler(String[] args) throws Exception
    {
        db = new DBHandler(args[0], args[1], args[2]);
        equipeHandler = new EquipeHandler(db.getConnexion());
        arbitreHandler = new ArbitreHandler(db.getConnexion());
        arbitrerHandler = new ArbitrerHandler(db.getConnexion());
        faitpartieHandler = new FaitPartieHandler(db.getConnexion());
        joueurHandler = new JoueurHandler(db.getConnexion());
        matchHandler = new MatchHandler(db.getConnexion());
        terrainHandler = new TerrainHandler(db.getConnexion());
        
        
        if (args.length > 3)
        {
           reader = new BufferedReader(new FileReader(args[3]));
        }
        else
        {
            reader = new BufferedReader(new InputStreamReader(System.in));
            lectureAuClavier = true;
        }
    }
    
    public void Start() throws Exception
    {
        if(lectureAuClavier)
        {
            readInput();
        }
        else
        {
            readFile();
        }
    }
 
    private void readInput() throws Exception
    {
        while (!end)
        {
            System.out.println("Enter a command:");
            String[] command = reader.readLine().split(" ");
            switch(command[0])
            {
                case "aide":
                {
                    afficherAide();
                    break;
                }
                case "exit":
                {
                    end = true;
                    break;
                }
                default:
                {
                    executerTransaction(command);
                    break;
                }
            }
            pressAnyKeyToContinue();
        }
    }
    
    private void readFile() throws Exception
    {
        String currentLine = reader.readLine();
        while(currentLine != null)
        {
            executerTransaction(currentLine.split(" "));
            currentLine = reader.readLine();
        }
    }

    private void executerTransaction(String[] splittedcommand) throws Exception 
    {

        switch (splittedcommand[0])
        {
            case "creerEquipe":
            {
                creerEquipe(splittedcommand);
                break;
            }
            case "afficherEquipes":
            {
                System.out.println("afficherEquipes");
                afficherEquipes();
                break;
            }
            case "supprimerEquipe":
            {
                if (equipeHandler.existe(splittedcommand[1]))
                {
                    Equipe eq = equipeHandler.getEquipe(splittedcommand[1]);
                    equipeHandler.supprimer(eq.id);
                }
                break;
            }
            case "creerJoueur":
            {
                creerJoueur(splittedcommand);
                System.out.println("creerJoueur");
                break;
            }
            case "afficherJoueursEquipe":
            {
                System.out.println("afficherJoueursEquipe");
                break;
            }
            case "supprimerJoueur":
            {
                System.out.println("supprimerJoueur");
                break;
            }
            case "creerMatch":
            {
                System.out.println("creerMatch");
                break;
            }
            case "creerArbitre":
            {
                if (arbitreHandler.existe(splittedcommand[1],splittedcommand[2]))
                    System.out.println("l'arbitre existe deja");
                else
                {
                    arbitreHandler.inserer(arbitreHandler.getLastID()+1, splittedcommand[1],splittedcommand[2]);
                }
                break;
            }
            case "afficherArbitres":
            {
                ArrayList<Arbitre> listeArbitre = arbitreHandler.getAll();
                for(Arbitre ar: listeArbitre)
                {
                   System.out.println("ID : " + ar.id + " Nom:  " + ar.nom + " Prenom: " + ar.prenom);
                }
                break;
            }
            case "arbitrerMatch":
            {
                System.out.println("arbitrerMatch");
                break;
            }
            case "entrerResultatMatch":
            {
                System.out.println("entrerResultatMatch");
                break;
            }
            case "afficherResultatDate":
            {
                System.out.println("afficherResultatDate");
                break;
            }
            case "afficherResultats":
            {
                System.out.println("afficherResultats");
                break;
            }
            default:
            {
                System.out.println("  Transactions non reconnue.  Essayer \"aide\"");
                break;
            }
        }
    }
    
    private void afficherAide() 
    {
        System.out.println();
        System.out.println("Chaque transaction comporte un nom et une liste d'arguments");
        System.out.println("separes par des espaces. La liste peut etre vide.");
        System.out.println(" Les dates sont en format yyyy-mm-dd.");
        System.out.println("");
        System.out.println("Les transactions sont:");
        System.out.println("  aide");
        System.out.println("  exit");
        System.out.println("  creerEquipe  <EquipeNom> <NomTterrain> <AdresseTerrain");
        System.out.println("  afficherEquipes");
        System.out.println("  supprimerEquipe <EquipeNom>");
        System.out.println("  creerJoueur <JoueurNom> <JoueurPrenom> <EquipeNom> <Numero> <DateDebut>");
        System.out.println("  afficherJoueursEquipe <EquipeNom>");
        System.out.println("  supprimerJoueur <JoueurNom> <JoueurPrenom>");
        System.out.println("  creerMatch <MatchDate> <MatchHeure> <EquipeNomLocal> <EquipeNomVisiteur>");
        System.out.println("  creerArbitre <ArbitreNom> <ArbitrePrenom>");
        System.out.println("  afficherArbitres");
        System.out.println("  aribtrerMatch  <MatchDate> <MatchHeure> <EquipeNomLocal> <EquipeNomVisiteur> <ArbitreNom> <ArbirePRenom>");
        System.out.println("  entrerResultatMatch <MatchDate> <MatchHeure> <EquipeNomLocal> <EquipeNomVisiteur> <PointsLocal> <PointsVisiteur>");
        System.out.println("  afficherResultatsDate <APartirDate>");
        System.out.println("  afficherResultats <EquipeNom>");
    }


    private void creerEquipe(String[] command) throws SQLException
    {
        if(command.length == 4)
        {
            creerEquipe(command[1],command[2],command[3]);
        }
    }
    private void creerEquipe(String equipeNom, String nomTerrain, String adresseTerrain) throws SQLException
    {
        Terrain terrain = terrainHandler.getTerrain(nomTerrain);
        if(terrain == null)
        {
            terrain = new Terrain(terrainHandler.getLastID() + 1,nomTerrain, adresseTerrain);
            terrainHandler.inserer(terrain.id, terrain.nom, terrain.adresse);
            System.out.println("Created Terrain: " + terrain.nom);
        }
        Equipe equipe = equipeHandler.getEquipe(equipeNom);
        if(equipe == null)
        {
            equipe = new Equipe(equipeHandler.getLastID() + 1, terrain.id, equipeNom);
            equipeHandler.inserer(equipe.id, equipe.idTerrain, equipe.nom);
        }
        else
        {
            System.out.println("Equipe: " + equipeNom + " existe deja.");
        }
        
        
    }
    
    private void afficherEquipes(String[] command)
    {
        if(command.length > 1)
        {
            //Explain error
        }
        else
        {
            afficherEquipes();
        }
    }
    
    private void afficherEquipes()
    {
        
    }
    
    private void supprimerEquipe(String[] commande) throws SQLException
    {
        if (equipeHandler.existe(commande[1]))
        {
            supprimerEquipe(commande[1]);
        }
    }
    
    private void supprimerEquipe(String equipeNom) throws SQLException
    {
        Equipe eq = equipeHandler.getEquipe(equipeNom);
        equipeHandler.supprimer(eq.id);
    }
    
 private void creerJoueur(String[] command) throws SQLException
    {
        if (command.length <2)
        {
            //Explain error
        }
        else
        {
            String equipenom = "";
            int numero = -1;
            Date date = null;
            if (command.length == 6)
            {
                 equipenom = command[3];
                 numero = Integer.parseInt(command[4]);
                 //date = FormatDate.convertirDate(command[5])   
                 date = null;
            }
            else if (command.length > 2)
            {
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
                long time = System.currentTimeMillis();
                date = new Date(time);
            }
                
             creerJoueur(command[1],command[2],equipenom,numero,date);
        }
            
    }
    private void creerJoueur(String joueurNom, String joueurPrenom, String equipeNom, int numero, Date dateDebut) throws SQLException
    {
        Connexion cx = db.getConnexion();
        joueurHandler.inserer(joueurHandler.getLastID() + 1, joueurNom, joueurPrenom);
        if (numero != -1)
        {
         
         Equipe eq = equipeHandler.getEquipe(equipeNom);
         faitpartieHandler.inserer(joueurHandler.getLastID(), eq.id, numero, dateDebut, null);
        }
        cx.commit();
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
    
    /**
     * Method used to wait for the user to press a key
     * @throws IOException In case there is an error while reading the input of the user
     */
    private void pressAnyKeyToContinue() throws IOException
    {
        System.out.println("Press any key to continue...");
        System.in.read();
    }
}
