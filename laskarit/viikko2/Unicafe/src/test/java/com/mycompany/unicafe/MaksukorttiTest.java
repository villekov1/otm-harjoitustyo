package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
        //Huom. kortilla aluksi vain kymmenen senttiä
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoAlussaOikein(){
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void rahanLataaminenKasvattaaArvoa(){
        kortti.lataaRahaa(100);
        assertEquals("saldo: 1.10", kortti.toString());
    }
    
    @Test
    public void rahanOttaminenToimiiJosRahaaEiRiittavasti(){
        kortti.otaRahaa(100);
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void rahanOttaminenToimiiJosRahaaRiittavasti(){
        kortti.lataaRahaa(200);
        kortti.otaRahaa(105);
        //Rahaa 1 euro ja 5 senttiä
        
        assertEquals("saldo: 1.5", kortti.toString());
    }
    
    @Test
    public void rahanOttaminenPalauttaaTruen(){
        assertEquals(true, kortti.otaRahaa(5));
    }
    
    @Test
    public void rahanOttaminenPalauttaaFalsen(){
        assertEquals(false, kortti.otaRahaa(20));
    }
}
