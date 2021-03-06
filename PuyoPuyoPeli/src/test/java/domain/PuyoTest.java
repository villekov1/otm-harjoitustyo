package domain;

import domain.Puyo;
import domain.Colour;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PuyoTest {
    
    Puyo puyo;
    
    public PuyoTest() {
    }
    
    
    @Before
    public void setUp() {
        puyo = new Puyo(5, 10, Colour.RED);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void getPositionXWorks() {      
        assertEquals(5, puyo.getPositionX());
    }
    
    @Test
    public void getPositionYWorks() {
        assertEquals(10, puyo.getPositionY());
    }
    
    @Test
    public void moveXWorks() {
        puyo.moveX(-2);
        assertEquals(3, puyo.getPositionX());
    }
    
    @Test
    public void moveYWorks() {
        puyo.moveY(2);
        assertEquals(12, puyo.getPositionY());
    }
    
    @Test
    public void moveXYWorks1() {
        puyo.moveXY(-1, 2);
        assertEquals(4, puyo.getPositionX()); 
    }
    
    @Test
    public void moveXYWorks2() {
        puyo.moveXY(-1, 2);
        assertEquals(12, puyo.getPositionY()); 
    }
    
    @Test
    public void getColourWorks1() {
        assertEquals(Colour.RED, puyo.getColour());
    }
    @Test
    public void getColoursWorks2() {
        Puyo puyo2 = new Puyo(Colour.BLUE);
        assertEquals(Colour.BLUE, puyo2.getColour());
    }
    
    @Test
    public void equalsReturnsTrueIfObjectsAreTheSame() {
        Puyo puyo2 = new Puyo(5, 10, Colour.RED);
        
        assertEquals(true, puyo.equals(puyo2));
    }
    
    @Test
    public void equalsReturnsFalseIfObjectsDifferent() {
        Puyo puyo2 = new Puyo(5, 9, Colour.RED);
        
        assertEquals(false, puyo.equals(puyo2));
    }
    
    @Test
    public void equalsReturnsFalseIfObjectsDifferent2() {
        Puyo puyo2 = new Puyo(3, 10, Colour.RED);
        
        assertEquals(false, puyo.equals(puyo2));
    }
    
    @Test
    public void equalsReturnsFalseIfObjectsDifferent3() {
        Puyo puyo2 = new Puyo(3, 9, Colour.YELLOW);
        
        assertEquals(false, puyo.equals(puyo2));
    }
    
    @Test
    public void equalsReturnsFalseIfObjectsDifferent4() {
        Puyo puyo2 = new Puyo(3, 9, Colour.RED);
        
        assertEquals(false, puyo.equals(puyo2));
    }
    
    @Test
    public void equalsReturnsFalseIfObjectsDifferent5() {
        Puyo puyo2 = new Puyo(5, 9, Colour.BLUE);
        
        assertEquals(false, puyo.equals(puyo2));
    }
    
    @Test
    public void equalsReturnsFalseIfObjectsDifferent6() {
        Puyo puyo2 = new Puyo(5, 10, Colour.PURPLE);
        
        assertEquals(false, puyo.equals(puyo2));
    }
    
    @Test
    public void equalsReturnsFalseIfComparableNotPuyo() {
        Score score = new Score(2, 1000, "Minä");
        
        assertEquals(false, puyo.equals(score));
    }
    
    @Test
    public void toStringWorks() {
        assertEquals("(5, 10), RED", puyo.toString());
    }
    
    @Test
    public void setColourWorks(){
        puyo.setColour(Colour.GREEN);
        assertEquals(Colour.GREEN, puyo.getColour());
    }
    
    @Test
    public void setPositionXWorks(){
        puyo.setPositionX(21);
        assertEquals(21, puyo.getPositionX());
    }
    
    @Test
    public void setPositionYWorks(){
        puyo.setPositionY(17);
        assertEquals(17, puyo.getPositionY());
    }
    
    
}
