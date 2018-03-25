package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
        
        this.asetaPari();
    }
    
    public void paivita(){
        this.tiputa();
        this.paivitaTyhjatPaikat();
        
        if(this.onkoTaynna(tippuva.getSijaintiX(), tippuva.getSijaintiY()) 
                && this.onkoTaynna(tippuvanAkseli.getSijaintiX(), tippuvanAkseli.getSijaintiY())){
            this.asetaPari();
        }
        
        for(int i=0; i<leveys; i++){
            for(int j=0; j<korkeus; j++){
                ArrayList<Puyo> ketju = this.etsiKetju(i, j);
                this.tuhoaKetjunPuyot(ketju);
            }
        }
    }
    
    public void tiputa(){
        if(tippuva.getSijaintiY()>tippuvanAkseli.getSijaintiY()){
            if(tippuva.getSijaintiY()+1<=12 && !this.onkoTaynna(tippuva.getSijaintiX(), tippuva.getSijaintiY()+1)){
                this.tippuva.siirraY(1);
            }
            this.paivitaTyhjatPaikat();
            
            if(tippuvanAkseli.getSijaintiY()+1<=12 && !this.onkoTaynna(tippuvanAkseli.getSijaintiX(), tippuvanAkseli.getSijaintiY()+1)){
                this.tippuvanAkseli.siirraY(1);
            }
            
        }else{  
            if(tippuvanAkseli.getSijaintiY()+1<=12 && !this.onkoTaynna(tippuvanAkseli.getSijaintiX(), tippuvanAkseli.getSijaintiY()+1)){
                this.tippuvanAkseli.siirraY(1);
            }
            this.paivitaTyhjatPaikat();
            
            if(tippuva.getSijaintiY()+1<=12 && !this.onkoTaynna(tippuva.getSijaintiX(), tippuva.getSijaintiY()+1)){
                this.tippuva.siirraY(1);
            }
        }
        ruudukko.get(tippuva.getSijaintiX()).put(tippuva.getSijaintiY(), tippuva);
        ruudukko.get(tippuvanAkseli.getSijaintiX()).put(tippuvanAkseli.getSijaintiY(), tippuvanAkseli);
        
        //Tämän avulla varmistetaan, että tippuvat puyot tallentuvat maassa oleviksi
        if(this.onkoTaynna(tippuva.getSijaintiX(), tippuva.getSijaintiY()+1) || tippuva.getSijaintiY()==12){
            puyot.add(new Puyo(tippuva.getSijaintiX(), tippuva.getSijaintiY(), tippuva.getVari()));
        }
        if(this.onkoTaynna(tippuvanAkseli.getSijaintiX(), tippuvanAkseli.getSijaintiY()+1) || tippuvanAkseli.getSijaintiY()==12){
            puyot.add(new Puyo(tippuvanAkseli.getSijaintiX(), tippuvanAkseli.getSijaintiY(), tippuvanAkseli.getVari()));
        }

    }
    
    public void siirraVasemmalle(){
        if(tippuva.getSijaintiX()>0 && tippuvanAkseli.getSijaintiX()>0){
            if(!this.onkoTaynna(tippuva.getSijaintiX(), tippuva.getSijaintiY()) &&
                !this.onkoTaynna(tippuvanAkseli.getSijaintiX(), tippuvanAkseli.getSijaintiY())){
                if(!this.onkoTaynna(tippuva.getSijaintiX()-1, tippuva.getSijaintiY()) 
                    && !this.onkoTaynna(tippuvanAkseli.getSijaintiX()-1, tippuvanAkseli.getSijaintiY())){
                        tippuva.siirraX(-1);
                        tippuvanAkseli.siirraX(-1);

                }

            }
        }
    }
    
    public void siirraOikealle(){
        if(tippuva.getSijaintiX()<5 && tippuvanAkseli.getSijaintiX()<5){
            if(!this.onkoTaynna(tippuva.getSijaintiX(), tippuva.getSijaintiY()) &&
                !this.onkoTaynna(tippuvanAkseli.getSijaintiX(), tippuvanAkseli.getSijaintiY())){
                if(!this.onkoTaynna(tippuva.getSijaintiX()+1, tippuva.getSijaintiY()) 
                    && !this.onkoTaynna(tippuvanAkseli.getSijaintiX()+1, tippuvanAkseli.getSijaintiY())){
                        tippuva.siirraX(1);
                        tippuvanAkseli.siirraX(1);
                }
            }
        }
    }
    
    public void kaannaOikealle(){
        
    }
    
    public void kaannaVasemmalle(){
    
    }
    
    public void paivitaTyhjatPaikat(){
        
        for(int i=0; i<leveys; i++){
            for(int j=0; j<korkeus; j++){
                taynna.get(i).put(j, false);
            }
        }
        
        this.puyot.stream().forEach(puyo -> {
            if(puyo != tippuva && puyo != tippuvanAkseli){
                taynna.get(puyo.getSijaintiX()).put(puyo.getSijaintiY(), true);
            }

        });
        
    }
    
    public ArrayList<Puyo> etsiKetju(int x, int y){
        ArrayList<Puyo> lista = new ArrayList<>();
        
        return lista;
    }
    
    public Puyo arvoPuyo(){
        Vari vari;
        Random arpoja = new Random();
        int luku = arpoja.nextInt(9);
        
        if(luku>=0 && luku<2){
            vari = Vari.PUNAINEN;
        }else if(luku>=2 && luku<4){
            vari = Vari.SININEN;
        }else if(luku>=4 && luku<6){
            vari = Vari.KELTAINEN;
        }else if(luku>= 6 && luku<8){
            vari = Vari.VIHREA;
        }else{
            vari = Vari.VIOLETTI;
        }
        
        Puyo puyo = new Puyo(2, 0, vari);
        
        return puyo;
    }
    
    public void asetaPari(){
        tippuva = this.arvoPuyo();
        tippuvanAkseli = this.arvoPuyo();
        tippuvanAkseli.siirraY(1);
        puyot.add(tippuva);
        puyot.add(tippuvanAkseli);
    }
    
    public boolean onkoTaynna(int x, int y){
        if(y>=13){
            return true;
        }
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
    
    public Puyo getTippuvanAkseli(){
        return this.tippuvanAkseli;
    }
    
    public ArrayList<Puyo> getPuyot(){
        return this.puyot;
    }
    
}
