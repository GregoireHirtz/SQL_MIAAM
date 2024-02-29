package app.menu;

import activeRecord.Commande;
import activeRecord.Plat;
import activeRecord.Reservation;
import activeRecord.Tabl;
import app.App;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Menu {

    public static final int MENU_INITIAL = 0;
    public static final int MENU_CONNEXION = 1;
    public static final int MENU_ACCUEIL = 2;
    public static final int MENU_ACCUEIL_GESTION = 3;

    private Scanner sc = new Scanner(System.in);

    /**
     * Affiche un message et demande à l'utilisateur de saisir un entier
     * @param message
     * @return
     */
    public int saisieInt(String message){
        System.out.print(message);
        try{
            return sc.nextInt();
        }catch (Exception e){
            return -1;
        }

    }

    /**
     * Affiche un message et demande à l'utilisateur de saisir une chaine de caractères
     * @param message
     * @return
     */
    public String saisieString(String message){
        System.out.print(message);
        return sc.next();
    }

    /**
     * Liste les tables libre à une date saisie par l'utilisateur
     * @param app
     */
    protected void listerTablesLibres(App app){
        System.out.println("Liste des tables libres");

        String dateStr = saisieString("Date (aaaa-mm-jj): ");
        String heureStr = saisieString("Heure (hh:mm): ");
        dateStr = dateStr+ " " + heureStr+":00";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date;
        try{
            date = dateFormat.parse(dateStr);
        }catch (Exception e){
            System.out.println("Date invalide");
            return;
        }

        try{
            List<Tabl> tabls = Tabl.getTableLibre(app.bd, date);
            for (Tabl tabl : tabls){
                System.out.println(tabl);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Permet de réserver une table à une date et heure saisie par l'utilisateur
     * @param app
     */
    protected void reserverTable(App app){

        System.out.println("Réservation d'une table");
        System.out.println("Pour quelle date et heure voulez-vous réserver une table?");
        String dateStr = saisieString("Date (aaaa-mm-jj): ");
        String heureStr = saisieString("Heure (hh:mm): ");
        dateStr = dateStr+ " " + heureStr+":00";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date;
        try{
            date = dateFormat.parse(dateStr);
        }catch (Exception e){
            System.out.println("Date invalide");
            return;
        }

        try{

            app.bd.execute("LOCK TABLE tabl WRITE, reservation WRITE");

            app.bd.setAutoCommit(false);
            app.bd.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            List<Tabl> tabls = Tabl.getTableLibre(app.bd, date);
            for (Tabl tabl : tabls){
                System.out.println(tabl);
            }

            int idTabl = saisieInt("Id de la table: ");
            Tabl tabl = Tabl.findByNum(app.bd, idTabl);
            if (tabl == null){
                System.out.println("Table inexistante");
                return;
            }
            int nbPersonnes = saisieInt("Nombre de personnes: ");
            if (nbPersonnes > tabl.getNbplace()){
                System.out.println("La table ne peut pas accueillir autant de personnes");
                return;
            }

            Reservation reservation = new Reservation(idTabl, date, nbPersonnes);
            reservation.save(app.bd);
            System.out.println("Réservation effectuée");
            System.out.println("Numéro de réservation: " + reservation.getNumres());
            app.bd.commit();

        }catch (SQLException e){
            e.printStackTrace();
            app.bd.rollback();
        }finally {
            try {
                app.bd.execute("UNLOCK TABLES");
                app.bd.setAutoCommit(true);
            }catch (SQLException e){}
        }


    }

    /**
     * Liste les plats disponibles
     * @param app
     */
    protected void listerPlatsDispo(App app){
        System.out.println("Liste des plats disponibles");
        try{
            List<Plat> plats = Plat.getAll(app.bd);
            for (Plat plat : plats){
                if (plat.getQtservie() > 0){
                    System.out.println(plat);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Permet de commander des plats pour une réservation
     * @param app
     */
    protected void commanderPlats(App app){
        try{
            app.bd.execute("LOCK TABLE plat WRITE, commande WRITE, reservation WRITE");

            app.bd.setAutoCommit(false);
            app.bd.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            int numres = saisieInt("Numéro de réservation: ");
            Reservation reservation = Reservation.findByNum(app.bd, numres);
            if (reservation == null){
                System.out.println("Réservation inexistante");
                return;
            }

            if (reservation.getDatpaie() != null){
                System.out.println("Réservation déjà payée");
                return;
            }

            System.out.println("Commander un plat");
            listerPlatsDispo(app);

            boolean choisir = true;
            while (choisir){
                int idPlat = saisieInt("Id du plat: ");
                if (idPlat == 0){
                    choisir = false;
                    break;
                }

                Plat plat = Plat.findByNum(app.bd, idPlat);
                if (plat == null){
                    System.out.println("Plat inexistant");
                    break;
                }
                int qte = saisieInt("Quantité: ");
                if (qte > plat.getQtservie()){
                    System.out.println("Quantité insuffisante");
                    break;
                }

                Commande commande = Commande.findByNums(app.bd, numres, idPlat);
                if (commande == null) {
                    commande = new Commande(numres, idPlat, qte);
                }else{
                    commande.setQuantite(commande.getQuantite()+qte);
                }
                commande.save(app.bd);
                plat.setQtservie(plat.getQtservie()-qte);
                plat.save(app.bd);
                System.out.println("!! 0 pour terminer la commande");
            }

        }catch (SQLException e) {
            e.printStackTrace();
            app.bd.rollback();
        }finally {
            try {
                app.bd.commit();
                app.bd.execute("UNLOCK TABLES");
                app.bd.setAutoCommit(true);
            }catch (SQLException e){}
        }
    }
}
