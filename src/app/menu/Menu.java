package app.menu;

import app.App;

import java.util.Scanner;

public abstract class Menu {

    public static final int MENU_INITIAL = 0;
    public static final int MENU_CONNEXION = 1;
    public static final int MENU_ACCUEIL = 2;

    private Scanner sc = new Scanner(System.in);


    public abstract void run(App app);

    public int saisieInt(String message){
        System.out.print(message);
        try{
            return sc.nextInt();
        }catch (Exception e){
            return -1;
        }

    }

    public String saisieString(String message){
        System.out.print(message);
        return sc.next();
    }
}
