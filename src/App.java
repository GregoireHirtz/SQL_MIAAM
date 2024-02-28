import bd.Bd;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class App {

    public static final int MENU_INITIAL = 0;
    public static final int MENU_CONNEXION = 1;
    public static final int MENU_ACCUEIL = 2;

    private Scanner sc;
    private int menu;
    private Connection connection = null;
    private String grade;

    private Bd bd;

    public App(Bd bd) {
        this.sc = new Scanner(System.in);
        this.menu = 1;

        this.bd = bd;
    }

    public void start() {
        boolean run = true;
        while (run) {
            switch (menu){

                case MENU_INITIAL -> {

                    if (connection != null) {
                        menu = MENU_ACCUEIL;
                        break;
                    }

                    System.out.println("Menu initial");
                    System.out.println("1. Se connecter");
                    System.out.println("2. Quitter");

                    int choix = saisieInt("> ");
                    if (choix == 1) {
                        menu = MENU_CONNEXION;
                    } else if (choix == 2) {
                        run = false;
                    }
                }


                case MENU_CONNEXION -> {

                    if (connection != null) {
                        menu = MENU_ACCUEIL;
                        break;
                    }

                    System.out.println("Menu connexion");
                    //String mail = saisieString("Mail: ");
                    //String password = saisieString("Password: ");
                    String mail = "user1@mail.com";
                    String password = "password";
                    try{
                        Object[] o = bd.getConnection(mail, password);
                        this.connection = (Connection)o[0];
                        this.grade = (String)o[1];
                    }catch (SQLException e){
                        System.out.println("Erreur de connexion");
                        e.printStackTrace();
                    }
                }


                case MENU_ACCUEIL -> {
                    System.out.println("Menu accueil");
                    System.out.println("1: Consulter tables dispo");
                    System.out.println("2: Réserver une table");
                    System.out.println("9: Quitter");
                    int choix = saisieInt("> ");

                    switch (choix){
                        case 1:
                            consulterTables();
                            break;

                        case 2:
                            System.out.println("Réserver une table");
                            break;

                        case 9:
                            run = false;
                    }
                }
            }

            System.out.println(" ");
        }
    }

    private int saisieInt(String message){
        if (message == null) message = "";
        System.out.print(message);
        return sc.nextInt();
    }



    private void consulterTables(){
        System.out.println("Consulter les tables");
    }
}
