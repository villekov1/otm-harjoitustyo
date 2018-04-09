package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

public class Pelitilanne {
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
        this.taynna = new HashMap<>();
        this.puyot = new ArrayList<>();
        
        for(int i=0; i<leveys; i++){
            taynna.put(i, new HashMap<>());
            for(int j=0; j<korkeus; j++){
                taynna.get(i).put(j, false);
            }
        }
        
        this.asetaPari();
    }
    
    public void paivita(){
        this.tiputaTippuvat();
        this.paivitaTyhjatPaikat();
        
        if(this.onkoTaynna(tippuva.getSijaintiX(), tippuva.getSijaintiY()) 
            && this.onkoTaynna(tippuvanAkseli.getSijaintiX(), tippuvanAkseli.getSijaintiY())){
            this.asetaPari();
            
            //Poistetaan ketjut vain silloin, kun molemmat tippuvat omat maassa
            int i=0;
            while(i<this.puyot.size()){
                Puyo puyo = this.puyot.get(i);
                ArrayList<Puyo> ketju = this.etsiKetju(puyo.getSijaintiX(), puyo.getSijaintiY());
                this.tuhoaKetjunPuyot(ketju);
                i++;
            }
        }
        
        this.paivitaTyhjatPaikat();
        this.tiputaKaikki();
    }
    
    public void tiputaTippuvat(){
        if(tippuva.getSijaintiY()>=tippuvanAkseli.getSijaintiY()){
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
        this.paivitaTyhjatPaikat();
        
        
        //Tämän avulla varmistetaan, että tippuvat puyot tallentuvat maassa oleviksi
        if(this.onkoTaynna(tippuva.getSijaintiX(), tippuva.getSijaintiY()+1) || tippuva.getSijaintiY()==12){
            puyot.add(new Puyo(tippuva.getSijaintiX(), tippuva.getSijaintiY(), tippuva.getVari()));
            puyot.remove(tippuva);
        }
        if(this.onkoTaynna(tippuvanAkseli.getSijaintiX(), tippuvanAkseli.getSijaintiY()+1) || tippuvanAkseli.getSijaintiY()==12){
            puyot.add(new Puyo(tippuvanAkseli.getSijaintiX(), tippuvanAkseli.getSijaintiY(), tippuvanAkseli.getVari()));
            puyot.remove(tippuvanAkseli);
        }

    }
    
    public void tiputaKaikki(){
        for(int y=korkeus-1; y>=0; y--){
            for(int x=leveys-1; x>=0; x--){
                if(this.onkoTaynna(x, y) && !this.onkoTaynna(x, y+1)){
                    Puyo puyo = this.etsiPuyo(x, y);
                    while(true){
                        puyo.siirraY(1);
                        this.paivitaTyhjatPaikat();
                        if(this.onkoTaynna(puyo.getSijaintiX(), puyo.getSijaintiY()+1) || puyo.getSijaintiY()==12){
                            break;
                        }
                    } 
                }
            }
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
        if(!onkoTaynna(tippuva.getSijaintiX(),tippuva.getSijaintiY()) 
            && !onkoTaynna(tippuvanAkseli.getSijaintiX(), tippuvanAkseli.getSijaintiY())){
            if(tippuva.getSijaintiY()<tippuvanAkseli.getSijaintiY() && tippuvanAkseli.getSijaintiX()<5
                && !this.onkoTaynna(tippuvanAkseli.getSijaintiX()+1, tippuvanAkseli.getSijaintiY())){
                tippuva.siirraX(1);
                tippuva.siirraY(1);
            }else if(tippuva.getSijaintiX()>tippuvanAkseli.getSijaintiX()
                && !this.onkoTaynna(tippuvanAkseli.getSijaintiX(), tippuvanAkseli.getSijaintiY()+1)){
                tippuva.siirraX(-1);
                tippuva.siirraY(1);
            }else if(tippuva.getSijaintiY()>tippuvanAkseli.getSijaintiY() && tippuvanAkseli.getSijaintiX()>0
                && !this.onkoTaynna(tippuvanAkseli.getSijaintiX()-1, tippuvanAkseli.getSijaintiY())){
                tippuva.siirraX(-1);
                tippuva.siirraY(-1);
            }else if(tippuva.getSijaintiX()<tippuvanAkseli.getSijaintiX()
                && !this.onkoTaynna(tippuvanAkseli.getSijaintiX(), tippuvanAkseli.getSijaintiY()-1)){
                tippuva.siirraX(1);
                tippuva.siirraY(-1);
            }
        }
    }
    
    public void kaannaVasemmalle(){
    
    }
    
    public void paivitaTyhjatPaikat(){
        //Varmistetaan, että listassa ei ole saman Puyon kopioita.
        ArrayList<Puyo> karsittulista = this.puyot.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
        this.puyot = karsittulista;
        
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
        if(y>=13 || y<0 || x<0 || x>=6){
            return true;
        }
        if(this.taynna.get(x).get(y) == true){
            return true;
        }
        
        return false;
    }
    
    public ArrayList<Puyo> etsiKetju(int x, int y){
        ArrayList<Puyo> lista = new ArrayList<>();
        lista.add(this.etsiPuyo(x, y));
        
        int i=0;
        while(i<5){
            int j=0;
            int koko = lista.size();
            while(j<koko){
                Puyo puyo = lista.get(j);
                Vari vari = puyo.getVari();        
                int x2 = puyo.getSijaintiX();
                int y2 = puyo.getSijaintiY();
                
                if(x2-1>= 0 && onkoTaynna(x2-1, y2) && this.etsiPuyo(x2-1, y2).getVari() == vari && !lista.contains(this.etsiPuyo(x2-1,y2))){
                    lista.add(this.etsiPuyo(x2-1,y2));
                }
                if(x2+1<leveys && onkoTaynna(x2+1, y)&& this.etsiPuyo(x2+1, y2).getVari() == vari && !lista.contains(this.etsiPuyo(x2+1,y2))){
                    lista.add(this.etsiPuyo(x2+1,y2));
                }
                if(y2+1<korkeus && onkoTaynna(x2, y2+1) && this.etsiPuyo(x2, y2+1).getVari() == vari && !lista.contains(this.etsiPuyo(x2,y2+1))){
                    lista.add(this.etsiPuyo(x2,y2+1));
                }
                if(y2-1>=0 && onkoTaynna(x2, y2-1) && this.etsiPuyo(x2, y2-1).getVari() == vari && !lista.contains(this.etsiPuyo(x2,y2-1))){
                    lista.add(this.etsiPuyo(x2,y2-1));
                }
                j++;
            }
            i++;   
        }
        
        return lista;     
    }
    
    public Puyo etsiPuyo(int x, int y){
        Puyo puyo;
        int i=0;
        while(i<puyot.size()){
            if(puyot.get(i).getSijaintiX()==x && puyot.get(i).getSijaintiY()==y 
                && puyot.get(i)!=tippuva && puyot.get(i)!=tippuvanAkseli){
                return puyot.get(i);
            }
            i++;
        }
        return new Puyo(x, y, Vari.TYHJA);
    }
    
    public void tuhoaKetjunPuyot(ArrayList<Puyo> lista){
        if(lista.size()>=4){
            lista.stream().distinct().forEach(puyo -> {
                puyot.remove(puyo);
            });
        }
    }
    
    public int palautaLeveys(){
        return this.leveys;
    }
    
    public int palautaKorkeus(){
        return this.korkeus;
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
    
    public int getPisteet(){
        return this.pisteet;
    }
    
}