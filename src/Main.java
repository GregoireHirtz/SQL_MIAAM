import api.Api;
import app.App;
import bd.Bd;

public class Main {

    public static void main(String[] args) throws Exception{

        App app = new App(new Api("jdbc:mysql://172.17.0.2:3306/miaam", "admin", "password"));
        app.run();

        System.out.println("Fin du programme");
    }
}
