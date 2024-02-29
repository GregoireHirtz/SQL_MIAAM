package app.menu;

import activeRecord.Serveur;
import app.App;
import bd.Bd;

public class MenuConnexion extends Menu {

    public void run(App app){
        System.out.println("Menu connexion");
        //String email = saisieString("Email: ");
        //String mdp = saisieString("Mot de passe: ");
        String email = "user1@mail.com";
        String mdp = "password";

        app.bd = app.api.getBd(email, mdp);

        if (app.bd != null){
            Serveur serveur = Serveur.getByEmail(app.bd, email);
            if (serveur.getGrade().compareTo("gestionnaire") == 0){
                app.menu = MENU_ACCUEIL_GESTION;
            } else {
                app.menu = MENU_ACCUEIL;
            }
        } else {
            app.menu = MENU_INITIAL;
        }
        System.out.println("Menu: " + app.menu);
    }

}
