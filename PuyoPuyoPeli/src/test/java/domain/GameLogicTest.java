package domain;

import domain.GameLogic;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameLogicTest {
    
    GameLogic situation;
    
    public GameLogicTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        situation = new GameLogic(6, 13);
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
    public void updateDropsTheFallingPuyo() {
        situation.update();
        
        assertEquals(1, situation.getFalling().getPositionY());
    }
    
    @Test
    public void updateDropsTheFallingAxis() {
        situation.update();
        
        assertEquals(2, situation.getFallingAxis().getPositionY());
    }
    
    @Test
    public void addPuyoWorks() {
        situation.addPuyo(new Puyo(5, 12, Colour.YELLOW));
        
        assertEquals(3, situation.getPuyos().size());
    }
    @Test
    public void updateFilledWorks() {
        situation.addPuyo(new Puyo(2, 10, Colour.BLUE));
        situation.updateFilled();
        
        assertEquals(true, situation.isTheSpaceFilled(2, 10));
        
    }
    
    @Test
    public void updateFilledWorks2() {
        situation.addPuyo(new Puyo(3, 11, Colour.GREEN));
        situation.updateFilled();
        
        assertEquals(true, situation.isTheSpaceFilled(3, 11));
    }
    @Test
    public void updateFilledWorks3() {
        situation.addPuyo(new Puyo(3, 11, Colour.GREEN));
        situation.updateFilled();
        
        assertEquals(false, situation.isTheSpaceFilled(1, 10));
    }
    
    @Test
    public void findChainReturnsCorrectChain() {
        situation.addPuyo(new Puyo(3, 12, Colour.YELLOW));
        situation.addPuyo(new Puyo(3, 11, Colour.YELLOW));
        situation.addPuyo(new Puyo(3, 10, Colour.YELLOW));
        situation.addPuyo(new Puyo(3, 9, Colour.YELLOW));
        situation.updateFilled();
        
        List<Puyo> list = situation.findChain(3, 10);
        
        assertEquals(4, list.size());
    }
    
    @Test
    public void isTheSpaceFilledReturnsTrueIfTheSpaceIsFilled() {
        situation.addPuyo(new Puyo(5, 11, Colour.YELLOW));
        situation.updateFilled();
        
        assertEquals(true, situation.isTheSpaceFilled(5, 11));
        
    }
    @Test
    public void isTheSpaceFilledReturnsTrueIfOutOfBounds(){
        assertEquals(true, situation.isTheSpaceFilled(-1, 0));
    }
    
    @Test
    public void isTheSpaceFilledReturnsTrueIfOutOfBounds2() {
        assertEquals(true, situation.isTheSpaceFilled(3, 14));
    }
    
    @Test
    public void findPuyoReturnsEmptyPuyoIfNotInTheListPuyos() {
        Puyo puyo1 = new Puyo(5, 11, Colour.RED);
        Puyo puyo2 = new Puyo(2, 8, Colour.BLUE);
        
        situation.addPuyo(puyo1);
        situation.addPuyo(puyo2);
        
        assertEquals(Colour.EMPTY, situation.findPuyo(3, 7).getColour());
    }
    
    @Test
    public void findChainReturnsCorrectChain2() {
        situation.addPuyo(new Puyo(2, 12, Colour.RED));
        situation.addPuyo(new Puyo(3, 12, Colour.RED));
        situation.addPuyo(new Puyo(4, 12, Colour.RED));
        situation.addPuyo(new Puyo(5, 12, Colour.RED));
        situation.updateFilled();
        
        List<Puyo> list = situation.findChain(3, 12);
        
        assertEquals(4, list.size());
    }
    
    @Test
    public void findChainReturnsCorrectChain3() {
        situation.addPuyo(new Puyo(2, 12, Colour.PURPLE));
        situation.addPuyo(new Puyo(3, 12, Colour.PURPLE));
        situation.addPuyo(new Puyo(4, 12, Colour.PURPLE));
        situation.addPuyo(new Puyo(4, 11, Colour.PURPLE));
        situation.addPuyo(new Puyo(5, 12, Colour.PURPLE));
        situation.updateFilled();
        
        List<Puyo> list = situation.findChain(3, 12);
        
        assertEquals(5, list.size());
    }
    
    @Test
    public void destroyChainWorksIfChainIsLongEnough() {
        situation.addPuyo(new Puyo(2, 12, Colour.PURPLE));
        situation.addPuyo(new Puyo(3, 12, Colour.PURPLE));
        situation.addPuyo(new Puyo(4, 12, Colour.PURPLE));
        situation.addPuyo(new Puyo(4, 11, Colour.PURPLE));
        situation.updateFilled();
        
        ArrayList<Puyo> list = situation.findChain(3, 12);
        situation.destroyChain(list);
        situation.updateFilled();
        
        assertEquals(false, situation.isTheSpaceFilled(3, 12));
        
    }
    
    @Test
    public void destroyChainDoesNotWorkIfChainIsNotLongEnough() {
        situation.addPuyo(new Puyo(2, 12, Colour.PURPLE));
        situation.addPuyo(new Puyo(3, 12, Colour.PURPLE));
        situation.addPuyo(new Puyo(4, 12, Colour.PURPLE));
        situation.updateFilled();
        
        ArrayList<Puyo> list = situation.findChain(3, 12);
        situation.destroyChain(list);
        situation.updateFilled();
        
        assertEquals(true, situation.isTheSpaceFilled(3, 12));        
    }
    
    @Test
    public void turnRightWorksCorrectly() {
        situation.getFalling().moveXY(0, 2);
        situation.getFallingAxis().moveXY(0, 2);
        
        situation.turnRight();
        assertEquals(3, situation.getFalling().getPositionY());
    }
    
    @Test
    public void turnRightWorksCorrectly2() {
        situation.getFalling().moveXY(0, 2);
        situation.getFallingAxis().moveXY(0, 2);
        
        situation.turnRight();
        assertEquals(3, situation.getFalling().getPositionX());
    }
    
    @Test
    public void turnLeftWorksCorrectly() {
        situation.getFalling().moveXY(0, 2);
        situation.getFallingAxis().moveXY(0, 2);
        
        situation.turnLeft();
        assertEquals(3, situation.getFalling().getPositionY());
    }
    
    @Test
    public void turnLeftWorksCorrectly2() {
        situation.getFalling().moveXY(0, 2);
        situation.getFallingAxis().moveXY(0, 2);
        
        situation.turnLeft();
        assertEquals(1, situation.getFalling().getPositionX());
    }
    
    @Test
    public void moveRightWorksCorrectly() {
        situation.moveRight();
        
        assertEquals(3, situation.getFallingAxis().getPositionX());
    }
    
    @Test
    public void moveLeftWorksCorrectly() {
        situation.moveLeft();
        
        assertEquals(1, situation.getFallingAxis().getPositionX());
    }
    
    @Test
    public void gameOverReturnsTrueIfGameIsOver() {
        situation.addPuyo(new Puyo(2, 0, Colour.BLUE));
        situation.updateFilled();
        
        assertEquals(true, situation.gameOver());
    }
    
    @Test
    public void gameOverReturnsFalseIfGameIsNotOver() {
        situation.addPuyo(new Puyo(1, 0, Colour.BLUE));
        situation.updateFilled();
        
        assertEquals(false, situation.gameOver());
    }
    
    @Test
    public void addPointsWorks(){
        situation.addPoints(55);
        
        assertEquals(55, situation.getPoints());
    }
}
