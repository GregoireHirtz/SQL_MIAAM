package activeRecord;

import bd.Bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Affecter implements ActiveRecord{

    private int numtab;
    private Date dataff;
    private int numserv;

    public Affecter(int numtab, Date dataff, int numserv) {
        if (numtab < 0 || dataff == null || numserv < 0) {
            throw new IllegalArgumentException("Les paramètres ne peuvent pas être null");
        }
        this.numtab = numtab;
        this.dataff = dataff;
        this.numserv = numserv;
    }


    @Override
    public void save(Bd bd) {
        if (bd == null) throw new IllegalArgumentException("La connexion ne peut pas être null");

        String sql = "INSERT INTO affecter (numtab, dataff, numserv) VALUES (?, ?, ?)";
        try{
            bd.executeQuery(sql, this.numtab, this.dataff, this.numserv);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static List<Affecter> getAll(Bd bd){
        if (bd == null) throw new IllegalArgumentException("La connexion ne peut pas être null");

        try{
            ResultSet rs = bd.executeQuery("SELECT * FROM affecter");
            ArrayList<Affecter> affectations = new ArrayList<>();
            while (rs.next()){
                affectations.add(new Affecter(rs.getInt("numtab"), rs.getDate("dataff"), rs.getInt("numserv")));
            }
            return affectations;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Affecter findByTablDate(Bd bd, int numtab, Date dataff){
        if (bd == null) throw new IllegalArgumentException("La connexion ne peut pas être null");

        try{
            ResultSet rs = bd.executeQuery("SELECT * FROM affecter WHERE numtab = ? AND dataff = ?", numtab, dataff);
            if (rs.next()){
                return new Affecter(rs.getInt("numtab"), rs.getDate("dataff"), rs.getInt("numserv"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public String toString(){
        String str = "Table: " + this.numtab;
        str += "\nDate: " + this.dataff;
        str += "\nServeur: " + this.numserv;
        str += "\n";
        return str;
    }
}
