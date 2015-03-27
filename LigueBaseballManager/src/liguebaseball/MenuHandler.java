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
import java.sql.Time;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * Class that handle the main process of LigueBaseball
 * It will handle commands either received from console input or red from a file.
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
    
    /**
     * Start MenuHandler process
     * @throws Exception If anything goes wrong 
     */
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
 
    /**
     * Read Input from console and handle commands
     * @throws Exception If anything goes wrong
     */
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
    
    /**
     * Read a file and handles commands
     * @throws Exception If anything goes wrong
     */
    private void readFile() throws Exception
    {
        String currentLine = reader.readLine();
        while(currentLine != null)
        {
            executerTransaction(currentLine.split(" "));
            currentLine = reader.readLine();
        }
    }

    /**
     * Sub method that manage Transactions to the BD from the command received
     * @param splittedcommand The command to handle
     * @throws Exception If anything goes wrong
     */
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
    
    /**
     * Show help menu to the console
     */
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


    /**
     * Parse command and call its related function if the command array is valid
     * @param command String array containing the parameters to parse and then to send to the related function
     * @throws SQLException If any error happens during a transaction with the DB 
     */
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
    
    /**
     * Create a new Equipe if it it doesn't already exists
     * @param equipeNom The name of the Equipe to add
     * @param nomTerrain The name of the Terrain of the Equipe to add
     * @param adresseTerrain The address of the Terrain of the Equipe to add
     * @throws SQLException If any error happens during a transaction with the DB 
     */
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
            if(!adresseTerrain.equals(terrain.adresse))
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
    
    /**
     * Parse command and call its related function if the command array is valid
     * @param command String array containing the parameters to parse and then to send to the related function
     * @throws SQLException If any error happens during a transaction with the DB 
     */
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
    
    /**
     * Show to the console all Equipe in the DB
     * @throws SQLException If any error happens during a transaction with the DB 
     */
    private void afficherEquipes() throws SQLException
    {
        ArrayList<Equipe> equipes = equipeHandler.getAll();
        System.out.println(Integer.toString(equipes.size()) + " Equipes trouves");
        for(Equipe equipe : equipes)
        {
            System.out.println(equipe.toString());
        }
    }
    
    /**
     * Parse command and call its related function if the command array is valid
     * @param command String array containing the parameters to parse and then to send to the related function
     * @throws SQLException If any error happens during a transaction with the DB 
     */
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
    
    /**
     * Delete the Equipe represented by equipeNom from the DB
     * @param equipeNom The name of the Equipe to deletes
     * @throws SQLException If any error happens during a transaction with the DB 
     */
    private void supprimerEquipe(String equipeNom) throws SQLException
    {
        Equipe equipe = equipeHandler.getEquipe(equipeNom);
        if(equipe == null)
        {
            System.out.println("L'equipe: " + equipeNom + " n'existe pas.");
        }
        else
        {
            equipeHandler.supprimer(equipe.id);
            System.out.println("L'equipe: " + equipeNom + " a ete supprimee.");
        }
    }
    
    /**
     * Parse command and call its related function if the command array is valid
     * @param command String array containing the parameters to parse and then to send to the related function
     * @throws SQLException If any error happens during a transaction with the DB 
     */
   private void creerJoueur(String[] command) throws SQLException
    {
        if (command.length <2)
        {
            //Explain error
        }
        else
        {
            boolean valide =true;
            String equipenom = "";
            int numero = -1;
            Date date = null;
            if (command.length == 6)
            {
                 equipenom = command[3];
                 numero = Integer.parseInt(command[4]);
                 if (DateTimeHelper.isDateValid(command[5].toString()))
                    date = java.sql.Date.valueOf(command[5]);  
                 else
                 {
                    System.out.println("la date n'est pas valide");
                    valide = false;
                 }
                
            }
            else if (command.length > 3)
            {
                equipenom = command[3];
                 numero = Integer.parseInt(command[4]);
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
                long time = System.currentTimeMillis();
                date = new Date(time);
            }
             if (valide == true)
             {
                creerJoueur(command[1],command[2],equipenom,numero,date);
             }
        }
            
    }
   
   /**
    * 
    * @param joueurNom
    * @param joueurPrenom
    * @param equipeNom
    * @param numero
    * @param dateDebut
    * @throws SQLException 
    */
    private void creerJoueur(String joueurNom, String joueurPrenom, String equipeNom, int numero, Date dateDebut) throws SQLException
    {
        Connexion cx = db.getConnexion();
        if (!joueurHandler.existeNom(joueurNom, joueurPrenom))
        {
            joueurHandler.inserer(joueurHandler.getLastID() + 1, joueurNom, joueurPrenom);
            if (numero != -1)
            {
 
             Equipe eq = equipeHandler.getEquipe(equipeNom);
             if (DateTimeHelper.isDateValid(dateDebut.toString()))
                faitpartieHandler.inserer(joueurHandler.getLastID(), eq.id, numero, dateDebut, null);
             else
                System.out.println("la date n'est pas valide");
            }
        cx.commit();
        }
        else
            System.out.println("le joueur existe deja");
    }
    
    /**
     * Parse command and call its related function if the command array is valid
     * @param command String array containing the parameters to parse and then to send to the related function
     * @throws SQLException If any error happens during a transaction with the DB 
     */
    private void afficherJoueursEquipe(String[] commande) throws SQLException
    {
        boolean EquipeNom=false;
        if(commande.length == 1)
        {
            afficherJoueursEquipe("",EquipeNom);
        }
        else if (commande.length == 2)
        {
            EquipeNom=true;
             afficherJoueursEquipe(commande[1],EquipeNom);
        }
    }
    private void afficherJoueursEquipe(String equipeNom, boolean EquipeNom) throws SQLException
    {
        if (!EquipeNom)
        {
            Map<Integer,Integer> FaitPartie = faitpartieHandler.getAll();
            for (Map.Entry<Integer,Integer> entry : FaitPartie.entrySet() )
            {
                int JoueurId = entry.getKey();
                int EquipeId = entry.getValue();
                Equipe eq =  equipeHandler.getEquipe(EquipeId);
                Joueur j = joueurHandler.getJoueur(JoueurId);
                System.out.println(j.toString() + "Nom Equipe : " + eq.nom);
            }
        }
        else
        { 
            if (equipeHandler.existe(equipeNom)){
            Equipe eq = equipeHandler.getEquipe(equipeNom);
            ArrayList<Joueur> joueurList = faitpartieHandler.getJoueursByEquipe(eq.id);
            for (Joueur j :joueurList)
            {
                System.out.println(j.toString());
            }
            }
            else
                System.out.println("l'equipe n'existe pas");
        }  
    }
    
    /**
     * Parse command and call its related function if the command array is valid
     * @param command String array containing the parameters to parse and then to send to the related function
     * @throws SQLException If any error happens during a transaction with the DB 
     */
  private void supprimerJoueur(String[] commande) throws SQLException
    {
        if(commande.length == 3)
        {
           supprimerJoueur(commande[1],commande[2]);     
        }
        else
        {
            //Gerer l'erreur
        }
    }
    private void supprimerJoueur(String joueurNom, String joueurPrenom) throws SQLException
    {
        if (joueurHandler.existeNom(joueurNom,joueurPrenom))
            {
                if (!lectureAuClavier)
                {
                     joueurHandler.supprimer(joueurHandler.getJoueurId(joueurNom, joueurPrenom).id);
                }
                else
                {
                    Joueur j = joueurHandler.getJoueurId(joueurNom, joueurPrenom);
                    System.out.println(j.toString());   
                    System.out.println("Voulez vous le supprimer? Y/N");
                    Scanner in = new Scanner(System.in);
                    String val = in.nextLine().toUpperCase();
                    if (val.contains("Y"))
                       joueurHandler.supprimer(joueurHandler.getJoueurId(joueurNom, joueurPrenom).id);
                    
                }
            }
        else
            System.out.println("Le joueur n'existe pas");
    }
    
    
    /**
     * Parse command and call its related function if the command array is valid
     * @param command String array containing the parameters to parse and then to send to the related function
     * @throws SQLException If any error happens during a transaction with the DB 
     */
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
        if ((equipeHandler.existe(equipeNomLocal) && equipeHandler.existe(equipeNomVisiteur)) &&  equipeNomLocal != equipeNomVisiteur)
        {
        Equipe eq = equipeHandler.getEquipe(equipeNomLocal);
        Equipe eq2 = equipeHandler.getEquipe(equipeNomVisiteur);
        matchHandler.inserer(matchHandler.getLastID() + 1, eq.id, eq2.id, eq.idTerrain, java.sql.Date.valueOf(matchDate), java.sql.Time.valueOf(matchHeure), 0, 0);
        }
        
    }
    
    
    /**
     * Parse command and call its related function if the command array is valid
     * @param command String array containing the parameters to parse and then to send to the related function
     * @throws SQLException If any error happens during a transaction with the DB 
     */
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
    
    /**
     * Parse command and call its related function if the command array is valid
     * @param command String array containing the parameters to parse and then to send to the related function
     * @throws SQLException If any error happens during a transaction with the DB 
     */
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

    
    /**
     * Parse command and call its related function if the command array is valid
     * @param command String array containing the parameters to parse and then to send to the related function
     * @throws SQLException If any error happens during a transaction with the DB 
     */
    private void arbitrerMatch(String[] commande) throws SQLException, Exception
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
    private void arbitrerMatch(String matchDate, String matchHeure, String equipeNomLocal, String equipeNomVisiteur, String ArbitreNom, String ArbitrePrenom) throws SQLException, Exception
    {
 
      Date date = java.sql.Date.valueOf(matchDate);
      if (matchHeure.length()< 6)
          matchHeure = matchHeure + ":00";
      Time time = java.sql.Time.valueOf(matchHeure);
      Equipe eq = equipeHandler.getEquipe(equipeNomLocal);
      Equipe eq2 = equipeHandler.getEquipe(equipeNomVisiteur);
 
        if (matchHandler.existeMatch(eq.id,eq2.id, date, time))
        {
            int matchID = matchHandler.getId(eq.id, eq2.id, java.sql.Date.valueOf(matchDate), java.sql.Time.valueOf(matchHeure));
            ArrayList<Arbitre> arbitreliste = arbitrerHandler.getArbitresByMatch(matchID);
            if (arbitreliste.size() < 4)
            {
                if (arbitreHandler.existe(ArbitreNom, ArbitrePrenom))
                    arbitrerHandler.inserer(arbitreHandler.getId(ArbitreNom,ArbitrePrenom), matchID);
            }
        }
        else
            System.out.println("le match n'existe pas");
    }
    
    /**
     * Parse command and call its related function if the command array is valid
     * @param command String array containing the parameters to parse and then to send to the related function
     * @throws SQLException If any error happens during a transaction with the DB 
     */
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
    
    
    /**
     * Parse command and call its related function if the command array is valid
     * @param command String array containing the parameters to parse and then to send to the related function
     * @throws SQLException If any error happens during a transaction with the DB 
     */
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
        Date
    }
    
    
    /**
     * Parse command and call its related function if the command array is valid
     * @param command String array containing the parameters to parse and then to send to the related function
     * @throws SQLException If any error happens during a transaction with the DB 
     */
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
