package app.menu;

import app.App;

public class MenuAccueil extends Menu {

    @Override
    public void run(App app){
        System.out.println("Menu initial");
        System.out.println("1. Lister tables libres");
        System.out.println("9. Quitter");
        int choix = saisieInt("> ");

        switch (choix){
            case 1:
                listerTablesLibres();
                break;
            case 9:
                app.running = false;
                break;
            default:
                System.out.println("Choix invalide");
                break;
        }
    }


    private void listerTablesLibres(){
        System.out.println("Liste des tables libres");

    }
}
