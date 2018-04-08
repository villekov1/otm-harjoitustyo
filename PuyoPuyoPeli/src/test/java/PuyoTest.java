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
}
