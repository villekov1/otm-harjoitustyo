package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Pelitilanne {
    private HashMap<Integer, HashMap<Integer, Boolean>> filled;
    private ArrayList<Puyo> puyos;
    
    private int points;
    private int puyonumber;
    private int width;
    private int height;
    private Puyo falling;
    private Puyo fallingAxis;
    private Puyo next;
    
    public Pelitilanne(int width, int height) {
        this.width = width;
        this.height = height;
        this.points = 0;
        this.puyonumber = 0;
        this.filled = new HashMap<>();
        //Tämä sisältää tiedon siitä, mitkä ruudukon paikat ovat täynnä. Tippuvia ei lasketa
        
        this.puyos = new ArrayList<>();
        
        for (int i = 0; i < width; i++) {
            filled.put(i, new HashMap<>());
            for (int j = 0; j < height; j++) {
                filled.get(i).put(j, false);
            }
        }
        
        this.setPair();
    }
    
    public void update() {
        this.dropFalling();
        this.updateFilled();
        
        if (this.isTheSpaceFilled(falling.getPositionX(), falling.getPositionY()) 
            && this.isTheSpaceFilled(fallingAxis.getPositionX(), fallingAxis.getPositionY())) {
            this.setPair();
            
            for (int j = 0; j < 3; j++) {
                //Poistetaan ketjut vain silloin, kun molemmat tippuvat omat maassa
                int i = 0;
                while (i < this.puyos.size()) {
                    Puyo puyo = this.puyos.get(i);
                    ArrayList<Puyo> ketju = this.findChain(puyo.getPositionX(), puyo.getPositionY());
                    this.destroyChain(ketju);
                    i++;
                }                
                this.updateFilled();
                this.dropAll();
            }
        }   
    }
    
    public void dropFalling() {
        if (falling.getPositionY() >= fallingAxis.getPositionY()) {
            if (falling.getPositionY() + 1 <= 12 && !this.isTheSpaceFilled(falling.getPositionX(), falling.getPositionY() + 1)) {
                this.falling.moveY(1);
            }
            this.updateFilled();
            
            if (fallingAxis.getPositionY() + 1 <= 12 
                && !this.isTheSpaceFilled(fallingAxis.getPositionX(), fallingAxis.getPositionY() + 1)) {
                this.fallingAxis.moveY(1);
            }
            
        } else {  
            if (fallingAxis.getPositionY() + 1 <= 12 && !this.isTheSpaceFilled(fallingAxis.getPositionX(), fallingAxis.getPositionY() + 1)) {
                this.fallingAxis.moveY(1);
            }
            this.updateFilled();
            
            if (falling.getPositionY() + 1 <= 12 && !this.isTheSpaceFilled(falling.getPositionX(), falling.getPositionY() + 1)) {
                this.falling.moveY(1);
            }
        }
        this.updateFilled();
        
        
        //Tämän avulla varmistetaan, että tippuvat puyot tallentuvat maassa oleviksi
        if (this.isTheSpaceFilled(falling.getPositionX(), falling.getPositionY() + 1) || falling.getPositionY() == 12) {
            puyos.add(new Puyo(falling.getPositionX(), falling.getPositionY(), falling.getColour()));
            puyos.remove(falling);
        }
        if (this.isTheSpaceFilled(fallingAxis.getPositionX(), fallingAxis.getPositionY() + 1) || fallingAxis.getPositionY() == 12) {
            puyos.add(new Puyo(fallingAxis.getPositionX(), fallingAxis.getPositionY(), fallingAxis.getColour()));
            puyos.remove(fallingAxis);
        }
    }
    
    public void dropAll() {
        for (int y = height - 1; y >= 0; y--) {
            for (int x = width - 1; x >= 0; x--) {
                if (this.isTheSpaceFilled(x, y) && !this.isTheSpaceFilled(x, y + 1)) {
                    Puyo puyo = this.findPuyo(x, y);
                    while(true) {
                        puyo.moveY(1);
                        this.updateFilled();
                        if (this.isTheSpaceFilled(puyo.getPositionX(), puyo.getPositionY()+1) || puyo.getPositionY()==12) {
                            break;
                        }
                    } 
                }
            }
        }  
    }
    
    public void moveLeft() {
        if (falling.getPositionX() > 0 && fallingAxis.getPositionX() > 0) {
            if (!this.isTheSpaceFilled(falling.getPositionX(), falling.getPositionY()) &&
                !this.isTheSpaceFilled(fallingAxis.getPositionX(), fallingAxis.getPositionY())) {
                if (!this.isTheSpaceFilled(falling.getPositionX()-1, falling.getPositionY()) 
                    && !this.isTheSpaceFilled(fallingAxis.getPositionX()-1, fallingAxis.getPositionY())) {
                        falling.moveX(-1);
                        fallingAxis.moveX(-1);
                }
            }
        }
    }
    
    public void moveRight() {
        if (falling.getPositionX() < 5 && fallingAxis.getPositionX() < width - 1) {
            if (!this.isTheSpaceFilled(falling.getPositionX(), falling.getPositionY()) &&
                !this.isTheSpaceFilled(fallingAxis.getPositionX(), fallingAxis.getPositionY())) {
                if (!this.isTheSpaceFilled(falling.getPositionX() + 1, falling.getPositionY()) 
                    && !this.isTheSpaceFilled(fallingAxis.getPositionX()+1, fallingAxis.getPositionY())) {
                        falling.moveX(1);
                        fallingAxis.moveX(1);
                }
            }
        }
    }
    
    public void kaannaOikealle() {
        if (!isTheSpaceFilled(falling.getPositionX(),falling.getPositionY()) 
            && !isTheSpaceFilled(fallingAxis.getPositionX(), fallingAxis.getPositionY())) {
            if (falling.getPositionY() < fallingAxis.getPositionY() && fallingAxis.getPositionX() < width -1
                && !this.isTheSpaceFilled(fallingAxis.getPositionX() + 1, fallingAxis.getPositionY())){
                falling.moveX(1);
                falling.moveY(1);
            } else if (falling.getPositionX() > fallingAxis.getPositionX()
                && !this.isTheSpaceFilled(fallingAxis.getPositionX(), fallingAxis.getPositionY() + 1)) {
                falling.moveX(-1);
                falling.moveY(1);
            } else if (falling.getPositionY() > fallingAxis.getPositionY() && fallingAxis.getPositionX() > 0
                && !this.isTheSpaceFilled(fallingAxis.getPositionX() - 1, fallingAxis.getPositionY())) {
                falling.moveX(-1);
                falling.moveY(-1);
            } else if (falling.getPositionX() < fallingAxis.getPositionX()
                && !this.isTheSpaceFilled(fallingAxis.getPositionX(), fallingAxis.getPositionY() - 1)) {
                falling.moveX(1);
                falling.moveY(-1);
            }
        }
    }
    
    public void kaannaVasemmalle() {
    
    }
    
    public void updateFilled() {
        //Varmistetaan, että listassa ei ole saman Puyon kopioita.
        ArrayList<Puyo> cutDownList = this.puyos.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
        this.puyos = cutDownList;
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                filled.get(i).put(j, false);
            }
        }
        
        this.puyos.stream().forEach(puyo -> {
            if (puyo != falling && puyo != fallingAxis) {
                filled.get(puyo.getPositionX()).put(puyo.getPositionY(), true);
            }
        });
        
    }
    
    public Puyo randomPuyo() {
        Vari vari;
        Random arpoja = new Random();
        int luku = arpoja.nextInt(9);
        
        if (luku >= 0 && luku < 2) {
            vari = Vari.RED;
        } else if (luku >= 2 && luku < 4) {
            vari = Vari.BLUE;
        } else if (luku >= 4 && luku < 6) {
            vari = Vari.YELLOW;
        } else if (luku >= 6 && luku < 8) {
            vari = Vari.GREEN;
        } else {
            vari = Vari.PURPLE;
        }
        
        Puyo puyo = new Puyo(2, 0, vari);
        return puyo;
    }
    
    public void setPair() {
        falling = this.randomPuyo();
        fallingAxis = this.randomPuyo();
        fallingAxis.moveY(1);
        puyos.add(falling);
        puyos.add(fallingAxis);
    }
    
    public boolean isTheSpaceFilled(int x, int y) {
        if (y >= height || y < 0 || x < 0 || x >= width) {
            return true;
        }
        if (this.filled.get(x).get(y) == true) {
            return true;
        }
        
        return false;
    }
    
    public ArrayList<Puyo> findChain(int x, int y) {
        ArrayList<Puyo> list = new ArrayList<>();
        list.add(this.findPuyo(x, y));
        
        for (int i = 0; i < 6; i++) {
            int size = list.size();
            for (int j = 0; j < size; j++) {
                Puyo puyo = list.get(j);
                Vari vari = puyo.getColour();        
                int x2 = puyo.getPositionX();
                int y2 = puyo.getPositionY();
                
                if (x2 - 1 >= 0 && isTheSpaceFilled(x2 - 1, y2) && this.findPuyo(x2 - 1, y2).getColour() == vari && !list.contains(this.findPuyo(x2 - 1,y2))) {
                    list.add(this.findPuyo(x2 - 1,y2));
                }
                if (x2 + 1 < width && isTheSpaceFilled(x2 + 1, y)&& this.findPuyo(x2 + 1, y2).getColour() == vari && !list.contains(this.findPuyo(x2 + 1,y2))) {
                    list.add(this.findPuyo(x2 + 1,y2));
                }
                if (y2 + 1 < height && isTheSpaceFilled(x2, y2 + 1) && this.findPuyo(x2, y2 + 1).getColour() == vari && !list.contains(this.findPuyo(x2,y2 + 1))) {
                    list.add(this.findPuyo(x2,y2 + 1));
                }
                if (y2 - 1>=0 && isTheSpaceFilled(x2, y2 - 1) && this.findPuyo(x2, y2 - 1).getColour() == vari && !list.contains(this.findPuyo(x2,y2 - 1))) {
                    list.add(this.findPuyo(x2,y2 - 1));
                }
            }    
        }
        
        return list;     
    }
    
    public Puyo findPuyo(int x, int y) {
        Puyo puyo;
        int i = 0;
        while (i < puyos.size()) {
            if (puyos.get(i).getPositionX() == x && puyos.get(i).getPositionY() == y 
                && puyos.get(i) != falling && puyos.get(i) != fallingAxis) {
                return puyos.get(i);
            }
            i++;
        }
        return new Puyo(x, y, Vari.EMPTY);
    }
    
    public void destroyChain(ArrayList<Puyo> list) {
        if (list.size() >= 4) {
            list.stream().distinct().forEach(puyo -> {
                this.points += 10;
                puyos.remove(puyo);
            });
        }
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public Puyo getFalling() {
        return this.falling;
    }
    
    public Puyo getFallingAxis() {
        return this.fallingAxis;
    }
    
    public ArrayList<Puyo> getPuyos() {
        return this.puyos;
    }
    
    public int getPoints() {
        return this.points;
    }
    
    public void addPoints(int amount) {
        this.points += amount;
    }
    
    public void addPuyo(Puyo puyo){
        this.puyos.add(puyo);
    }
}