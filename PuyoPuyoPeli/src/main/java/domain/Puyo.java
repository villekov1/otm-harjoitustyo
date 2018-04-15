package domain;

import java.util.Random;

public class Puyo {
    private int positionX;
    private int positionY;
    private Vari colour;
    
    public Puyo(int positionX, int positionY, Vari colour) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.colour = colour;
    }
    public Puyo(Vari colour) {
        this.colour = colour;
        this.positionX = 2;
        this.positionY = 0;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public Vari getColour() {
        return colour;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
    
    public void moveX(int transition) {
        this.positionX += transition;
    }
    
    public void moveY(int transition) {
        this.positionY += transition;
    }

    public void setColour(Vari colour) {
        //Tälle ei pitäisi olla mitään tarvetta, mutta tehdään se kuitenkin varmuuden vuoksi
        this.colour = colour;
    }
    
    public String toString() {
        return "(" + this.getPositionX() + ", " + this.getPositionY() + "), " + this.getColour();
    }
    public boolean equals(Object comparable) {
        if (this == comparable) {
            return true;
        }

        if (!(comparable instanceof Puyo)) {
            return false;
        }

        Puyo comparedPuyo = (Puyo) comparable;

        if (this.positionX == comparedPuyo.positionX &&
                this.positionY == comparedPuyo.positionY &&
                this.colour == comparedPuyo.colour) {
            return true;
        }

        return false;
    }
    
}
