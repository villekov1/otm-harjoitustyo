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
public class TulosTest {
    
    Tulos tulos;
    Tulos tulos2;
    
    public TulosTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        tulos = new Tulos(1, 1900, "Ville");
        tulos2 = new Tulos(2, 980, "Tuomas");
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
}
