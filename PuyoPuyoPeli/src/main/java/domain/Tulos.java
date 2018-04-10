package domain;

import java.util.Calendar;
import java.util.Date;

public class Tulos {
    private int tulos;
    private Calendar pvm;
    private String nimi;
    
    public Tulos(int tulos, String nimi){
        this.tulos = tulos;
        this.nimi = nimi;
        this.pvm = Calendar.getInstance();
    }
    
    public String toString(){
        String paivays = ""+pvm.get(Calendar.DAY_OF_MONTH)+"."+pvm.get(Calendar.MONTH)+"."+pvm.get(Calendar.YEAR);
        return this.nimi + ": "+this.tulos; //+", ("+paivays+")";
    }
}
