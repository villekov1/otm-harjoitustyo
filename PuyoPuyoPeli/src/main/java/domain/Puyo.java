package domain;

import java.util.Random;

/**
 * A class that represent a Puyo that has a position and a colour.
 */
public class Puyo {
    private int positionX;
    private int positionY;
    private Colour colour;
    
    /**
    * A constructor of the class Puyo.
    * @param   positionX   The x-coordinate
    * @param   positionY   The y-coordinate
    * @param   colour      The colour of a Puyo
    */
    public Puyo(int positionX, int positionY, Colour colour) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.colour = colour;
    }
    /**
    * A constructor of the class Puyo.
    * Note that this sets the x-coordinate as 2 and y-coordinate as 0.
    * @param   colour   The colour of the Puyo
    */
    public Puyo(Colour colour) {
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

    public Colour getColour() {
        return colour;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
    
    /**
    * The method moves the Puyo horizontally.
    * @param   transition   The amount of movement
    */
    public void moveX(int transition) {
        this.positionX += transition;
    }
    
    /**
    * The method moves the Puyo vertically
    * @param   transition   The amount of movement
    */
    public void moveY(int transition) {
        this.positionY += transition;
    }
    
    /**
    * The method moves the Puyo both horizontally and vertically
    * @param   transitionX   The amount of horizontal movement
    * @param   transitionY   The amount of vertical movement
    */
    public void moveXY(int transitionX, int transitionY) {
        this.positionX += transitionX;
        this.positionY += transitionY;
    }

    public void setColour(Colour colour) {
        //Tälle ei pitäisi olla mitään tarvetta, mutta tehdään se kuitenkin varmuuden vuoksi
        this.colour = colour;
    }
    
    @Override
    public String toString() {
        return "(" + this.getPositionX() + ", " + this.getPositionY() + "), " + this.getColour();
    }
    @Override
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
