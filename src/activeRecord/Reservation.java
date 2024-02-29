package activeRecord;

import bd.Bd;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reservation implements ActiveRecord{

    private int numres;
    private int numtab;
    private Date datres;
    private int nbpers;
    private Date datpaie;
    private String modpaie;
    private Double montcom;

    public Reservation(int numtab, Date datres, int nbpers) {
        if (numtab < 0 || datres == null || nbpers < 0) {
            throw new IllegalArgumentException("Les paramètres ne peuvent pas être null");
        }

        this.numres = 0;
        this.numtab = numtab;
        this.datres = datres;
        this.nbpers = nbpers;
        this.datpaie = null;
        this.modpaie = null;
        this.montcom = null;
    }

    public Reservation(int numres, int numtab, Date datres, int nbpers, Date datpaie, String modpaie, double montcom){
        if (numres < 0 || numtab < 0 || datres == null || nbpers < 0) {
            throw new IllegalArgumentException("Les paramètres ne peuvent pas être null");
        }

        this.numres = numres;
        this.numtab = numtab;
        this.datres = datres;
        this.nbpers = nbpers;
        this.datpaie = datpaie;
        this.modpaie = modpaie;
        this.montcom = montcom;
    }

    @Override
    public void save(Bd bd) {
        if (bd == null) throw new IllegalArgumentException("La connexion ne peut pas être null");

        if (this.numres == 0) {
            String sql = "INSERT INTO reservation (numtab, datres, nbpers, datpaie, modpaie, montcom) VALUES (?, ?, ?, ?, ?, ?)";
            try{
                bd.executeQuery(sql, this.numtab, this.datres, this.nbpers, this.datpaie, this.modpaie, this.montcom);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else{
            String sql = "UPDATE reservation SET numtab = ?, datres = ?, nbpers = ?, datpaie = ?, modpaie = ?, montcom = ? WHERE numres = ?";
            try{
                bd.executeQuery(sql, this.numtab, this.datres, this.nbpers, this.datpaie, this.modpaie, this.montcom, this.numres);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static Reservation findByNum(Bd bd, int numres){
        if (bd == null) throw new IllegalArgumentException("La connexion ne peut pas être null");

        String sql = "SELECT * FROM reservation WHERE numres = ?";
        try{
            ResultSet rs = bd.executeQuery(sql, numres);
            if (rs.next()){
                return new Reservation(rs.getInt("numres"), rs.getInt("numtab"), rs.getDate("datres"), rs.getInt("nbpers"), rs.getDate("datpaie"), rs.getString("modpaie"), rs.getDouble("montcom"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Date  getDatpaie() {
        return datpaie;
    }

    public int getNumres() {
        return numres;
    }

    public String toString(){
        return "Réservation n°" + this.numres + " pour " + this.nbpers + " personnes";
    }
}
