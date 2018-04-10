package domain;

import java.util.Random;

public class Puyo {
    private int sijaintiX;
    private int sijaintiY;
    private Vari vari;
    
    public Puyo(int sijaintiX, int sijaintiY, Vari vari){
        this.sijaintiX = sijaintiX;
        this.sijaintiY = sijaintiY;
        this.vari = vari;
    }
    public Puyo(Vari vari){
        this.vari = vari;
        this.sijaintiX = 2;
        this.sijaintiY = 0;
    }

    public int getSijaintiX() {
        return sijaintiX;
    }

    public int getSijaintiY() {
        return sijaintiY;
    }

    public Vari getVari() {
        return vari;
    }

    public void setSijaintiX(int sijaintiX) {
        this.sijaintiX = sijaintiX;
    }

    public void setSijaintiY(int sijaintiY) {
        this.sijaintiY = sijaintiY;
    }
    
    public void siirraX(int siirtyma){
        this.sijaintiX = this.sijaintiX + siirtyma;
    }
    
    public void siirraY(int siirtyma){
        this.sijaintiY = this.sijaintiY + siirtyma;
    }

    public void setVari(Vari vari) {
        //Tälle ei pitäisi olla mitään tarvetta, mutta tehdään se kuitenkin varmuuden vuoksi
        this.vari = vari;
    }
    
    public String toString(){
        return "("+this.getSijaintiX()+", "+this.getSijaintiY()+"), "+this.getVari();
    }
    public boolean equals(Object verrattava) {
        if (this == verrattava) {
            return true;
        }

        if (!(verrattava instanceof Puyo)) {
            return false;
        }

        Puyo verrattavaPuyo = (Puyo) verrattava;

        if (this.sijaintiX == verrattavaPuyo.sijaintiX &&
                this.sijaintiY == verrattavaPuyo.sijaintiY &&
                this.vari == verrattavaPuyo.vari) {
            return true;
        }

        return false;
    }
    
}
