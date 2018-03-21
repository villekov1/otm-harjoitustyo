package domain;

public class Puyo {
    private int sijaintiX;
    private int sijaintiY;
    private Vari vari;
    private boolean maassa;
    
    public Puyo(int sijaintiX, int sijaintiY, Vari vari){
        this.sijaintiX = sijaintiX;
        this.sijaintiY = sijaintiY;
        this.vari = vari;
        this.maassa = false;
    }
    public Puyo(Vari vari){
        this.vari = vari;
        this.sijaintiX = 3;
        this.sijaintiY = 0;
        this.maassa = false;
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

    public boolean isMaassa() {
        return maassa;
    }

    public void setSijaintiX(int sijaintiX) {
        this.sijaintiX = sijaintiX;
    }

    public void setSijaintiY(int sijaintiY) {
        this.sijaintiY = sijaintiY;
    }

    public void setVari(Vari vari) {
        //Tälle ei pitäisi olla mitään tarvetta, mutta tehdään se kuitenkin varmuuden vuoksi
        this.vari = vari;
    }

    public void setMaassa(boolean maassa) {
        this.maassa = maassa;
    }
    
    
}