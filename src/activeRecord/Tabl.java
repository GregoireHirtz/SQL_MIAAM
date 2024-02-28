package activeRecord;

import bd.Bd;

public class Tabl implements ActiveRecord {

    private int numtab;
    private int nbplace;

    public Tabl(int nbplace) {
        if (nbplace < 0) throw new IllegalArgumentException("Le nombre de place ne peut pas être négatif");

        this.numtab = 0;
        this.nbplace = nbplace;
    }

    @Override
    public void save(Bd bd){
        if (bd == null) throw new IllegalArgumentException("La connexion ne peut pas être null");

        if (this.numtab == 0) {
            String sql = "INSERT INTO tabl (nbplace) VALUES (?)";
            try{
                bd.executeQuery(sql, this.nbplace);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else{
            String sql = "UPDATE tabl SET nbplace = ? WHERE numtab = ?";
            try{
                bd.executeQuery(sql, this.nbplace, this.numtab);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
