package domain;

import java.util.ArrayList;
import java.util.HashMap;

public class Pelitilanne {
    
    private HashMap<Integer, HashMap<Integer, Puyo>> ruudukko;
    private HashMap<Integer, HashMap<Integer, Boolean>> taynna;
    private ArrayList<Puyo> puyot;
    
    private int pisteet;
    private int puyoja;
    private int leveys;
    private int korkeus;
    private Puyo tippuva;
    private Puyo tippuvanAkseli;
    private Puyo seuraava;
    
    public Pelitilanne(){
        this.leveys = 6;
        this.korkeus = 13;
        this.pisteet = 0;
        this.puyoja = 0;
        
        this.ruudukko = new HashMap<>();
        this.taynna = new HashMap<>();
        this.puyot = new ArrayList<>();
        
        for(int i=0; i<leveys; i++){
            HashMap<Integer, Puyo> rivi = new HashMap<>();
            ruudukko.put(i, rivi);
            taynna.put(i, new HashMap<>());
            
            for(int j=0; j<korkeus; j++){
                taynna.get(i).put(j, false);
            }
        }
        
        tippuva = this.arvoPuyo();
        tippuvanAkseli = this.arvoPuyo();
        tippuvanAkseli.siirraY(1);
        
        puyot.add(tippuva);
        puyot.add(tippuvanAkseli);
        
        ruudukko.get(tippuva.getSijaintiX()).put(tippuva.getSijaintiY(), tippuva);
        ruudukko.get(tippuvanAkseli.getSijaintiX()).put(tippuvanAkseli.getSijaintiY(), tippuvanAkseli);
        
        taynna.get(tippuva.getSijaintiX()).put(tippuva.getSijaintiY(), true);
        taynna.get(tippuvanAkseli.getSijaintiX()).put(tippuvanAkseli.getSijaintiY(), true);
    }
    
    public void paivita(){
        
        this.tiputa();
        this.paivitaTyhjatPaikat();
        
        for(int i=0; i<leveys; i++){
            for(int j=0; j<korkeus; j++){
                ArrayList<Puyo> ketju = this.etsiKetju(i, j);
                this.tuhoaKetjunPuyot(ketju);
            }
        }
    }
    
    public void tiputa(){
        //Ei lopullinen
        this.tippuva.siirraY(1);
        this.tippuvanAkseli.siirraY(1);
        
        ruudukko.get(tippuva.getSijaintiX()).put(tippuva.getSijaintiY(), tippuva);
        ruudukko.get(tippuvanAkseli.getSijaintiX()).put(tippuvanAkseli.getSijaintiY(), tippuvanAkseli);
        
    }
    
    public void paivitaTyhjatPaikat(){
        
        for(int i=0; i<leveys; i++){
            for(int j=0; j<korkeus; j++){
                taynna.get(i).put(j, false);
            }
        }
        
        this.puyot.stream().forEach(puyo -> {
            int x = puyo.getSijaintiX();
            int y = puyo.getSijaintiY();
            
            taynna.get(x).put(y, true);
            
        });
        
    }
    
    public ArrayList<Puyo> etsiKetju(int x, int y){
        ArrayList<Puyo> lista = new ArrayList<>();
        
        return lista;
    }
    
    public Puyo arvoPuyo(){
        //Ei lopullinen
        Puyo puyo = new Puyo(2, 0, Vari.PUNAINEN);
        
        return puyo;
    }
    
    public boolean onkoTaynna(int x, int y){
        if(this.taynna.get(x).get(y) == true){
            return true;
        }
        
        return false;
    }
    
    public void tuhoaKetjunPuyot(ArrayList<Puyo> lista){
        //Ei lopullinen
    }
    
    public int palautaLeveys(){
        return this.leveys;
    }
    
    public int palautaKorkeus(){
        return this.korkeus;
    }
    
    public HashMap<Integer, HashMap<Integer, Puyo>> palautaTilanne(){
        return this.ruudukko;
    }
    
    public Puyo getTippuva(){
        return this.tippuva;
    }
}
