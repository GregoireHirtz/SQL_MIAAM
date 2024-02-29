package app;

import api.Api;
import app.menu.*;
import bd.Bd;

public class App {

    public int menu = 1;
    public boolean running = true;
    public Api api;
    public Bd bd;

    public App(Api api){
        this.api = api;
    }


    public void run(){
        while (running){
            switch (menu){
                case 0:
                    new MenuInitial().run(this);
                    break;
                case 1:
                    new MenuConnexion().run(this);
                    break;
                case 2:
                    new MenuAccueil().run(this);
                    break;
                case 3:
                    new MenuAccueilGestion().run(this);
                    break;
                default:
                    menu = 0;
            }
            System.out.println(" ");
        }
    }
}
