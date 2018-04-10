import domain.Puyo;
import domain.Vari;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PuyoTest {
    
    Puyo puyo;
    
    public PuyoTest() {
    }
    
    
    @Before
    public void setUp() {
        puyo = new Puyo(5, 10, Vari.PUNAINEN);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void getSijaintiXToimiiOikein() {      
        assertEquals(5, puyo.getSijaintiX());
    }
    
    @Test
    public void getSijaintiYToimiiOikein(){
        assertEquals(10, puyo.getSijaintiY());
    }
    
    @Test
    public void siirraXToimii(){
        puyo.siirraX(-2);
        assertEquals(3, puyo.getSijaintiX());
    }
    
    @Test
    public void siirraYToimii(){
        puyo.siirraY(2);
        assertEquals(12, puyo.getSijaintiY());
    }
    @Test
    public void getVariToimii1(){
        assertEquals(Vari.PUNAINEN, puyo.getVari());
    }
    @Test
    public void getVariToimii2(){
        Puyo puyo2 = new Puyo(Vari.SININEN);
        assertEquals(Vari.SININEN, puyo2.getVari());
    }
    
    @Test
    public void equalsPalauttaaTruenJosPuyotSamanlaisia(){
        Puyo puyo2 = new Puyo(5, 10, Vari.PUNAINEN);
        
        assertEquals(true, puyo.equals(puyo2));
    }
    
    @Test
    public void equalsPalauttaaFalsenJosPuyotErilaisia(){
        Puyo puyo2 = new Puyo(5, 9, Vari.SININEN);
        
        assertEquals(false, puyo.equals(puyo2));
    }
    
    @Test
    public void toStringToimiiOikein(){
        assertEquals("(5, 10), PUNAINEN", puyo.toString());
    }
    
    
}
