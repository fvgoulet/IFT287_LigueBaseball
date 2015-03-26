/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;
import java.io.*;
import java.sql.Date;
import java.text.*;
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
        try
        {
            if(lectureAuClavier)
            {
                readInput();
            }
            else
            {
                readFile();
            }
            db.getConnexion().commit();
        }
        catch(Exception ex)
        {
            System.out.println("An error occurred during a transaction: " + "\r\n" + ex.toString());
            db.getConnexion().rollback();
        }
        db.getConnexion().fermer();
    }
 
    private void readInput() throws Exception
    {
        while (!end)
        {
            
            System.out.println("Entrz une commande:");
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
                System.out.println("Commande afficherEquipes");
                creerEquipe(splittedcommand);
                break;
            }
            case "afficherEquipes":
            {
                System.out.println("afficherEquipes");
                afficherEquipes(splittedcommand);
                break;
            }
            case "supprimerEquipe":
            {
                System.out.println("supprimerEquipe");
                supprimerEquipe(splittedcommand);
                break;
            }
            case "creerJoueur":
            {
                System.out.println("creerJoueur");
                creerJoueur(splittedcommand);
                break;
            }
            case "afficherJoueursEquipe":
            {
                System.out.println("afficherJoueursEquipe");
                afficherJoueursEquipe(splittedcommand);
                break;
            }
            case "supprimerJoueur":
            {
                System.out.println("supprimerJoueur");
                supprimerJoueur(splittedcommand);
                break;
            }
            case "creerMatch":
            {
                System.out.println("creerMatch");
                creerMatch(splittedcommand);
                break;
            }
            case "creerArbitre":
            {
                System.out.println("creerArbitre");
                creerArbitre(splittedcommand);
                break;
            }
            case "afficherArbitres":
            {
                System.out.println("afficherArbitres");
                afficherArbitres(splittedcommand);
                break;
            }
            case "arbitrerMatch":
            {
                System.out.println("arbitrerMatch");
                arbitrerMatch(splittedcommand);
                break;
            }
            case "entrerResultatMatch":
            {
                System.out.println("entrerResultatMatch");
                entrerResultatMatch(splittedcommand);
                break;
            }
            case "afficherResultatDate":
            {
                System.out.println("afficherResultatDate");
                afficherResultatsDate(splittedcommand);
                break;
            }
            case "afficherResultats":
            {
                System.out.println("afficherResultats");
                afficherResultats(splittedcommand);
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
        else
        {
            
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
        else
        {
            if(adresseTerrain != terrain.adresse)
            {
                System.out.println("Le terrain: " + terrain.nom + " ne correspond pas à l'adresse: " + terrain.adresse);
                System.out.println("Veuillez entrer la bonne adresse: " + terrain.adresse + " ou entrez un nom de terrain different.");
                return;
            }
        }
        Equipe equipe = equipeHandler.getEquipe(equipeNom);
        if(equipe == null)
        {
            equipe = new Equipe(equipeHandler.getLastID() + 1, terrain.id, equipeNom);
            equipeHandler.inserer(equipe.id, equipe.idTerrain, equipe.nom);
            System.out.println("Equipe: " + equipeNom + " cree.");
        }
        else
        {
            System.out.println("Equipe: " + equipeNom + " existe deja.");
        }
    }
    
    private void afficherEquipes(String[] command) throws SQLException
    {
        if(command.length > 1)
        {
            System.out.println(" Cette commande ne prend pas de paramêtres.");
        }
        else
        {
            afficherEquipes();
        }
    }
    
    private void afficherEquipes() throws SQLException
    {
        ArrayList<Equipe> equipes = equipeHandler.getAll();
        System.out.println(Integer.toString(equipes.size()) + " Equipes trouves");
        for(Equipe equipe : equipes)
        {
            System.out.println(equipe.toString());
        }
    }
    
    private void supprimerEquipe(String[] commande) throws SQLException
    {
        if (equipeHandler.existe(commande[1]))
        {
            supprimerEquipe(commande[1]);
        }
        else
        {
            System.out.println("L'equipe: " + commande[1] + " n'existe pas.");
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
                 date = java.sql.Date.valueOf(command[5]);  
            }
            else if (command.length > 3)
            {
                equipenom = command[3];
                 numero = Integer.parseInt(command[4]);
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
    private void afficherJoueursEquipe(String[] commande) throws SQLException
    {
        if(commande.length == 2)
        {
            afficherJoueursEquipe(commande[1]);
        }
        else
        {
            //Gerer l'erreur
        }
    }
    private void afficherJoueursEquipe(String equipeNom) throws SQLException
    {
        
    }
    
    
    private void supprimerJoueur(String[] commande) throws SQLException
    {
        if(commande.length == 3)
        {
            supprimerJoueur(commande[1], commande[2]);
        }
        else
        {
            //Gerer l'erreur
        }
    }
    private void supprimerJoueur(String joueurNom, String joueurPrenom) throws SQLException
    {
        
    }
    
    
    private void creerMatch(String[] commande) throws SQLException
    {
        if(commande.length == 5)
        {
            creerMatch(commande[1], commande[2], commande[3], commande[4]);
        }
        else
        {
            //Gerer l'erreur
        }
    }
    private void creerMatch(String matchDate, String matchHeure, String equipeNomLocal, String equipeNomVisiteur) throws SQLException
    {
        
    }
    
    
    private void creerArbitre(String[] commande) throws SQLException
    {
        if(commande.length == 3)
        {
            creerArbitre(commande[1], commande[2]);
        }
        else
        {
            //Gerer l'erreur
        }
    }
    private void creerArbitre(String nom, String prenom) throws SQLException
    {
        Arbitre arbitre = arbitreHandler.getArbitre(nom, prenom);
        if(arbitre == null)
        {
            arbitre = new Arbitre(arbitreHandler.getLastID() + 1, nom, prenom);
            arbitreHandler.inserer(arbitre.id, arbitre.nom, arbitre.prenom);
            System.out.println("Created Arbitre: " + nom + " ," + prenom);
        }
        else
        {
            System.out.println("L'Arbitre: " + nom + " ," + prenom + " existe deja.");
        }
    }
    
    
    private void afficherArbitres(String[] command) throws SQLException
    {
        if(command.length == 1)
        {
            afficherArbitres();
        }
        else
        {
            System.out.println("Cette commande ne prend pas de parametres.");
        }
    }
    private void afficherArbitres() throws SQLException
    {
        ArrayList<Arbitre> arbitres = arbitreHandler.getAll();
        System.out.println(Integer.toString(arbitres.size()) + " Arbitres trouves");
        for(Arbitre arbitre : arbitres)
        {
            System.out.println(arbitre.toString());
        }
    }

    private void arbitrerMatch(String[] commande) throws SQLException
    {
        if(commande.length == 7)
        {
            arbitrerMatch(commande[1], commande[2], commande[3], commande[4], commande[5], commande[6]);
        }
        else
        {
            //Gerer l'erreur
        }
    }
    private void arbitrerMatch(String matchDate, String matchHeure, String equipeNomLocal, String equipeNomVisiteur, String pointsLocal, String pointsVisiteur) throws SQLException
    {
        
    }
    
    private void entrerResultatMatch(String[] commande) throws SQLException
    {
        if(commande.length == 7)
        {
            entrerResultatMatch(commande[1], commande[2], commande[3], commande[4], commande[5], commande[6]);
        }
        else
        {
            //Gerer l'erreur
        }
    }
    private void entrerResultatMatch(String matchDate, String matchHeure, String nomEquipeLocal, String nomEquipeVisiteur, String pointsLocal, String pointsVisiteur) throws SQLException
    {
        
    }
    
    private void afficherResultatsDate(String[] commande) throws SQLException
    {
        if(commande.length == 2)
        {
            afficherResultatsDate(commande[1]);
        }
        else
        {
            //Gerer l'erreur
        }
    }
    private void afficherResultatsDate(String aPartirDe) throws SQLException
    {
        
    }
    
    private void afficherResultats(String[] commande) throws SQLException
    {
        if(commande.length == 2)
        {
            afficherResultats(commande[1]);
        }
        else
        {
            //Gerer l'erreur
        }
    }
    private void afficherResultats(String EquipeNom) throws SQLException
    {
        Equipe equipe = equipeHandler.getEquipe(EquipeNom);
        if(equipe == null)
        {
            System.out.println("L'equipe: " + EquipeNom + " n'existe pas.");
            return;
        }
        ArrayList<Arbitre> arbitres = arbitreHandler.getAll();
        System.out.println(Integer.toString(arbitres.size()) + " Arbitres trouves");
        for(Arbitre arbitre : arbitres)
        {
            System.out.println(arbitre.toString());
        }
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
