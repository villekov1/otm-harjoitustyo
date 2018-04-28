/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ville
 */
public class FilledMapTest {
    
    FilledMap filled;
    
    public FilledMapTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        filled = new FilledMap(6, 13);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void updateFilledWorks () {
        ArrayList<Puyo> list = new ArrayList<>();
        Puyo falling = new Puyo(2, 0, Colour.BLUE);
        Puyo fallingAxis = new Puyo(2, 1, Colour.GREEN);
        
        list.add(new Puyo(4, 10, Colour.PURPLE));
        list.add(new Puyo(3, 7, Colour.PURPLE));
        list.add(falling);
        list.add(fallingAxis);
        filled.updateFilled(list, falling, fallingAxis);
        
        boolean bothAreFilled = filled.isTheSpaceFilled(4, 10) && filled.isTheSpaceFilled(3, 7);
        assertEquals(true, bothAreFilled);
    }
    
    @Test
    public void updateFilledWorks2 () {
        ArrayList<Puyo> list = new ArrayList<>();
        Puyo falling = new Puyo(2, 0, Colour.BLUE);
        Puyo fallingAxis = new Puyo(2, 1, Colour.GREEN);
        
        list.add(new Puyo(4, 10, Colour.PURPLE));
        list.add(new Puyo(3, 7, Colour.PURPLE));
        list.add(falling);
        list.add(fallingAxis);
        filled.updateFilled(list, falling, fallingAxis);
        
        boolean bothAreFilled = filled.isTheSpaceFilled(4, 10) && filled.isTheSpaceFilled(2, 7);
        assertEquals(false, bothAreFilled);
    }
    
    @Test
    public void isTheSpaceFilledReturnsTrue () {
        ArrayList<Puyo> list = new ArrayList<>();
        Puyo falling = new Puyo(Colour.BLUE);
        Puyo fallingAxis = new Puyo(Colour.GREEN);
        
        list.add(new Puyo(4, 10, Colour.PURPLE));
        filled.updateFilled(list, falling, fallingAxis);
        
        assertEquals(true, filled.isTheSpaceFilled(4, 10));
    }
    
    @Test
    public void isTheSpaceFilledReturnsFalse () {
        ArrayList<Puyo> list = new ArrayList<>();
        Puyo falling = new Puyo(Colour.BLUE);
        Puyo fallingAxis = new Puyo(Colour.GREEN);
        
        list.add(new Puyo(4, 10, Colour.PURPLE));
        filled.updateFilled(list, falling, fallingAxis);
        
        assertEquals(false, filled.isTheSpaceFilled(3, 10));
    }
    
    @Test
    public void isTheSpaceFilledReturnsFalseWhenTheSpaceHasFallingPuyoOnIt1() {
        ArrayList<Puyo> list = new ArrayList<>();
        Puyo falling = new Puyo(2, 0, Colour.BLUE);
        Puyo fallingAxis = new Puyo(2, 1, Colour.GREEN);
        
        list.add(new Puyo(4, 10, Colour.PURPLE));
        list.add(falling);
        list.add(fallingAxis);
        filled.updateFilled(list, falling, fallingAxis);
        
        assertEquals(false, filled.isTheSpaceFilled(2, 0));
    }
    
    @Test
    public void isTheSpaceFilledReturnsFalseWhenTheSpaceHasFallingPuyoOnIt2() {
        ArrayList<Puyo> list = new ArrayList<>();
        Puyo falling = new Puyo(2, 0, Colour.BLUE);
        Puyo fallingAxis = new Puyo(2, 1, Colour.GREEN);
        
        list.add(new Puyo(4, 10, Colour.PURPLE));
        list.add(falling);
        list.add(fallingAxis);
        filled.updateFilled(list, falling, fallingAxis);
        
        assertEquals(false, filled.isTheSpaceFilled(2, 1));
    }
    
    @Test
    public void isTheSpaceFilledReturnsTrueIfOutOfBounds1(){
        assertEquals(true, filled.isTheSpaceFilled(-1, 0));
    }
    
    @Test
    public void isTheSpaceFilledReturnsTrueIfOutOfBounds2() {
        assertEquals(true, filled.isTheSpaceFilled(3, 14));
    }
    
    @Test
    public void isTheSpaceFilledReturnsTrueIfOutOfBounds3(){
        assertEquals(true, filled.isTheSpaceFilled(7, 0));
    }
    
    @Test
    public void isTheSpaceFilledReturnsTrueIfOutOfBounds4() {
        assertEquals(true, filled.isTheSpaceFilled(3, -2));
    }
}
