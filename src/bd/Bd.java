package bd;

import java.sql.*;

public class Bd {

    private String url, username, password;
    private Connection connection;

    public Bd(String url, String username, String password) throws SQLException {
        if (url==null || username==null || password==null) throw new NullPointerException("aucun paramètre ne doit être null");
        this.url = url;
        this.username = username;
        this.password = password;
        this.connection = DriverManager.getConnection(url, username, password);
    }

    public Object[] getConnection(String mail, String password) throws SQLException {
        if (mail==null || password==null) throw new NullPointerException("aucun paramètre ne doit être null");

        String grade = verifLogin(mail, password);
        if (grade == null) return null;
        switch (grade){
            case "gestionnaire":
                return new Object[]{DriverManager.getConnection(url, username, this.password), grade};
            case "serveur":
                return new Object[]{DriverManager.getConnection(url, username, this.password), grade};
            default:
                System.out.println("Erreur de grade");
                return null;
        }
    }


    /**
     * Vérifie si le login est correct, et retourne le grade de l'utilisateur
     * @param mail
     * @param password
     * @return
     * @throws SQLException
     */
    private String verifLogin(String mail, String password) throws SQLException{
        String sql = "SELECT * FROM serveur WHERE email = ? AND passwd = ?";
        PreparedStatement prep = connection.prepareStatement(sql);
        prep.setString(1, mail);
        prep.setString(2, password);
        ResultSet rs = prep.executeQuery();

        if (rs.next()){
            return rs.getString("grade");
        }
        return null;
    }

}
