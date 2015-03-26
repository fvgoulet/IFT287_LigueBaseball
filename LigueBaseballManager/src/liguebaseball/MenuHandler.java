/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;
import java.io.*;
import java.util.StringTokenizer;
import java.text.*;
import java.sql.SQLException;

import java.sql.SQLException;

/**
 *
 * @author fvgou_000
 */
public class MenuHandler 
{
    DBHandler db;
    EquipeHandler equipeHandler;
    private static boolean lectureAuClavier;
    InputStream sourceTransaction;
    ArbitreHandler arbitreHandler;

    public MenuHandler(String[] args) throws Exception
    {
        db = new DBHandler(args[0], args[1], args[2]);
        equipeHandler = new EquipeHandler(db.getConnexion());
        // ouverture du fichier de transactions
        // s'il est spécifié comme argument
        lectureAuClavier = true;
        sourceTransaction = System.in;
        if (args[3] != "")
           {
           sourceTransaction = new FileInputStream(args[3]);
           lectureAuClavier = false;
         }
    }
    
 void Start() throws BaseballException
    {
            try {

                BufferedReader reader =
                    new BufferedReader(
                        new InputStreamReader(sourceTransaction));

                traiterTransactions(reader);
                }
            catch (Exception e)
                {
                e.printStackTrace(System.out);
                }
            finally
                {
                //close connection
                }
    }

            /**
              * Traitement des transactions de baseball
              */
            static void traiterTransactions(BufferedReader reader)
                throws Exception
            {
            afficherAide();
            String transaction = lireTransaction(reader);
            while (!finTransaction(transaction))
                {
                /* découpage de la transaction en mots*/
                StringTokenizer tokenizer = new StringTokenizer(transaction, " ");
                if (tokenizer.hasMoreTokens())
                    executerTransaction(tokenizer);
                transaction = lireTransaction(reader);
                }
            }

            /**
              * Lecture d'une transaction
              */
            static String lireTransaction(BufferedReader reader)
                throws IOException
            {
            System.out.print("> ");
            String transaction = reader.readLine();
            /* echo si lecture dans un fichier */
            if (!lectureAuClavier)
                System.out.println(transaction);
            return transaction;
            }

            /**
              * Décodage et traitement d'une transaction
              */
            static void executerTransaction(StringTokenizer tokenizer)
                throws Exception
            {
                String command = tokenizer.nextToken();

                /* ******************* */
                /*         HELP        */
                /* ******************* */
                if ("aide".startsWith(command))
                    afficherAide();
                /* ******************* */
                /* Creer equipe            */
                /* ******************* */
                else if ("creerEquipe".startsWith(command))
                    System.out.println("CreerEquipe");
                  
                /* ******************* */
                /* afficherEquipes              */
                /* ******************* */
                else if ("afficherEquipes".startsWith(command))
                    System.out.println("afficherEquipes");
                /* ********************* */
                /* supprimerEquipe                */
                /* ********************* */
                else if ("supprimerEquipe".startsWith(command))
                    System.out.println("supprimerEquipe");
                /* ******************* */
                /* creerJoueur          */
                /* ******************* */
                else if ("creerJoueur".startsWith(command))
                    System.out.println("creerJoueur");
                /* ********************* */
                /* afficherJoueursEquipe             */
                /* ********************* */
                else if ("afficherJoueursEquipe".startsWith(command))
                     System.out.println("afficherJoueursEquipe");
                /* ********************* */
                /* SupprimerJoueur              */
                /* ********************* */
                else if ("supprimerJoueur".startsWith(command))
                  System.out.println("supprimerJoueur");
                /* ******************* */
                /* Creer match         */
                /* ******************* */
                else if ("creerMatch".startsWith(command))
                     System.out.println("creerMatch");
                /* ******************* */
                /* creerArbitre            */
                /* ******************* */
                else if ("creerArbitre".startsWith(command))
                    System.out.println("creerArbitre");
                /* ******************* */
                /* afficherArbitres */
                /* ******************* */
                else if ("afficherArbitres".startsWith(command))
                      System.out.println("afficherArbitres");
                /* ******************* */
                /* arbitrerMatch  */
                /* ******************* */
                else if ("arbitrerMatch ".startsWith(command))
                     System.out.println("arbitrerMatch");
                /* *********************           */
                /* entrerResultatMatch   */
                /* *********************           */
                else if ("entrerResultatMatch".startsWith(command))
                    System.out.println("entrerResultatMatch");
                /* *********************           */
                /* afficher resultats date  */
                /* *********************           */
                else if ("afficherResultatDate".startsWith(command))
                    System.out.println("afficherResultatDate");
                /* *********************           */
                /* afficher resultats   */
                /* *********************           */
                else if ("afficherResultats ".startsWith(command))
                     System.out.println("afficherResultats");
                /* *********************           */
                /* commentaire : ligne débutant par --   */
                /* *********************           */
                else if ("--".startsWith(command))
                    {}// ne rien faire; c'est un commentaire
                /* ***********************   */
                /* TRANSACTION NON RECONNUEE */
                /* ***********************   */
                else
                    System.out.println("  Transactions non reconnue.  Essayer \"aide\"");
                }
    
    
            /** Affiche le menu des transactions acceptées par le système */
        static void afficherAide()
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
         * Vérifie si la fin du traitement des transactions est
         * atteinte.
         */
        static boolean finTransaction(String transaction)
        {
        /* fin de fichier atteinte */
        if (transaction == null)
            return true;

        StringTokenizer tokenizer = new StringTokenizer(transaction, " ");

        /* ligne ne contenant que des espaces */
        if (!tokenizer.hasMoreTokens())
            return false;

        /* commande "exit" */
        String commande = tokenizer.nextToken();
        if (commande.equals("exit"))
            return true;
        else
            return false;
        }

        /** lecture d'une chaîne de caractères de la transaction entrée à l'écran */
        static String readString(StringTokenizer tokenizer)
        throws BaseballException
        {
        if (tokenizer.hasMoreElements())
            return tokenizer.nextToken();
        else
            throw new BaseballException("autre paramètre attendu");
        }

        /**
          * lecture d'un int java de la transaction entrée à l'écran
          */
        static int readInt(StringTokenizer tokenizer)
        throws BaseballException
        {
        if (tokenizer.hasMoreElements())
            {
            String token = tokenizer.nextToken();
            try
                {
                return Integer.valueOf(token).intValue();
                }
            catch (NumberFormatException e)
                {
                throw new BaseballException("Nombre attendu à la place de \"" +
                                                token + "\"");
                }
        }
        else
            throw new BaseballException("autre paramètre attendu");
        }

        /**
          * lecture d'un long java de la transaction entrée à l'écran
          */
        static long readLong(StringTokenizer tokenizer)
        throws BaseballException
        {
        if (tokenizer.hasMoreElements())
            {
            String token = tokenizer.nextToken();
            try
                {
                return Long.valueOf(token).longValue();
                }
            catch (NumberFormatException e)
                {
                throw new BaseballException("Nombre attendu à la place de \"" +
                                                    token + "\"");
                }
            }
        else
            throw new BaseballException("autre paramètre attendu");
        }

        /**
          * lecture d'une date en format YYYY-MM-DD
          */
        static String readDate(StringTokenizer tokenizer)
        throws BaseballException
        {
        if (tokenizer.hasMoreElements())
            {
            String token = tokenizer.nextToken();
            try
                {
                FormatDate.convertirDate(token);
                return token;
                }
            catch (ParseException e)
                {
                throw new BaseballException(
                  "Date en format YYYY-MM-DD attendue à la place  de \"" +
                  token + "\"");
                }
            }
        else
            throw new BaseballException("autre paramètre attendu");
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
