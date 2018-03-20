/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author villekov
 */
public class MaksukorttiTest {
    
    Maksukortti kortti; 
    public MaksukorttiTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() {}
     
    @Test
    public void konstruktoriAsettaaSaldonOikein() {
        assertEquals("Kortilla on rahaa 10.0 euroa", kortti.toString());
    }
    
    @Test
    public void syoEdullisestiVahentaaSaldoaOikein() {
        kortti.syoEdullisesti();

        assertEquals("Kortilla on rahaa 7.5 euroa", kortti.toString());
    }
    
    @Test
    public void syoMaukkaastiVahentaaSaldoaOikein() {
        kortti.syoMaukkaasti();

        assertEquals("Kortilla on rahaa 6.0 euroa", kortti.toString());
    }

    @Test
    public void syoEdullisestiEiVieSaldoaNegatiiviseksi() {
        kortti.syoMaukkaasti();
        kortti.syoMaukkaasti();
        // nyt kortin saldo on 2
        kortti.syoEdullisesti();

        assertEquals("Kortilla on rahaa 2.0 euroa", kortti.toString());
    }
    
    @Test
    public void kortilleVoiLadataRahaa() {
        kortti.lataaRahaa(25);
        assertEquals("Kortilla on rahaa 35.0 euroa", kortti.toString());
    }

    @Test
    public void kortinSaldoEiYlitaMaksimiarvoa() {
        kortti.lataaRahaa(200);
        assertEquals("Kortilla on rahaa 150.0 euroa", kortti.toString());
    }
    
    @Test
    public void syoMaukkaastiEiVieSaldoaNegatiiviseksi(){
        kortti.syoMaukkaasti();
        kortti.syoMaukkaasti();
        //Kortin saldo on 2
        kortti.syoMaukkaasti();
        
        assertEquals("Kortilla on rahaa 2.0 euroa", kortti.toString());
    }
    
    @Test
    public void negatiivisenSummanLataaminenEiMuutaArvoa(){
        kortti.lataaRahaa(-5);
        assertEquals("Kortilla on rahaa 10.0 euroa", kortti.toString());
    }
    
    @Test
    public void kortillaPystyyOstamaanEdullisestiJosRahaaLounaanVerran(){
        kortti.syoEdullisesti();
        kortti.syoEdullisesti();
        kortti.syoEdullisesti();
        //Nyt rahaa on kortilla 2.5 euroa
        
        kortti.syoEdullisesti();
        assertEquals("Kortilla on rahaa 0.0 euroa", kortti.toString());
    }
    
    @Test
    public void kortillaPystyyOstamaanMaukkaastiJosRahaaLounaanVerran(){
        kortti.syoMaukkaasti();
        kortti.syoMaukkaasti();
        kortti.lataaRahaa(2);
        //Nyt kortilla on rahaa 4.0 euroa
        
        kortti.syoMaukkaasti();
        
        assertEquals("Kortilla on rahaa 0.0 euroa", kortti.toString());
    }
}
