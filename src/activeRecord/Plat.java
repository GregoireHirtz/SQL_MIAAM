package activeRecord;

import bd.Bd;

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

    @Override
    public void save(Bd bd) {
        if (bd == null) throw new IllegalArgumentException("La connexion ne peut pas être null");

        if (this.numplat == 0) {
            String sql = "INSERT INTO plat (libelle, type, prixunit, qtservie) VALUES (?, ?, ?, ?)";
            try{
                bd.executeQuery(sql, this.libelle, this.type, this.prixunit, this.qtservie);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else{
            String sql = "UPDATE plat SET libelle = ?, type = ?, prixunit = ?, qtservie = ? WHERE numplat = ?";
            try{
                bd.executeQuery(sql, this.libelle, this.type, this.prixunit, this.qtservie, this.numplat);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
