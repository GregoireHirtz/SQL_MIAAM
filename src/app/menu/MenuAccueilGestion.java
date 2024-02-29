package app.menu;

import app.App;

public class MenuAccueilGestion extends Menu {

    public void run(App app){
        System.out.println("Menu initial");
        System.out.println("1. Lister tables libres");
        System.out.println("2. Réserver une table");
        System.out.println("3. Lister plats disponibles");
        System.out.println("4. Commander des plats");
        System.out.println("5. Consulter les affectations");
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

            case 9:
                app.running = false;
                break;
            default:
                System.out.println("Choix invalide");
                break;
        }
    }

    private void consulterAffectations(App app){
        System.out.println("Consulter les affectations");
    }
}
