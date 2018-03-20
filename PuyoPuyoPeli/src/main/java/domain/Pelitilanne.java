package domain;

import java.util.HashMap;

public class Pelitilanne {
    
    private HashMap<Integer, HashMap<Integer, Puyo>> ruudukko;
    private int pisteet;
    private int puyoja;
    private int leveys;
    private int korkeus;
    private Puyo tippuva;
    private Puyo seuraava;
    
    
    public Pelitilanne(){
        this.leveys = 6;
        this.korkeus = 13;
        this.pisteet = 0;
        this.puyoja = 0;
        
        this.ruudukko = new HashMap<>();
    }
}
