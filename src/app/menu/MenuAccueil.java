package app.menu;

import activeRecord.Commande;
import activeRecord.Plat;
import activeRecord.Reservation;
import activeRecord.Tabl;
import app.App;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MenuAccueil extends Menu {

    /**
     * Liste les tables libres
     * @param app
     */
    public void run(App app){
        System.out.println("Menu initial");
        System.out.println("1. Lister tables libres");
        System.out.println("2. Réserver une table");
        System.out.println("3. Lister plats disponibles");
        System.out.println("4. Commander des plats");
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

            case 9:
                app.running = false;
                break;
            default:
                System.out.println("Choix invalide");
                break;
        }
    }
}
