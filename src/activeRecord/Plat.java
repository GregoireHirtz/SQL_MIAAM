package activeRecord;

import bd.Bd;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Plat implements ActiveRecord{

    private int numplat;
    private String libelle;
    private String type;
    private double prixunit;
    private int qtservie;

    public Plat(String libelle, String type, double prixunit, int qtservie){
        if (libelle == null || type == null || prixunit < 0 || qtservie < 0) {
            throw new IllegalArgumentException("Les paramètres ne peuvent pas être null");
        }

        this.numplat = 0;
        this.libelle = libelle;
        this.type = type;
        this.prixunit = prixunit;
        this.qtservie = qtservie;
    }

    public Plat(int numplat, String libelle, String type, double prixunit, int qtservie){
        if (numplat <= 0 || libelle == null || type == null || prixunit < 0 || qtservie < 0) {
            throw new IllegalArgumentException("Les paramètres ne peuvent pas être null");
        }

        this.numplat = numplat;
        this.libelle = libelle;
        this.type = type;
        this.prixunit = prixunit;
        this.qtservie = qtservie;
    }

    @Override
    public void save(Bd bd) {
        if (bd == null) throw new IllegalArgumentException("La connexion ne peut pas être null");

        System.out.println("numplat : " + this.numplat);
        if (this.numplat == 0) {
            String sql = "INSERT INTO plat (libelle, type, prixunit, qtservie) VALUES (?, ?, ?, ?)";
            try{
                bd.executeQuery(sql, this.libelle, this.type, this.prixunit, this.qtservie);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else{
            String sql = "UPDATE plat SET libelle = ?, type = ?, prixunit = ?, qteservie = ? WHERE numplat = ?";
            try{
                bd.executeQuery(sql, this.libelle, this.type, this.prixunit, this.qtservie, this.numplat);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static List<Plat> getAll(Bd bd) throws SQLException {
        if (bd == null) throw new IllegalArgumentException("La connexion ne peut pas être null");
        ArrayList<Plat> plats = new ArrayList<>();
        ResultSet rs = bd.executeQuery("SELECT * FROM plat");
        while (rs.next()) {
            plats.add(new Plat(rs.getInt("numplat"), rs.getString("libelle"), rs.getString("type"), rs.getDouble("prixunit"), rs.getInt("qteservie")));
        }
        return plats;
    }

    public String toString(){
        return "- ["+this.numplat+"] " +this.libelle + " (" + this.type + ") - " + this.prixunit + "€" + " - " + this.qtservie + " restants";
    }

    public int getQtservie(){
        return this.qtservie;
    }

    public void setQtservie(int qtservie){
        this.qtservie = qtservie;
    }

    public static Plat findByNum(Bd bd, int numplat) throws SQLException {
        if (bd == null) throw new IllegalArgumentException("La connexion ne peut pas être null");

        ResultSet rs = bd.executeQuery("SELECT * FROM plat WHERE numplat = ?", numplat);
        if (rs.next()) {
            return new Plat(rs.getInt("numplat"), rs.getString("libelle"), rs.getString("type"), rs.getDouble("prixunit"), rs.getInt("qteservie"));
        }
        return null;
    }
}
