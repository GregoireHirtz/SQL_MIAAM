package api;

import bd.Bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Api {

    private Connection connection;

    public Api(String url, String user, String password){

        try{
            this.connection = DriverManager.getConnection(url, user, password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Recupere la base de donnee correspondant a l'email et au mot de passe
     * @param email
     * @param password
     */
    public Bd getBd(String email, String password) {
        String query = "SELECT grade FROM serveur WHERE email = ? AND passwd = ?";

        PreparedStatement stat;
        ResultSet rs;
        Bd bd = null;
        try {
            stat = connection.prepareStatement(query);
            stat.setString(1, email);
            stat.setString(2, password);
            rs = stat.executeQuery();

            if (rs.next()) {
                // recuperer le grade
                String grade = rs.getString("grade");
                switch (grade) {
                    case "serveur":
                        bd = new Bd("jdbc:mysql://172.17.0.2:3306/miaam", "admin", "password");
                        break;
                    case "gestionnaire":
                        bd = new Bd("jdbc:mysql://172.17.0.2:3306/miaam", "admin", "password");
                        break;
                    default:
                        System.out.println("Grade inconnu");
                }
            } else {
                System.out.println("Email/Mot de passe incorrect");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bd;
    }
}
