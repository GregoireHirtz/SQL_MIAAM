package app.menu;

import activeRecord.Serveur;
import app.App;
import bd.Bd;

public class MenuConnexion extends Menu {

    @Override
    public void run(App app){
        System.out.println("Menu connexion");
        //String email = saisieString("Email: ");
        //String mdp = saisieString("Mot de passe: ");
        String email = "user1@mail.com";
        String mdp = "password";

        app.bd = app.api.getBd(email, mdp);

        if (app.bd != null){
            app.menu = MENU_ACCUEIL;
        } else {
            app.menu = MENU_INITIAL;
        }
    }

}
