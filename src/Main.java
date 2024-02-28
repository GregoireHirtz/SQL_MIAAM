import bd.Bd;

public class Main {

    public static void main(String[] args) {

        Bd bd;
        try{
            bd = new Bd("jdbc:mysql://172.20.0.2:3306/miaam", "admin", "password");
        }catch (Exception e){
            e.printStackTrace();
            return;
        }


        App app = new App(bd);
        app.start();

        System.out.println("Fin du programme");
    }
}
