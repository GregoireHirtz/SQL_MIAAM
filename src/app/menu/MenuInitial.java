package app.menu;

import app.App;

public class MenuInitial extends Menu {

    public void run(App app){
        System.out.println("Menu initial");
        System.out.println("1. Connexion");
        System.out.println("2. Quitter");
        int choix = saisieInt("> ");

        switch (choix){
            case 1:
                app.menu = MENU_CONNEXION;
                break;
            case 2:
                app.running = false;
                break;
            default:
                System.out.println("Choix invalide");
                break;
        }
    }

}
