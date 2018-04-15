import domain.Pelitilanne;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PelitilanneTest {
    
    Pelitilanne tilanne;
    
    public PelitilanneTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        tilanne = new Pelitilanne(6, 13);
    }
    
    @After
    public void tearDown() {
    }

    //HUOM. Tällä hetkellä lähinnä PuyoTest-luokassa on testejä!
    
    @Test
    public void pointsInTheBeginningZero() {
        assertEquals(0, tilanne.getPoints());
    }
    
    @Test
    public void WidthIsRight() {
        assertEquals(6, tilanne.getWidth());
    }
    
    @Test
    public void heightIsRight() {
        assertEquals(13, tilanne.getHeight());
    }
}
