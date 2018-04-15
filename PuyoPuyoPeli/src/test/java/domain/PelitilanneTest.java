package domain;

import domain.Pelitilanne;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PelitilanneTest {
    
    Pelitilanne situation;
    
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
        situation = new Pelitilanne(6, 13);
    }
    
    @After
    public void tearDown() {
    }

    //HUOM. Tällä hetkellä lähinnä PuyoTest-luokassa on testejä!
    
    @Test
    public void pointsInTheBeginningZero() {
        assertEquals(0, situation.getPoints());
    }
    
    @Test
    public void WidthIsRight() {
        assertEquals(6, situation.getWidth());
    }
    
    @Test
    public void heightIsRight() {
        assertEquals(13, situation.getHeight());
    }
    
    @Test
    public void twoPuyosInTheBeginning() {
        assertEquals(2, situation.getPuyos().size());
    }
    
    @Test
    public void updateDropsTheFallingPuyo(){
        situation.update();
        
        assertEquals(1, situation.getFalling().getPositionY());
    }
    
    @Test
    public void updateDropsTheFallingAxis(){
        situation.update();
        
        assertEquals(2, situation.getFallingAxis().getPositionY());
    }
    
    @Test
    public void addPuyoWorks(){
        situation.addPuyo(new Puyo(5, 12, Vari.YELLOW));
        
        assertEquals(3, situation.getPuyos().size());
    }
    @Test
    public void updateFilledWorks(){
        situation.addPuyo(new Puyo(2, 10, Vari.BLUE));
        situation.updateFilled();
        
        assertEquals(true, situation.isTheSpaceFilled(2, 10));
        
    }
    
    @Test
    public void updateFilledWorks2(){
        situation.addPuyo(new Puyo(3, 11, Vari.GREEN));
        situation.updateFilled();
        
        assertEquals(true, situation.isTheSpaceFilled(3, 11));
    }
    @Test
    public void updateFilledWorks3(){
        situation.addPuyo(new Puyo(3, 11, Vari.GREEN));
        situation.updateFilled();
        
        assertEquals(false, situation.isTheSpaceFilled(1, 10));
    }
    
    @Test
    public void findChainReturnsCorrectChain(){
        situation.addPuyo(new Puyo(3, 12, Vari.YELLOW));
        situation.addPuyo(new Puyo(3, 11, Vari.YELLOW));
        situation.addPuyo(new Puyo(3, 10, Vari.YELLOW));
        situation.addPuyo(new Puyo(3, 9, Vari.YELLOW));
        situation.updateFilled();
        
        List<Puyo> list = situation.findChain(3, 10);
        
        assertEquals(4, list.size());
    }
    
    @Test
    public void findChainReturnsCorrectChain2(){
        situation.addPuyo(new Puyo(2, 12, Vari.RED));
        situation.addPuyo(new Puyo(3, 12, Vari.RED));
        situation.addPuyo(new Puyo(4, 12, Vari.RED));
        situation.addPuyo(new Puyo(5, 12, Vari.RED));
        situation.updateFilled();
        
        List<Puyo> list = situation.findChain(3, 12);
        
        assertEquals(4, list.size());
    }
    
    @Test
    public void findChainReturnsCorrectChain3(){
        situation.addPuyo(new Puyo(2, 12, Vari.PURPLE));
        situation.addPuyo(new Puyo(3, 12, Vari.PURPLE));
        situation.addPuyo(new Puyo(4, 12, Vari.PURPLE));
        situation.addPuyo(new Puyo(4, 11, Vari.PURPLE));
        situation.addPuyo(new Puyo(5, 12, Vari.PURPLE));
        situation.updateFilled();
        
        List<Puyo> list = situation.findChain(3, 12);
        
        assertEquals(5, list.size());
    }
    
    @Test
    public void destroyChainWorksIfChainIsLongEnough(){
        situation.addPuyo(new Puyo(2, 12, Vari.PURPLE));
        situation.addPuyo(new Puyo(3, 12, Vari.PURPLE));
        situation.addPuyo(new Puyo(4, 12, Vari.PURPLE));
        situation.addPuyo(new Puyo(4, 11, Vari.PURPLE));
        situation.updateFilled();
        
        ArrayList<Puyo> list = situation.findChain(3, 12);
        situation.destroyChain(list);
        situation.updateFilled();
        
        assertEquals(false, situation.isTheSpaceFilled(3, 12));
        
    }
    
    @Test
    public void destroyChainDoesNotWorkIfChainIsNotLongEnough(){
        situation.addPuyo(new Puyo(2, 12, Vari.PURPLE));
        situation.addPuyo(new Puyo(3, 12, Vari.PURPLE));
        situation.addPuyo(new Puyo(4, 12, Vari.PURPLE));
        situation.updateFilled();
        
        ArrayList<Puyo> list = situation.findChain(3, 12);
        situation.destroyChain(list);
        situation.updateFilled();
        
        assertEquals(true, situation.isTheSpaceFilled(3, 12));        
    }
}
