import api.Api;
import app.App;
import bd.Bd;

public class Main {

    public static void main(String[] args) throws Exception{

        // partie temporaire a  renvoir en programmation RMI sur serveur distant
        Api api = new Api("jdbc:mysql://172.17.0.2:3306/miaam", "admin", "password");

        // lancement de l'app
        App app = new App(api);
        app.run();

        System.out.println("Fin du programme");
    }
}
