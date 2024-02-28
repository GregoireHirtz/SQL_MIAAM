package activeRecord;

import bd.Bd;

public class Commande implements ActiveRecord{

    private int numres;
    private int numplat;
    private int quantite;

    public Commande(int numres, int numplat, int quantite){
        if (numres < 0 || numplat < 0 || quantite < 0) {
            throw new IllegalArgumentException("Les paramètres ne peuvent pas être null");
        }

        this.numres = numres;
        this.numplat = numplat;
        this.quantite = quantite;
    }

    @Override
    public void save(Bd bd) {
        if (bd == null) throw new IllegalArgumentException("La connexion ne peut pas être null");

        String sql = "INSERT INTO commande (numres, numplat, quantite) VALUES (?, ?, ?)";
        try{
            bd.executeQuery(sql, this.numres, this.numplat, this.quantite);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
