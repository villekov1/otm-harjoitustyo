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
    public void gameOverWorksIfTheWidthIsUneven() {
        GameLogic situation2 = new GameLogic(7, 13);
        situation2.addPuyo(new Puyo(3, 0, Colour.RED));
        situation2.updateFilled();
        
        assertEquals(true, situation2.gameOver());
    }
    
    @Test
    public void gameOverReturnsFalseIfGameIsNotOver() {
        situation.addPuyo(new Puyo(1, 0, Colour.BLUE));
        situation.updateFilled();
        
        assertEquals(false, situation.gameOver());
    }
    
    @Test
    public void addPointsWorks() {
        situation.addPoints(55);
        
        assertEquals(55, situation.getPoints());
    }
    
    @Test
    public void hardDropWorks() {
        situation.hardDrop();
        
        boolean onTheGround = (situation.getFalling().getPositionY() == 11 && situation.getFallingAxis().getPositionY() == 12);
        assertEquals(true, onTheGround);
    }
    
    @Test
    public void dropFallingWorksAsIntendedWhenThePuyosAreUpsideDown() {
        situation.turnLeft();
        situation.turnLeft();
        
        situation.hardDrop();
        situation.updateFilled();
        
        boolean fallingPuyosOnTheGround = (situation.getFalling().getPositionY() == 12 && situation.getFallingAxis().getPositionY() == 11);
        assertEquals(true, fallingPuyosOnTheGround);
    }
    
    @Test
    public void dropAllWorks1() {
        situation.addPuyo(new Puyo(1, 5, Colour.RED));
        situation.addPuyo(new Puyo(4, 2, Colour.PURPLE));
        situation.updateFilled();
        situation.dropAll();
        
        boolean bothOnTheGround = (situation.isTheSpaceFilled(1, 12) && situation.isTheSpaceFilled(4, 12));
        assertEquals(true, bothOnTheGround);
    }
    
    @Test
    public void dropAllWorks2() {
        situation.addPuyo(new Puyo(1, 5, Colour.RED));
        situation.addPuyo(new Puyo(1, 8, Colour.PURPLE));
        situation.updateFilled();
        situation.dropAll();
        
        boolean bothOnTheGround = (situation.isTheSpaceFilled(1, 12) && situation.isTheSpaceFilled(1, 11));
        assertEquals(true, bothOnTheGround);
    }
    
    @Test
    public void dropAllWorks3() {
        situation.addPuyo(new Puyo(5, 11, Colour.GREEN));
        situation.addPuyo(new Puyo(5, 12, Colour.YELLOW));
        situation.updateFilled();
        situation.dropAll();
        
        boolean bothOnTheGround = (situation.isTheSpaceFilled(5, 12) && situation.isTheSpaceFilled(5, 11));
        assertEquals(true, bothOnTheGround);
    }
    
    @Test
    public void areFallingPuyosOnTheGroundReturnsTrue() {
        situation.getFallingAxis().moveY(11);
        situation.getFallingAxis().moveY(11);
        situation.updateFilled();
        
        assertEquals(true, situation.areFallingPuyosOnTheGround());
    }
    
    @Test
    public void turnLeftReturnsToTheOriginalPositionEventually() {
        situation.update();
        
        int fallingX = situation.getFalling().getPositionX();
        int fallingY = situation.getFalling().getPositionY();
        int fallingAxisX = situation.getFallingAxis().getPositionX();
        int fallingAxisY = situation.getFallingAxis().getPositionY();
        
        situation.turnLeft();
        situation.turnLeft();
        situation.turnLeft();
        situation.turnLeft();
        
        boolean onTheOriginalPositions = (situation.getFalling().getPositionX() == fallingX)
                && (situation.getFalling().getPositionY() == fallingY)
                && (situation.getFallingAxis().getPositionX() == fallingAxisX)
                && (situation.getFallingAxis().getPositionY() == fallingAxisY);
    }
    
    @Test
    public void turnRightReturnsToTheOriginalPositionEventually() {
        situation.update();
        
        int fallingX = situation.getFalling().getPositionX();
        int fallingY = situation.getFalling().getPositionY();
        int fallingAxisX = situation.getFallingAxis().getPositionX();
        int fallingAxisY = situation.getFallingAxis().getPositionY();
        
        situation.turnRight();
        situation.turnRight();
        situation.turnRight();
        situation.turnRight();
        
        boolean onTheOriginalPositions = (situation.getFalling().getPositionX() == fallingX)
            && (situation.getFalling().getPositionY() == fallingY)
            && (situation.getFallingAxis().getPositionX() == fallingAxisX)
            && (situation.getFallingAxis().getPositionY() == fallingAxisY);
    }
    
    @Test
    public void turnLeftCancelsTurnRight() {
        situation.turnRight();
        situation.turnLeft();
        
        boolean onTheOriginalPositions = (situation.getFalling().getPositionX() == 2)
            && (situation.getFalling().getPositionY() == 0);
        
        assertEquals(true, onTheOriginalPositions);
    }
    
    @Test
    public void turnRightWorksProperlyAgainsTheWall() {
        situation.getFalling().moveXY(3, 2);
        situation.getFallingAxis().moveXY(3, 2);
        
        situation.turnRight();
        situation.updateFilled();
        
        boolean puyosOnTheCorrectPlaces = situation.isTheSpaceFilled(5, 3) && situation.isTheSpaceFilled(4, 3);
    }
    @Test
    public void turnRightWorksProperlyAgainsTheWall2() {
        situation.getFalling().moveXY(-2, 2);
        situation.getFallingAxis().moveXY(-2, 0);
        
        situation.turnRight();
        situation.updateFilled();
        
        boolean puyosOnTheCorrectPlaces = situation.isTheSpaceFilled(0, 2) && situation.isTheSpaceFilled(1, 2);
    }
    
    @Test
    public void turnLeftWorksProperlyAgainsTheWall() {
        situation.getFalling().moveXY(-2, 2);
        situation.getFallingAxis().moveXY(-2, 2);
        
        situation.turnLeft();
        situation.updateFilled();
        
        boolean puyosOnTheCorrectPlaces = situation.isTheSpaceFilled(0, 3) && situation.isTheSpaceFilled(1, 3);
    }
    @Test
    public void turnLeftWorksProperlyAgainsTheWall2() {
        situation.getFalling().moveXY(3, 2);
        situation.getFallingAxis().moveXY(3, 0);
        
        situation.turnLeft();
        situation.updateFilled();
        
        boolean puyosOnTheCorrectPlaces = situation.isTheSpaceFilled(5, 2) && situation.isTheSpaceFilled(4, 2);
    }
    
    @Test
    public void nextPuyosReturnsTwoPuyos() {
        //Because the Puyos are randomised, we only check that the method returns two Puyos
        ArrayList<Puyo> next = situation.nextPuyos();
        
        assertEquals(2, next.size());
    }
    
    @Test
    public void ifPuyosMeetTheGroundNewPuyosAreSet() {
        Puyo falling = situation.getFalling();
        Puyo axis = situation.getFallingAxis();
        
        situation.hardDrop();
        situation.update();
        
        assertEquals(false, (falling == situation.getFalling() && axis == situation.getFallingAxis()));
    }
    

}
