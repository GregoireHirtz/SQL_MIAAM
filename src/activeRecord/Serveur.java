package activeRecord;

import bd.Bd;
import java.sql.SQLException;

public class Serveur implements ActiveRecord{

    private int numserv;
    private String email;
    private String passwd;
    private String nomserv;
    private String grade;


    public Serveur(String email, String passwd, String nomserv, String grade) {
        if (email == null || passwd == null || nomserv == null || grade == null) {
            throw new IllegalArgumentException("Les paramètres ne peuvent pas être null");
        }
        this.numserv = 0;
        this.email = email;
        this.passwd = passwd;
        this.nomserv = nomserv;
        this.grade = grade;
    }


    @Override
    public void save(Bd bd){
        if (bd == null) throw new IllegalArgumentException("La connexion ne peut pas être null");

        if (this.numserv == 0) {
            String sql = "INSERT INTO serveur (email, passwd, nomserv, grade) VALUES (?, ?, ?, ?)";
            try{
                bd.executeQuery(sql, this.email, this.passwd, this.nomserv, this.grade);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        else{
            String sql = "UPDATE serveur SET email = ?, passwd = ?, nomserv = ?, grade = ? WHERE numserv = ?";
            try{
                bd.executeQuery(sql, this.email, this.passwd, this.nomserv, this.grade, this.numserv);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public static Serveur findByNum(Bd bd, int num) throws SQLException {
        if (num <= 0) throw new IllegalArgumentException("Le numéro de serveur doit être positif");


        String sql = "SELECT * FROM serveur WHERE numserv = ?";


        return null;
    }
}
