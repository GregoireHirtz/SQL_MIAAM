package app.menu;

import activeRecord.Affecter;
import activeRecord.Serveur;
import activeRecord.Tabl;
import app.App;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MenuAccueilGestion extends Menu {

    /**
     * Menu d'un serveur gestionnaire
     * @param app
     */
    public void run(App app){
        System.out.println("Menu initial");
        System.out.println("1. Lister tables libres");
        System.out.println("2. Réserver une table");
        System.out.println("3. Lister plats disponibles");
        System.out.println("4. Commander des plats");
        System.out.println("5. Consulter les affectations");
        System.out.println("6. Affecter un serveur");
        System.out.println("7. Payer une reservation");
        System.out.println("9. Quitter");
        int choix = saisieInt("> ");

        switch (choix){
            case 1:
                listerTablesLibres(app);
                break;

            case 2:
                reserverTable(app);
                break;

            case 3:
                listerPlatsDispo(app);
                break;

            case 4:
                commanderPlats(app);
                break;

            case 5:
                consulterAffectations(app);
                break;

            case 6:
                affecterServeur(app);
                break;

            case 7:
                payerReservation(app);
                break;

            case 9:
                app.running = false;
                break;
            default:
                System.out.println("Choix invalide");
                break;
        }
    }

    /**
     * Consulter les affectations des serveurs
     * @param app
     */
    private void consulterAffectations(App app){
        System.out.println("Consulter les affectations des serveurs");
        try{
            List<Affecter> affectations = Affecter.getAll(app.bd);
            for (Affecter affectation : affectations){
                System.out.println(affectation);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Affecter un serveur à une table
     * @param app
     */
    private void affecterServeur(App app){
        System.out.println("Affecter un serveur à une table");

        try{
            // lock tables
            app.bd.setAutoCommit(false);
            app.bd.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            app.bd.execute("LOCK TABLES serveur WRITE, tabl WRITE, affecter WRITE");

            // recuperer le serveur
            Serveur.getAll(app.bd).forEach(System.out::println);
            int numserv = saisieInt("Numéro du serveur: ");
            Serveur serveur = Serveur.findByNum(app.bd, numserv);
            if (serveur == null){
                System.out.println("Serveur inexistant");
                return;
            }

            // recuperer la table
            Tabl.getAll(app.bd).forEach(System.out::println);
            int numtabl = saisieInt("Numéro de la table: ");
            Tabl tabl = Tabl.findByNum(app.bd, numtabl);
            if (tabl == null){
                System.out.println("Table inexistante");
                return;
            }

            // recuperer la date d'affectation
            String dateStr = saisieString("Date (aaaa-mm-jj): ");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date;
            try{
                date = dateFormat.parse(dateStr);
            }catch (Exception e){
                System.out.println("Date invalide");
                return;
            }

            // verifier table pas deja affectee a cette date
            if (Affecter.findByTablDate(app.bd, numtabl, date) != null){
                System.out.println("Table déjà affectée à cette date");
                return;
            }

            // affecter le serveur
            Affecter affecter = new Affecter(numtabl, date, numserv);
            affecter.save(app.bd);
            app.bd.commit();
            System.out.println("Affectation effectuée");


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

    private void payerReservation(App app) {

        // recuperer le numero de reservation
        int numres = saisieInt("Numéro de réservation: ");
        try {
            // recuperer la reservation
            activeRecord.Reservation reservation = activeRecord.Reservation.findByNum(app.bd, numres);
            if (reservation == null) {
                System.out.println("Réservation inexistante");
                return;
            }

            app.bd.execute("LOCK TABLES reservation WRITE, commande WRITE, plat WRITE");
            app.bd.setAutoCommit(false);
            app.bd.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            // verifier si la reservation est deja payee
            System.out.println(reservation.getDatpaie());
            if (reservation.getDatpaie() != null) {
                System.out.println("Réservation déjà payée");
                return;
            }

            // mettre a jour la somme a payer
            reservation.calculMontant(app.bd);
            System.out.println(reservation);

            // demander mode de paiement
            String mode = saisieString("Mode de paiement (1. Espece, 2. Cheque, 3. Carte): ");
            if (!mode.equals("1") && !mode.equals("2") && !mode.equals("3")) {
                System.out.println("Mode de paiement invalide");
                return;
            }
            switch (mode) {
                case "1":
                    reservation.setModepaie("Espèces");
                    break;
                case "2":
                    reservation.setModepaie("Chèque");
                    break;
                case "3":
                    reservation.setModepaie("Carte");
                    break;
            }

            // mettre a jour la date de paiement
            reservation.setDatpaie(new Date());

            // sauvegarder les modifications
            reservation.save(app.bd);
            app.bd.commit();

            System.out.println("Paiement effectué");

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                app.bd.execute("UNLOCK TABLES");
                app.bd.setAutoCommit(true);
            }catch (SQLException e){}
        }
    }
}
