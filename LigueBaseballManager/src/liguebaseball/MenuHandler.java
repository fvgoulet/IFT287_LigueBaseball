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
 * Class that handle the main process of LigueBaseball It will handle commands
 * either received from console input or red from a file.
 *
 * @author fvgou_000
 */
public class MenuHandler
{

    Logger logger;
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
        logger = new Logger("LigueBaseballLog_" + DateTimeHelper.getDateTimeString() + ".txt");

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
     *
     * @throws Exception If anything goes wrong
     */
    public void Start() throws Exception
    {
        try
        {
            if (lectureAuClavier)
            {
                readInput();
            }
            else
            {
                readFile();
            }
            db.getConnexion().commit();
        }
        catch (Exception ex)
        {
            logger.Log("An error occurred during a transaction: " + "\r\n" + ex.toString());
            db.getConnexion().rollback();
        }
        db.getConnexion().fermer();
    }

    /**
     * Read Input from console and handle commands
     *
     * @throws Exception If anything goes wrong
     */
    private void readInput() throws Exception
    {
        while (!end)
        {

            logger.Log("Entrz une commande:");
            String[] command = reader.readLine().split(" ");
            switch (command[0])
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
     *
     * @throws Exception If anything goes wrong
     */
    private void readFile() throws Exception
    {
        String currentLine = reader.readLine();
        while (currentLine != null)
        {
            executerTransaction(currentLine.split(" "));
            currentLine = reader.readLine();
        }
    }

    /**
     * Sub method that manage Transactions to the BD from the command received
     *
     * @param splittedcommand The command to handle
     * @throws Exception If anything goes wrong
     */
    private void executerTransaction(String[] splittedcommand) throws Exception
    {

        switch (splittedcommand[0])
        {
            case "creerEquipe":
            {
                logger.Log("Commande creerEquipe");
                creerEquipe(splittedcommand);
                break;
            }
            case "afficherEquipes":
            {
                logger.Log("Commande afficherEquipes");
                afficherEquipes(splittedcommand);
                break;
            }
            case "supprimerEquipe":
            {
                logger.Log("Commande supprimerEquipe");
                supprimerEquipe(splittedcommand);
                break;
            }
            case "creerJoueur":
            {
                logger.Log("Commande creerJoueur");
                creerJoueur(splittedcommand);
                break;
            }
            case "afficherJoueursEquipe":
            {
                logger.Log("Commande afficherJoueursEquipe");
                afficherJoueursEquipe(splittedcommand);
                break;
            }
            case "supprimerJoueur":
            {
                logger.Log("Commande supprimerJoueur");
                supprimerJoueur(splittedcommand);
                break;
            }
            case "creerMatch":
            {
                logger.Log("Commande creerMatch");
                creerMatch(splittedcommand);
                break;
            }
            case "creerArbitre":
            {
                logger.Log("Commande creerArbitre");
                creerArbitre(splittedcommand);
                break;
            }
            case "afficherArbitres":
            {
                logger.Log("Commande afficherArbitres");
                afficherArbitres(splittedcommand);
                break;
            }
            case "arbitrerMatch":
            {
                logger.Log("Commande arbitrerMatch");
                arbitrerMatch(splittedcommand);
                break;
            }
            case "entrerResultatMatch":
            {
                logger.Log("Commande entrerResultatMatch");
                entrerResultatMatch(splittedcommand);
                break;
            }
            case "afficherResultatDate":
            {
                logger.Log("Commande afficherResultatDate");
                afficherResultatsDate(splittedcommand);
                break;
            }
            case "afficherResultats":
            {
                logger.Log("Commande afficherResultats");
                afficherResultats(splittedcommand);
                break;
            }
            case "aide":
            {
                afficherAide();
                break;
            }
            case "--":
            {
                break;
            }
            default:
            {
                logger.Log("  Transactions non reconnue.  Essayer \"aide\"");
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
     *
     * @param command String array containing the parameters to parse and then
     * to send to the related function
     * @throws SQLException If any error happens during a transaction with the
     * DB
     */
    private void creerEquipe(String[] command) throws SQLException, IOException
    {
        if (command.length == 4)
        {
            creerEquipe(command[1], command[2], command[3]);
        }
        else
        {

        }
    }

    /**
     * Create a new Equipe if it it doesn't already exists
     *
     * @param equipeNom The name of the Equipe to add
     * @param nomTerrain The name of the Terrain of the Equipe to add
     * @param adresseTerrain The address of the Terrain of the Equipe to add
     * @throws SQLException If any error happens during a transaction with the
     * DB
     */
    private void creerEquipe(String equipeNom, String nomTerrain, String adresseTerrain) throws SQLException, IOException
    {
        Terrain terrain = terrainHandler.getTerrain(nomTerrain);
        if (terrain == null)
        {
            terrain = new Terrain(terrainHandler.getLastID() + 1, nomTerrain, adresseTerrain);
            terrainHandler.inserer(terrain.id, terrain.nom, terrain.adresse);
            logger.Log("Created Terrain: " + terrain.nom);
        }
        else
        {
            if (!adresseTerrain.equals(terrain.adresse))
            {
                logger.Log("Le terrain: " + terrain.nom + " ne correspond pas à l'adresse: " + terrain.adresse);
                logger.Log("Veuillez entrer la bonne adresse: " + terrain.adresse + " ou entrez un nom de terrain different.");
                return;
            }
        }
        Equipe equipe = equipeHandler.getEquipe(equipeNom);
        if (equipe == null)
        {
            equipe = new Equipe(equipeHandler.getLastID() + 1, terrain.id, equipeNom);
            equipeHandler.inserer(equipe.id, equipe.idTerrain, equipe.nom);
            logger.Log("Equipe: " + equipeNom + " cree.");
        }
        else
        {
            logger.Log("Equipe: " + equipeNom + " existe deja.");
        }
    }

    /**
     * Parse command and call its related function if the command array is valid
     *
     * @param command String array containing the parameters to parse and then
     * to send to the related function
     * @throws SQLException If any error happens during a transaction with the
     * DB
     */
    private void afficherEquipes(String[] command) throws SQLException, IOException
    {
        if (command.length > 1)
        {
            logger.Log(" Cette commande ne prend pas de paramêtres.");
        }
        else
        {
            afficherEquipes();
        }
    }

    /**
     * Show to the console all Equipe in the DB
     *
     * @throws SQLException If any error happens during a transaction with the
     * DB
     */
    private void afficherEquipes() throws SQLException, IOException
    {
        ArrayList<Equipe> equipes = equipeHandler.getAll();
        logger.Log(Integer.toString(equipes.size()) + " Equipes trouves");
        for (Equipe equipe : equipes)
        {
            logger.Log(equipe.toString());
        }
    }

    /**
     * Parse command and call its related function if the command array is valid
     *
     * @param command String array containing the parameters to parse and then
     * to send to the related function
     * @throws SQLException If any error happens during a transaction with the
     * DB
     */
    private void supprimerEquipe(String[] commande) throws SQLException, IOException
    {
        if (equipeHandler.existe(commande[1]))
        {
            supprimerEquipe(commande[1]);
        }
        else
        {
            logger.Log("L'equipe: " + commande[1] + " n'existe pas.");
        }
    }

    /**
     * Delete the Equipe represented by equipeNom from the DB
     *
     * @param equipeNom The name of the Equipe to deletes
     * @throws SQLException If any error happens during a transaction with the
     * DB
     */
    private void supprimerEquipe(String equipeNom) throws SQLException, IOException
    {
        Equipe equipe = equipeHandler.getEquipe(equipeNom);
        if (equipe == null)
        {
            logger.Log("L'equipe: " + equipeNom + " n'existe pas.");
        }
        else
        {
            equipeHandler.supprimer(equipe.id);
            logger.Log("L'equipe: " + equipeNom + " a ete supprimee.");
        }
    }

    /**
     * Parse command and call its related function if the command array is valid
     *
     * @param command String array containing the parameters to parse and then
     * to send to the related function
     * @throws SQLException If any error happens during a transaction with the
     * DB
     */
    private void creerJoueur(String[] command) throws SQLException, IOException
    {
        if (command.length < 2)
        {
            //Explain error
        }
        else
        {
            boolean valide = true;
            String equipenom = "";
            int numero = -1;
            Date date = null;
            if (command.length == 6)
            {
                equipenom = command[3];
                numero = Integer.parseInt(command[4]);
                if (DateTimeHelper.isDateValid(command[5].toString()))
                {
                    date = java.sql.Date.valueOf(command[5]);
                }
                else
                {
                    logger.Log("la date n'est pas valide");
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
                creerJoueur(command[1], command[2], equipenom, numero, date);
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
    private void creerJoueur(String joueurNom, String joueurPrenom, String equipeNom, int numero, Date dateDebut) throws SQLException, IOException
    {
        Connexion cx = db.getConnexion();
        if (!joueurHandler.existeNom(joueurNom, joueurPrenom))
        {
            joueurHandler.inserer(joueurHandler.getLastID() + 1, joueurNom, joueurPrenom);
            if (numero != -1)
            {

                Equipe eq = equipeHandler.getEquipe(equipeNom);
                if (DateTimeHelper.isDateValid(dateDebut.toString()))
                {
                    faitpartieHandler.inserer(joueurHandler.getLastID(), eq.id, numero, dateDebut, null);
                }
                else
                {
                    logger.Log("la date n'est pas valide");
                }
            }
            cx.commit();
        }
        else
        {
            logger.Log("le joueur existe deja");
        }
    }

    /**
     * Parse command and call its related function if the command array is valid
     *
     * @param command String array containing the parameters to parse and then
     * to send to the related function
     * @throws SQLException If any error happens during a transaction with the
     * DB
     */
    private void afficherJoueursEquipe(String[] commande) throws SQLException, IOException
    {
        boolean EquipeNom = false;
        if (commande.length == 1)
        {
            afficherJoueursEquipe("", EquipeNom);
        }
        else if (commande.length == 2)
        {
            EquipeNom = true;
            afficherJoueursEquipe(commande[1], EquipeNom);
        }
    }

    private void afficherJoueursEquipe(String equipeNom, boolean EquipeNom) throws SQLException, IOException
    {
        if (!EquipeNom)
        {
            Map<Integer, Integer> FaitPartie = faitpartieHandler.getAll();
            for (Map.Entry<Integer, Integer> entry : FaitPartie.entrySet())
            {
                int JoueurId = entry.getKey();
                int EquipeId = entry.getValue();
                Equipe eq = equipeHandler.getEquipe(EquipeId);
                Joueur j = joueurHandler.getJoueur(JoueurId);
                logger.Log(j.toString() + "Nom Equipe : " + eq.nom);
            }
        }
        else
        {
            if (equipeHandler.existe(equipeNom))
            {
                Equipe eq = equipeHandler.getEquipe(equipeNom);
                ArrayList<Joueur> joueurList = faitpartieHandler.getJoueursByEquipe(eq.id);
                for (Joueur j : joueurList)
                {
                    logger.Log(j.toString());
                }
            }
            else
            {
                logger.Log("l'equipe n'existe pas");
            }
        }
    }

    /**
     * Parse command and call its related function if the command array is valid
     *
     * @param command String array containing the parameters to parse and then
     * to send to the related function
     * @throws SQLException If any error happens during a transaction with the
     * DB
     */
    private void supprimerJoueur(String[] commande) throws SQLException, IOException
    {
        if (commande.length == 3)
        {
            supprimerJoueur(commande[1], commande[2]);
        }
        else
        {
            //Gerer l'erreur
        }
    }

    private void supprimerJoueur(String joueurNom, String joueurPrenom) throws SQLException, IOException
    {
        if (joueurHandler.existeNom(joueurNom, joueurPrenom))
        {
            if (!lectureAuClavier)
            {
                joueurHandler.supprimer(joueurHandler.getJoueurId(joueurNom, joueurPrenom).id);
            }
            else
            {
                Joueur j = joueurHandler.getJoueurId(joueurNom, joueurPrenom);
                logger.Log(j.toString());
                logger.Log("Voulez vous le supprimer? Y/N");
                Scanner in = new Scanner(System.in);
                String val = in.nextLine().toUpperCase();
                if (val.contains("Y"))
                {
                    joueurHandler.supprimer(joueurHandler.getJoueurId(joueurNom, joueurPrenom).id);
                }

            }
        }
        else
        {
            logger.Log("Le joueur n'existe pas");
        }
    }

    /**
     * Parse command and call its related function if the command array is valid
     *
     * @param command String array containing the parameters to parse and then
     * to send to the related function
     * @throws SQLException If any error happens during a transaction with the
     * DB
     */
    private void creerMatch(String[] commande) throws SQLException
    {
        if (commande.length == 5)
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
        if ((equipeHandler.existe(equipeNomLocal) && equipeHandler.existe(equipeNomVisiteur)) && equipeNomLocal != equipeNomVisiteur)
        {
            Equipe eq = equipeHandler.getEquipe(equipeNomLocal);
            Equipe eq2 = equipeHandler.getEquipe(equipeNomVisiteur);
            if (matchHeure.length() < 6)
            {
                matchHeure = matchHeure + ":00";
            }
            matchHandler.inserer(matchHandler.getLastID() + 1, eq.id, eq2.id, eq.idTerrain, java.sql.Date.valueOf(matchDate), java.sql.Time.valueOf(matchHeure), 0, 0);
        }
    }

    /**
     * Parse command and call its related function if the command array is valid
     *
     * @param command String array containing the parameters to parse and then
     * to send to the related function
     * @throws SQLException If any error happens during a transaction with the
     * DB
     */
    private void creerArbitre(String[] commande) throws SQLException, IOException
    {
        if (commande.length == 3)
        {
            creerArbitre(commande[1], commande[2]);
        }
        else
        {
            //Gerer l'erreur
        }
    }

    private void creerArbitre(String nom, String prenom) throws SQLException, IOException
    {
        Arbitre arbitre = arbitreHandler.getArbitre(nom, prenom);
        if (arbitre == null)
        {
            arbitre = new Arbitre(arbitreHandler.getLastID() + 1, nom, prenom);
            arbitreHandler.inserer(arbitre.id, arbitre.nom, arbitre.prenom);
            logger.Log("Created Arbitre: " + nom + " ," + prenom);
        }
        else
        {
            logger.Log("L'Arbitre: " + nom + " ," + prenom + " existe deja.");
        }
    }

    /**
     * Parse command and call its related function if the command array is valid
     *
     * @param command String array containing the parameters to parse and then
     * to send to the related function
     * @throws SQLException If any error happens during a transaction with the
     * DB
     */
    private void afficherArbitres(String[] command) throws SQLException, IOException
    {
        if (command.length == 1)
        {
            afficherArbitres();
        }
        else
        {
            logger.Log("Cette commande ne prend pas de parametres.");
        }
    }

    private void afficherArbitres() throws SQLException, IOException
    {
        ArrayList<Arbitre> arbitres = arbitreHandler.getAll();
        logger.Log(Integer.toString(arbitres.size()) + " Arbitres trouves");
        for (Arbitre arbitre : arbitres)
        {
            logger.Log(arbitre.toString());
        }
    }

    /**
     * Parse command and call its related function if the command array is valid
     *
     * @param command String array containing the parameters to parse and then
     * to send to the related function
     * @throws SQLException If any error happens during a transaction with the
     * DB
     */
    private void arbitrerMatch(String[] commande) throws SQLException, Exception
    {
        if (commande.length == 7)
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
        Equipe eq = null;
        Equipe eq2 = null;
        int id = 0;
        int id2 = 0;

        if (matchHeure.length() < 6)
        {
            matchHeure = matchHeure + ":00";
        }
        Time time = java.sql.Time.valueOf(matchHeure);
        if (equipeHandler.existe(equipeNomLocal))
        {
            id = equipeHandler.getEquipe(equipeNomLocal).id;
        }
        if (equipeHandler.existe(equipeNomVisiteur))
        {
            id2 = equipeHandler.getEquipe(equipeNomVisiteur).id;
        }

        if (matchHandler.existeMatch(id, id2, date, time))
        {
            int matchID = matchHandler.getId(id, id2, java.sql.Date.valueOf(matchDate), java.sql.Time.valueOf(matchHeure));
            ArrayList<Arbitre> arbitreliste = arbitrerHandler.getArbitresByMatch(matchID);
            if (arbitreliste.size() < 4)
            {
                if (arbitreHandler.existe(ArbitreNom, ArbitrePrenom))
                {
                    arbitrerHandler.inserer(arbitreHandler.getId(ArbitreNom, ArbitrePrenom), matchID);
                }
            }
        }
        else
        {
            logger.Log("le match n'existe pas");
        }
    }

    /**
     * Parse command and call its related function if the command array is valid
     *
     * @param command String array containing the parameters to parse and then
     * to send to the related function
     * @throws SQLException If any error happens during a transaction with the
     * DB
     */
    private void entrerResultatMatch(String[] commande) throws SQLException, ParseException, IOException
    {
        if (commande.length == 7)
        {
            entrerResultatMatch(commande[1], commande[2], commande[3], commande[4], commande[5], commande[6]);
        }
        else
        {
            //Gerer l'erreur
        }
    }

    private void entrerResultatMatch(String matchDate, String matchHeure, String nomEquipeLocal, String nomEquipeVisiteur, String pointsLocal, String pointsVisiteur) throws SQLException, ParseException, IOException
    {
        boolean parametersValid = true;
        Equipe local = equipeHandler.getEquipe(nomEquipeLocal);
        Equipe visiteur = equipeHandler.getEquipe(nomEquipeVisiteur);
        if (local == null)
        {
            logger.Log("L'equipe: " + nomEquipeLocal + " n'existe pas.");
            parametersValid = false;
        }
        if (visiteur == null)
        {
            logger.Log("L'equipe: " + nomEquipeVisiteur + " n'existe pas.");
            parametersValid = false;
        }
        if (!DateTimeHelper.isDateValid(nomEquipeVisiteur))
        {
            logger.Log("La date: " + matchDate + " ne respecte pas le format " + DateTimeHelper.DATE_FORMAT);
            parametersValid = false;
        }
        if (!DateTimeHelper.isTimeValid(matchHeure))
        {
            logger.Log("Le temps: " + matchHeure + " ne respecte pas le format " + DateTimeHelper.TIME_FORMAT);
            parametersValid = false;
        }
        if (!isIntValid(pointsLocal))
        {
            logger.Log("Le parametre 'pointsLocal': " + pointsLocal + " est invalide. Il doit être de valeur 'int' et >= 0.");
            parametersValid = false;
        }
        if (!isIntValid(pointsVisiteur))
        {
            logger.Log("Le parametre 'pointsVisiteur': " + pointsVisiteur + " est invalide. Il doit être de valeur 'int' et >= 0.");
            parametersValid = false;
        }
        if (parametersValid)
        {
            Date date = DateTimeHelper.convertirDate(matchDate);
            Time time = DateTimeHelper.convertirTime(matchHeure);
            Match match = new Match(matchHandler.getLastID() + 1, local.id, visiteur.id, local.idTerrain, date, time, Integer.parseInt(pointsLocal), Integer.parseInt(pointsVisiteur));
        }
    }

    /**
     * Parse command and call its related function if the command array is valid
     *
     * @param command String array containing the parameters to parse and then
     * to send to the related function
     * @throws SQLException If any error happens during a transaction with the
     * DB
     */
    private void afficherResultatsDate(String[] commande) throws SQLException, ParseException, IOException
    {
        if (commande.length == 2)
        {
            afficherResultatsDate(commande[1]);
        }
        else
        {
            logger.Log("La commande ne prend qu'un seul parametre.");
        }
    }

    private void afficherResultatsDate(String aPartirDe) throws SQLException, ParseException, IOException
    {
        if (!DateTimeHelper.isDateValid(aPartirDe))
        {
            logger.Log("La date: " + aPartirDe + " ne respecte pas le format " + DateTimeHelper.DATE_FORMAT);
        }
        else
        {
            ArrayList<Match> matches = matchHandler.getMatchesByDate(DateTimeHelper.convertirDate(aPartirDe));
            logger.Log(Integer.toString(matches.size()) + " Matchs trouves");
            for (Match match : matches)
            {
                logger.Log(match.toString());
            }
        }
    }

    /**
     * Parse command and call its related function if the command array is valid
     *
     * @param command String array containing the parameters to parse and then
     * to send to the related function
     * @throws SQLException If any error happens during a transaction with the
     * DB
     */
    private void afficherResultats(String[] commande) throws SQLException, IOException
    {
        if (commande.length == 2)
        {
            afficherResultats(commande[1]);
        }
        else
        {
            //Gerer l'erreur
        }
    }

    /**
     * Show each Match that contains a certain Equipe as either visitor or local
     *
     * @param equipeNom
     * @throws SQLException
     */
    private void afficherResultats(String equipeNom) throws SQLException, IOException
    {
        Equipe equipe = equipeHandler.getEquipe(equipeNom);
        if (equipe == null)
        {
            logger.Log("L'equipe: " + equipeNom + " n'existe pas.");
        }
        else
        {
            ArrayList<Match> matches = matchHandler.getMatchesByEquipe(equipe.id);
            logger.Log(Integer.toString(matches.size()) + " Matchs trouves");
            for (Match match : matches)
            {
                logger.Log(match.toString());
            }
        }
    }

    /**
     * Method used to wait for the user to press a key
     *
     * @throws IOException In case there is an error while reading the input of
     * the user
     */
    private void pressAnyKeyToContinue() throws IOException
    {
        System.out.println("Press any key to continue...");
        System.in.read();
    }

    private boolean isIntValid(String integer)
    {
        boolean valid = false;
        try
        {
            if (Integer.parseInt(integer) > -1)
            {
                valid = true;
            }
        }
        catch (NumberFormatException ex)
        {
        }
        return valid;
    }
}
