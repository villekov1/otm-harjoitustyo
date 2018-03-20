
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {
    
    Kassapaate kassa;
    Maksukortti kortti;
    
    public KassapaateTest() {
    }
    
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(250);
    }
    
//    @After
//    public void tearDown() {
//    }
    
    @Test
    public void luodunKassanRahamaaraOikein(){
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void myytyjaEdullisiaLounaitaAlussaNolla(){
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void myytyjaMaukkaitaLounaitaAlussaNolla(){
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    //Käteismaksut toimivat
    @Test
    public void kassassaOlevaRahamaaraKasvaaJosKateismaksuEdulliseenRiittava(){
        kassa.syoEdullisesti(250);
        
        assertEquals(100240, kassa.kassassaRahaa());
    }
    @Test
    public void kassassaOlevaRahamaaraKasvaaJosKateismaksuMaukkaaseenRiittava(){
        kassa.syoMaukkaasti(410);
        
        assertEquals(100400, kassa.kassassaRahaa());
    }
    
    @Test
    public void kateisPalautusOikeinEdullisessa(){
        assertEquals(10, kassa.syoEdullisesti(250));
    }
    
    @Test
    public void kateisPalautusOikeinMaukkaassa(){
        assertEquals(30, kassa.syoMaukkaasti(430));
    }
    
    @Test
    public void myytyjenLounaidenMaaraKasvaaEdullisesti(){
        kassa.syoEdullisesti(250);
        
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void myytyjenLounaidenMaaraKasvaaMaukkaasti(){
        kassa.syoMaukkaasti(410);
        
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kassassaOlevaRahamaaraEiMuutuJosKateisMaksuEiRiittavaEdulliseen(){
        kassa.syoEdullisesti(220);
        
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kassassaOlevaRahamaaraEiMuutuJosKateisMaksuEiRiittavaMaukkaaseen(){
        kassa.syoMaukkaasti(270);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void rahatPalautetaanOikeinEdullisesti(){
        assertEquals(210, kassa.syoEdullisesti(210));
    }
    
    @Test
    public void rahatPalautetaanOikeinMaukkaasti(){
        assertEquals(300, kassa.syoMaukkaasti(300));
    }
    
    //Maksukortin toiminta
    @Test
    public void kortiltaVeloitetaanOikeaSummaJosKortillaTarpeeksiRahaaEdulliseen(){
        kassa.syoEdullisesti(kortti);
        
        assertEquals(10, kortti.saldo());
    }
    @Test
    public void kortiltaVeloitetaanOikeaSummaJosKortillaTarpeeksiRahaaMaukkaaseen(){
        kortti.lataaRahaa(160);
        //Rahaa nyt kortilla 4.10
        kassa.syoMaukkaasti(kortti);
        
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void palauttaaArvonTrueJosKortillaRahaaEdulliseen(){
        assertEquals(true, kassa.syoEdullisesti(kortti));
    }
    
    @Test
    public void palauttaaArvonTrueJosKortillaRahaaMaukkaaseen(){
        kortti.lataaRahaa(170);
        
        assertEquals(true, kassa.syoMaukkaasti(kortti));
    }
    
    @Test
    public void myytyjenLounaidenMaaraKasvaaJosKortillaRahaaEdulliseen(){
        kassa.syoEdullisesti(kortti);
        
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void myytyjenLounaidenMaaraKasvaaJosKortillaRahaaMaukkaaseen(){
        kortti.lataaRahaa(800);
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        
        assertEquals(2, kassa.maukkaitaLounaitaMyyty());
    }
   
    @Test
    public void kortinRahamaaraEiMuutuJosKortillaEiRahaaEdulliseen(){
        kortti.otaRahaa(150);
        //Nyt kortilla saldoa 100
        kassa.syoEdullisesti(kortti);
        
        assertEquals(100, kortti.saldo());
    }
    @Test
    public void kortinRahamaaraEiMuutuJosKortillaEiRahaaMaukkaaseen(){
        kassa.syoMaukkaasti(kortti);
        
        assertEquals(250, kortti.saldo());
    }
    
    @Test
    public void myytyjenEdullistenMaaraEiKasvaJosKortillaEiRahaa(){
        kortti.otaRahaa(100);
        kassa.syoEdullisesti(kortti);
        
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void myytyjenMaukkaidenMaaraEiKasvaJosKortillaEiRahaa(){
        kassa.syoMaukkaasti(kortti);
        
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void palautetaanFalseJosKortillaEiRahaaEdulliseen(){
        kortti.otaRahaa(100);
        //Kortilla saldoa 150
        
        assertEquals(false, kassa.syoEdullisesti(kortti));
    }
    
    @Test
    public void palautetaanFalseJosKortillaEiRahaaMaukkaaseen(){       
        assertEquals(false, kassa.syoMaukkaasti(kortti));
    }
    
    @Test
    public void kassassaOlevaRahamaaraEiMuutuKortillaOstettaessaEdullinen(){
        kassa.syoEdullisesti(kortti);
        
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kassassaOlevaRahamaaraEiMuutuKortillaOstettaessaMaukas(){
        kortti.lataaRahaa(200);
        kassa.syoMaukkaasti(kortti);
        
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    //Kortin lataus toimii
    @Test
    public void kortinSaldoKasvaaRahaaLadattaessa(){
        kassa.lataaRahaaKortille(kortti, 150);
        
        assertEquals(400, kortti.saldo());
    }
    
    @Test
    public void kortinSaldoEiKasvaLadattaessaNegatiivistaSummaa(){
        //Tämä itse asiassa varmistetaan myös MaksukorttiTestin puolella
        kassa.lataaRahaaKortille(kortti, -50);
        
        assertEquals(250, kortti.saldo());
    }
    
    @Test
    public void kassassaOlevaRahamaaraKasvaaKorttiaLadattaessa(){
        kassa.lataaRahaaKortille(kortti, 1000);
        
        assertEquals(101000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kassassaOlevaRahamaaraEiMuutuLadattaessaNegatiivistaSummaa(){
        kassa.lataaRahaaKortille(kortti, -150);
        
        assertEquals(100000, kassa.kassassaRahaa());
    }
}
