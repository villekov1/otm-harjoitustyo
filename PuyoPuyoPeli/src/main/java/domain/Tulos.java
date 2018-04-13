package domain;


public class Tulos {
    private int id;
    private int tulos;
    private String nimi;
    
    public Tulos(int id, int tulos, String nimi){
        this.tulos = tulos;
        this.nimi = nimi;
    }
    
    public String getNimi(){
        return this.nimi;
    }
    public int getTulos(){
        return this.tulos;
    }
    
    public String toString(){
        return this.nimi + ": "+this.tulos;
    }
}
