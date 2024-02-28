package activeRecord;

import bd.Bd;

import java.util.Date;

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
}
