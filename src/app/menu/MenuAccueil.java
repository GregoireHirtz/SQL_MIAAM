package app.menu;

import activeRecord.Reservation;
import activeRecord.Tabl;
import app.App;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MenuAccueil extends Menu {

    @Override
    public void run(App app){
        System.out.println("Menu initial");
        System.out.println("1. Lister tables libres");
        System.out.println("2. Réserver une table");
        System.out.println("9. Quitter");
        int choix = saisieInt("> ");

        switch (choix){
            case 1:
                listerTablesLibres(app);
                break;

            case 2:
                reserverTable(app);
                break;

            case 9:
                app.running = false;
                break;
            default:
                System.out.println("Choix invalide");
                break;
        }
    }


    private void listerTablesLibres(App app){
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

    private void reserverTable(App app){

    }
}
