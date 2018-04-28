package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * This class is responsible for the game logic in Puyo Puyo -game.
 */
public class GameLogic {
    private HashMap<Integer, HashMap<Integer, Boolean>> filled;
    private FilledMap filledMap;
    private ArrayList<Puyo> puyos;
    
    private int points;
    private int puyonumber;
    private int width;
    private int height;
    private Puyo falling;
    private Puyo fallingAxis;
    private Puyo next;
    
    public GameLogic(int width, int height) {
        this.width = width;
        this.height = height;
        this.points = 0;
        this.puyonumber = 0;
        this.filledMap = new FilledMap(width, height);
        this.puyos = new ArrayList<>();
        
        this.setPair();
    }
    
    /**
    * The methods updates the situation in game.
    * It drops the falling Puyos, updates the FilledMap, finds chains and destroys them.
    */
    public void update() {
        this.dropFalling();
        this.updateFilled();
        
        if (this.isTheSpaceFilled(falling.getPositionX(), falling.getPositionY()) 
            && this.isTheSpaceFilled(fallingAxis.getPositionX(), fallingAxis.getPositionY())) {
            this.setPair();
            
            for (int j = 0; j < 3; j++) {
                //Poistetaan ketjut vain silloin, kun molemmat tippuvat omat maassa
                for (int i = 0; i < this.puyos.size(); i++) {
                    Puyo puyo = this.puyos.get(i);
                    ArrayList<Puyo> ketju = this.findChain(puyo.getPositionX(), puyo.getPositionY());
                    this.destroyChain(ketju);
                }                
                this.updateFilled();
                this.dropAll();
            }
        }   
    }
    
    /**
    * This methods drops the falling Puyos called falling and fallingAxis if possible.
    * It checks if the space under the Puyos are free and moves them if the spaces are free.
    */
    public void dropFalling() {
        
        if (falling.getPositionY() >= fallingAxis.getPositionY()) {
            if (falling.getPositionY() + 1 <= height - 1 && !this.isTheSpaceFilled(falling.getPositionX(), falling.getPositionY() + 1)) {
                this.falling.moveY(1);
            }
            this.updateFilled();
            
            if (fallingAxis.getPositionY() + 1 <= height - 1 
                && !this.isTheSpaceFilled(fallingAxis.getPositionX(), fallingAxis.getPositionY() + 1)) {
                this.fallingAxis.moveY(1);
            }  
        } else {  
            if (fallingAxis.getPositionY() + 1 <= height - 1  && !this.isTheSpaceFilled(fallingAxis.getPositionX(), fallingAxis.getPositionY() + 1)) {
                this.fallingAxis.moveY(1);
            }
            this.updateFilled();
            
            if (falling.getPositionY() + 1 <= height - 1 && !this.isTheSpaceFilled(falling.getPositionX(), falling.getPositionY() + 1)) {
                this.falling.moveY(1);
            }
        }
        this.updateFilled();
        this.addFallingPuyosToTheList();
    }
    
    /**
    * The method adds falling Puyos called falling and fallingAxis to the list called puyos.
    * This is necessary after both Puyos are on the ground.
    */
    public void addFallingPuyosToTheList() {
        //This puts falling Puyos to the list as new Puyos
        if (this.isTheSpaceFilled(falling.getPositionX(), falling.getPositionY() + 1) || falling.getPositionY() == height - 1) {
            puyos.add(new Puyo(falling.getPositionX(), falling.getPositionY(), falling.getColour()));
            puyos.remove(falling);
        }
        if (this.isTheSpaceFilled(fallingAxis.getPositionX(), fallingAxis.getPositionY() + 1) || fallingAxis.getPositionY() == height - 1) {
            puyos.add(new Puyo(fallingAxis.getPositionX(), fallingAxis.getPositionY(), fallingAxis.getColour()));
            puyos.remove(fallingAxis);
        }
    }
    
    /**
    * This method is called after chains are destroyed.
    * If any Puyo has a free space under them, the method will move them down until they hit the ground or other Puyos.
    */
    public void dropAll() {
        for (int y = height - 1; y >= 0; y--) {
            for (int x = width - 1; x >= 0; x--) {
                if (this.isTheSpaceFilled(x, y) && !this.isTheSpaceFilled(x, y + 1)) {
                    Puyo puyo = this.findPuyo(x, y);
                    while (true) {
                        puyo.moveY(1);
                        this.updateFilled();
                        if (this.isTheSpaceFilled(puyo.getPositionX(), puyo.getPositionY() + 1) || puyo.getPositionY() == height - 1) {
                            break;
                        }
                    } 
                }
            }
        }  
    }
    
    /**
    * The method will move falling Puyos falling and fallingAxis to the left if possible.
    */
    public void moveLeft() {
        if (falling.getPositionX() > 0 && fallingAxis.getPositionX() > 0) {
            if (!this.areFallingPuyosOnTheGround()) {
                if (!this.isTheSpaceFilled(falling.getPositionX() - 1, falling.getPositionY()) 
                    && !this.isTheSpaceFilled(fallingAxis.getPositionX() - 1, fallingAxis.getPositionY())) {
                    falling.moveX(-1);
                    fallingAxis.moveX(-1);
                }
            }
        }
    }
    
    /**
    * The method will move falling Puyos falling and fallingAxis to the right if possible.
    */
    public void moveRight() {
        if (falling.getPositionX() < width - 1 && fallingAxis.getPositionX() < width - 1) {
            if (!this.areFallingPuyosOnTheGround()) {
                if (!this.isTheSpaceFilled(falling.getPositionX() + 1, falling.getPositionY()) 
                    && !this.isTheSpaceFilled(fallingAxis.getPositionX() + 1, fallingAxis.getPositionY())) {
                    falling.moveX(1);
                    fallingAxis.moveX(1);
                }
            }
        }
    }
    
    /**
    * The method will turn falling Puyos clockwise if possible.
    */
    public void turnRight() {
        if (!this.areFallingPuyosOnTheGround()) {
            if (falling.getPositionY() < fallingAxis.getPositionY() && fallingAxis.getPositionX() < width - 1
                && !this.isTheSpaceFilled(fallingAxis.getPositionX() + 1, fallingAxis.getPositionY())) {
                falling.moveXY(1, 1);
            } else if (falling.getPositionY() < fallingAxis.getPositionY() && !this.isTheSpaceFilled(fallingAxis.getPositionX() - 1, fallingAxis.getPositionY())
                && this.isTheSpaceFilled(fallingAxis.getPositionX() + 1, fallingAxis.getPositionY())) {
                fallingAxis.moveXY(-1, 0);
                falling.moveXY(0, 1);
            } else if (falling.getPositionX() > fallingAxis.getPositionX()
                && !this.isTheSpaceFilled(fallingAxis.getPositionX(), fallingAxis.getPositionY() + 1)) {
                falling.moveXY(-1, 1);
            } else if (falling.getPositionY() > fallingAxis.getPositionY() && fallingAxis.getPositionX() > 0
                && !this.isTheSpaceFilled(fallingAxis.getPositionX() - 1, fallingAxis.getPositionY())) {
                falling.moveXY(-1, -1);
            } else if (falling.getPositionY() > fallingAxis.getPositionY() && !this.isTheSpaceFilled(falling.getPositionX() + 1, falling.getPositionY())
                && this.isTheSpaceFilled(fallingAxis.getPositionX() - 1, fallingAxis.getPositionY())) {
                fallingAxis.moveXY(1, 1);
            } else if (falling.getPositionX() < fallingAxis.getPositionX()
                && !this.isTheSpaceFilled(fallingAxis.getPositionX(), fallingAxis.getPositionY() - 1)) {
                falling.moveXY(1, -1);
            }
        }
    }
    
    /**
    * The method will turn falling Puyos anticlockwise if possible.
    */
    public void turnLeft() {
        if (!this.areFallingPuyosOnTheGround()) {
            if (falling.getPositionY() < fallingAxis.getPositionY() && fallingAxis.getPositionX() > 0
                && !this.isTheSpaceFilled(fallingAxis.getPositionX() - 1, fallingAxis.getPositionY())) {
                falling.moveXY(-1, 1);
            } else if (falling.getPositionY() < fallingAxis.getPositionY() && !this.isTheSpaceFilled(fallingAxis.getPositionX() + 1, fallingAxis.getPositionY())
                && this.isTheSpaceFilled(fallingAxis.getPositionX() - 1, fallingAxis.getPositionY())) {
                fallingAxis.moveXY(1, 0);
                falling.moveXY(0, 1);
            } else if (falling.getPositionX() > fallingAxis.getPositionX()
                && !this.isTheSpaceFilled(fallingAxis.getPositionX(), fallingAxis.getPositionY() - 1)) {
                falling.moveXY(-1, -1);
            } else if (falling.getPositionY() > fallingAxis.getPositionY() && fallingAxis.getPositionX() < width - 1
                && !this.isTheSpaceFilled(fallingAxis.getPositionX() + 1, fallingAxis.getPositionY())) {
                falling.moveXY(1, -1);
            } else if (falling.getPositionY() > fallingAxis.getPositionY() && !this.isTheSpaceFilled(falling.getPositionX() - 1, falling.getPositionY())
                && this.isTheSpaceFilled(fallingAxis.getPositionX() + 1, fallingAxis.getPositionY())) {
                fallingAxis.moveXY(-1, 1);
            } else if (falling.getPositionX() < fallingAxis.getPositionX()
                && !this.isTheSpaceFilled(fallingAxis.getPositionX(), fallingAxis.getPositionY() + 1)) {
                falling.moveXY(1, 1);
            }
        }
    }
    
    /**
    * The method tells if both of the falling Puyos are on the ground.

    * @return boolean value that tells if Puyos are on the ground or not.
    */
    public boolean areFallingPuyosOnTheGround() {
        if (isTheSpaceFilled(falling.getPositionX(), falling.getPositionY()) 
            || isTheSpaceFilled(fallingAxis.getPositionX(), fallingAxis.getPositionY())) {
            return true;
        }
        return false;
    }
    
    /**
    * The method updates the FilledMap according to the current list puyos.
    */
    public void updateFilled() {
        // This is more convenient than just calling filledMap's method,
        // because you don't have to input parameters 
        filledMap.updateFilled(this.puyos, this.falling, this.fallingAxis);
    }
    
    /**
    * The method returns a Puyo that is horizontally in the center of the screen.
    * The colour is random.
    * 
    * @return Puyo that is randomly coloured
    */
    public Puyo randomPuyo() {
        Colour vari;
        Random arpoja = new Random();
        int luku = arpoja.nextInt(9);
        
        if (luku >= 0 && luku < 2) {
            vari = Colour.RED;
        } else if (luku >= 2 && luku < 4) {
            vari = Colour.BLUE;
        } else if (luku >= 4 && luku < 6) {
            vari = Colour.YELLOW;
        } else if (luku >= 6 && luku < 8) {
            vari = Colour.GREEN;
        } else {
            vari = Colour.PURPLE;
        }
        
        Puyo puyo = new Puyo((int) (width / 2) - 1, 0, vari);
        return puyo;
    }
    
    /**
    * The method sets random Puyos to both falling and fallingAxis.
    */
    public void setPair() {
        falling = this.randomPuyo();
        fallingAxis = this.randomPuyo();
        fallingAxis.moveY(1);
        puyos.add(falling);
        puyos.add(fallingAxis);
    }
    
    /**
    * The method checks if a space (x, y) is filled.
    * 
    * @param   x   The x coordinate
    * @param   y   The y coordinate   
    * @return boolean that tells if the space is filled
    */
    public boolean isTheSpaceFilled(int x, int y) {
        return filledMap.isTheSpaceFilled(x, y);
    }
    
    /**
    * The method finds a chain that contains the Puyo in the position (x, y).
    * If there isn't a chain, it returns a list that only contains one Puyo.
    * @param   x   The x-coordinate
    * @param   y   The y-coordinate
    * @return ArrayList that contains the Puyo in a chain
    */
    public ArrayList<Puyo> findChain(int x, int y) {
        ArrayList<Puyo> list = new ArrayList<>();
        list.add(this.findPuyo(x, y));
        
        for (int i = 0; i < 6; i++) {
            int size = list.size();
            for (int j = 0; j < size; j++) {
                Puyo puyo = list.get(j);
                
                ArrayList<Puyo> adjacent = this.getAdjacentSameColorPuyos(puyo);
                adjacent.stream().forEach(p -> {
                    if (!list.contains(p)) {
                        list.add(p);
                    }
                });
            }    
        }
        return list;     
    }
    
    /**
    * The method finds the adjacent Puyos of a specific Puyo given by the parameter.
    * @param   puyo   The Puyo of which the adjacent Puyos are found.
    * 
    * @return ArrayList that contains the adjacent Puyos
    */
    public ArrayList<Puyo> getAdjacentSameColorPuyos(Puyo puyo) {
        ArrayList<Puyo> list = new ArrayList<>();
        Colour colour = puyo.getColour();        
        int x2 = puyo.getPositionX();
        int y2 = puyo.getPositionY();

        if (x2 - 1 >= 0 && isTheSpaceFilled(x2 - 1, y2) && this.findPuyo(x2 - 1, y2).getColour() == colour && !list.contains(this.findPuyo(x2 - 1, y2))) {
            list.add(this.findPuyo(x2 - 1, y2));
        }
        if (x2 + 1 < width && isTheSpaceFilled(x2 + 1, y2) && this.findPuyo(x2 + 1, y2).getColour() == colour && !list.contains(this.findPuyo(x2 + 1, y2))) {
            list.add(this.findPuyo(x2 + 1, y2));
        }
        if (y2 + 1 < height && isTheSpaceFilled(x2, y2 + 1) && this.findPuyo(x2, y2 + 1).getColour() == colour && !list.contains(this.findPuyo(x2, y2 + 1))) {
            list.add(this.findPuyo(x2, y2 + 1));
        }
        if (y2 - 1 >= 0 && isTheSpaceFilled(x2, y2 - 1) && this.findPuyo(x2, y2 - 1).getColour() == colour && !list.contains(this.findPuyo(x2, y2 - 1))) {
            list.add(this.findPuyo(x2, y2 - 1));
        }
        return list;
    }
    
    /**
    * The method finds a Puyo that is in the position (x, y).
    * @param   x   The x-coordinate
    * @param   y   The y-coordinate
    * @return   Puyo that is in the position (x, y)
    */
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
        return new Puyo(x, y, Colour.EMPTY);
    }
    
    /**
    * The method destroys a chain if the chain has at least four Puyos.
    * @param   list   An ArrayList containing the Puyos.
    */
    public void destroyChain(ArrayList<Puyo> list) {
        if (list.size() >= 4) {
            //It's convenient for testing if you can see the Puyos that were destroyed
            System.out.println("Tuhotut: " + list);
            list.stream().distinct().forEach(puyo -> {
                this.points += 10;
                puyos.remove(puyo);
            });
        }
    }
    
    /**
    * The method checks if the position in the center of the top row is free.
    * If not, the game is over.
    * @return boolean that tells if the game is over
    */
    public boolean gameOver() {
        if (this.isTheSpaceFilled((int) (width / 2) - 1, 0)) {
            return true;
        } else {
            return false;
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
    /**
    * The method adds a Puyo to the list puyos. The main use of this method is in testing.
    * @param   puyo   The Puyo that is added to the list

    */
    public void addPuyo(Puyo puyo) {
        this.puyos.add(puyo);
    }
    public int getPoints() {
        return this.points;
    }
    
    /**
    * The method adds points to the current score.
    * @param   amount   The amount of points to be added

    */
    public void addPoints(int amount) {
        this.points += amount;
    }
    
    
}