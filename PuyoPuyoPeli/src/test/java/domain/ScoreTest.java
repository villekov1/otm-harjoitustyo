/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

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
public class ScoreTest {
    
    Score tulos;
    Score tulos2;
    
    public ScoreTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        tulos = new Score(1, 1900, "Ville");
        tulos2 = new Score(2, 980, "Tuomas");
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void getNameReturnsName1() {
        assertEquals("Ville", tulos.getName());
    }
    
    @Test
    public void getNameReturnsName2() {
        assertEquals("Tuomas", tulos2.getName());
    }
    
    @Test
    public void getScoreReturnsCorrectPoints() {
        assertEquals(1900, tulos.getScore());
    }
    
    @Test
    public void toStringWorks() {
        assertEquals("Ville: 1900", tulos.toString());
    }
    
    @Test
    public void toStringWorks2() {
        assertEquals("Tuomas: 980", tulos2.toString());
    }
    
    @Test
    public void getIdWorks() {
        assertEquals(2, tulos2.getId());
    }
    
    @Test
    public void equalsWorksProperly1() {
        Score score1 = new Score(5, 1000, "Ville");
        Score score2 = new Score(-2, 1000, "Ville");
        
        assertEquals(true, score1.equals(score2));
    }
    
    @Test
    public void equalsWorksProperly2() {
        Score score1 = new Score(5, 2000, "Ville");
        Score score2 = new Score(-2, 1000, "Ville");
        
        assertEquals(false, score1.equals(score2));
    }
    
    @Test
    public void equalsWorksProperly3() {
        // Not a score
        Puyo score1 = new Puyo(5, 10, Colour.BLUE);
        Score score2 = new Score(-2, 1000, "Ville");
        
        assertEquals(false, score2.equals(score1));
    }
    
    @Test
    public void equalsWorksProperly4() {
        // Not a score
        Score score1 = new Score(-2, 1000, "Ville");
        
        assertEquals(true, score1.equals(score1));
    }
}
