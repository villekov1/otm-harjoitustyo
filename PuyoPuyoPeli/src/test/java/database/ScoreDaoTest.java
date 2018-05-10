package database;

import domain.Score;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ScoreDaoTest {
    
    Database database;
    ScoreDao scoreDao;
    
    public ScoreDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        try {
            File dbFile = new File("testit.db");
            if(dbFile.exists()){
                Connection conn = DriverManager.getConnection("jdbc:sqlite:testit.db");
                PreparedStatement stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Tulos("
                    + "id integer PRIMARY KEY, nimi varchar(200), tulos integer)");
                stmt.execute();
                stmt.close();
                conn.close();
                database = new Database("jdbc:sqlite:testit.db");
                scoreDao = new ScoreDao(database);
                return;
            }
        } catch(Exception e) {
            System.out.println("Virhe: "+e.getMessage());
        }
        
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:testit.db")) {
            if (conn != null) {
                System.out.println("Uusi tietokanta on luotu.");
                
                PreparedStatement stmt2 = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Tulos("
                    + "id integer PRIMARY KEY, nimi varchar(200), tulos integer)");
                stmt2.execute();
                stmt2.close();
                conn.close();
            }
        } catch(SQLException e) {
            System.out.println("Virhe: " + e.getMessage());
        }
        
        database = new Database("jdbc:sqlite:testit.db");
        scoreDao = new ScoreDao(database);
    }
    
    @After
    public void tearDown() {
        try{
            Connection conn = DriverManager.getConnection("jdbc:sqlite:testit.db");
            PreparedStatement stmt = conn.prepareStatement("DROP TABLE IF EXISTS Tulos");
            stmt.execute();
            stmt.close();
        } catch (Exception e){
            System.out.println("Jotain meni pieleen tietokantaan tallentamisessa: " + e.getMessage());
        }
    }
    
    @Test
    public void saveWorksProperly() {
        Score replacable = new Score(3, 0, "");
        Score score = new Score(2, 2000, "Ville");
        
        try { 
            replacable = scoreDao.save(score);
        } catch (Exception e) {
            System.out.println("Tallentaminen tietokantaan ei onnistunut: " + e.getMessage());
        }
        
        
        assertEquals("Ville: 2000", replacable.toString());
        
    }
    
    @Test
    public void findAllReturnsEverything() {
        Score score1 = new Score(1, 1000, "Pekka");
        Score score2 = new Score(5, 2012, "Anni");
        Score score3 = new Score(-1, 1921, "Matias");
        List<Score> lista = new ArrayList<>();
        
        try {
            scoreDao.save(score1);
            scoreDao.save(score2);
            scoreDao.save(score3);
        } catch (Exception e) {
            System.out.println("Jokin meni vikaan tietokantaan tallentamisessa: " + e.getMessage());
        }
        
        try {
            lista = scoreDao.findAll();
        } catch (Exception e) {
            System.out.println("Jotain meni pieleen tiedon hakemisessa: " + e.getMessage());
        }
        
        assertEquals(3, lista.size());
    }
    @Test
    public void saveIfNotInTheDatabaseDoesntSaveIfAlreadyInDatabase() {
        Score score1 = new Score(1, 1000, "Pekka");
        List<Score> lista = new ArrayList<>();
        
        try {
            scoreDao.saveIfNotInTheDatabase(score1);
            scoreDao.saveIfNotInTheDatabase(score1);
        } catch (Exception e) {
            System.out.println("Jokin meni vikaan tietokantaan tallentamisessa: " + e.getMessage());
        }
        
        try {
            lista = scoreDao.findAll();
        } catch (Exception e) {
            System.out.println("Jotain meni pieleen tiedon hakemisessa: " + e.getMessage());
        }
        
        assertEquals(1, lista.size());
    }
    @Test
    public void deleteWorksProperly() {
        Score score1 = new Score(1, 1000, "Pekka");
        List<Score> lista = new ArrayList<>();
        
        try {
            scoreDao.save(score1);
        } catch (Exception e) {
            System.out.println("Jokin meni vikaan tietokantaan tallentamisessa: " + e.getMessage());
        }
        
        try {
            scoreDao.delete(1);
        } catch (Exception e) {
            System.out.println("Jokin meni vikaan tiedon poistamisessa: " + e.getMessage());
        }
        
        try {
            lista = scoreDao.findAll();
        } catch (Exception e) {
            System.out.println("Jotain meni pieleen tiedon hakemisessa: " + e.getMessage());
        }
        
        assertEquals(0, lista.size());
    }
    
    @Test
    public void findIdReturnsCorrectId() {
        Score score1 = new Score(1, 1000, "Pekka");
        Score score2 = new Score(5, 2012, "Anni");
        Score score3 = new Score(-1, 1921, "Matias");
        
        try {
            scoreDao.save(score1);
            scoreDao.save(score2);
            scoreDao.save(score3);
        } catch (Exception e) {
            System.out.println("Jotain meni vikaan tiedon tallentamisessa.");
        }
        int id = 0;
        try {
            id = scoreDao.findId(score3);
        } catch (Exception e) {
            System.out.println("Ei voitu löytää id:tä.");
        }
        
        assertEquals(3, id);
    }
    
    @Test
    public void findIdReturnsMinusOneIfScoreIsNotInTheDatabase() {
        Score score1 = new Score(1, 1000, "Pekka");
        Score score2 = new Score(5, 2012, "Anni");
        
        try {
            scoreDao.save(score1);
            scoreDao.save(score2);
        } catch (Exception e) {
            System.out.println("Jotain meni vikaan tiedon tallentamisessa.");
        }
        int id = 0;
        try {
            id = scoreDao.findId(new Score(2, 1999, "Mikko"));
        } catch (Exception e) {
            System.out.println("Ei voitu löytää id:tä.");
        }
        
        assertEquals(-1, id);
    }
    
    @Test
    public void findByIdWorksProperly() {
        Score score1 = new Score(1, 1000, "Pekka");
        Score score2 = new Score(5, 2012, "Anni");
        Score score3 = new Score(-1, 1921, "Matias");
        
        try {
            scoreDao.save(score1);
            scoreDao.save(score2);
            scoreDao.save(score3);
        } catch (Exception e) {
            System.out.println("Jotain meni vikaan tiedon tallentamisessa.");
        }
        Score found = new Score(-1,0,"");
        
        try {
            found = scoreDao.findById(2);
        } catch (Exception e) {
            System.out.println("Ei voitu löytää id:tä.");
        }
        
        assertEquals(score2, found);
    }
    @Test
    public void findByIdReturnsNullIfThereIsNotSuchId() {
        Score found = new Score(-1,0,"");
        try {
            found = scoreDao.findById(2);
        } catch (Exception e) {
            System.out.println("Ei voitu löytää id:tä.");
        }
        
        assertEquals(null, found);
    }
    
    @Test
    public void findAllInOrderByPointsWorks() {
        Score score1 = new Score(1, 1000, "Pekka");
        Score score2 = new Score(5, 2012, "Anni");
        Score score3 = new Score(-1, 1921, "Matias");
        
        try {
            scoreDao.save(score1);
            scoreDao.save(score2);
            scoreDao.save(score3);
        } catch (Exception e) {
            System.out.println("Jotain meni vikaan tiedon tallentamisessa.");
        }
        List<Score> list = new ArrayList<>();
        
        try {
            list = scoreDao.findAllInOrderByPoints();
        } catch (Exception e) {
            System.out.println("Ei voitu löytää id:tä.");
        }
        
        boolean correctOrder = (list.get(0).equals(score2)) 
                && (list.get(1).equals(score3)) && (list.get(2).equals(score1));
        assertEquals(true, correctOrder);
    }
    
    @Test
    public void findAllInOrderByNameWorks() {
        Score score1 = new Score(1, 1000, "Pekka");
        Score score2 = new Score(5, 2012, "Matias");
        Score score3 = new Score(-1, 1921, "Anni");
        
        try {
            scoreDao.save(score1);
            scoreDao.save(score2);
            scoreDao.save(score3);
        } catch (Exception e) {
            System.out.println("Jotain meni vikaan tiedon tallentamisessa.");
        }
        List<Score> list = new ArrayList<>();
        
        try {
            list = scoreDao.findAllInOrderByName();
        } catch (Exception e) {
            System.out.println("Ei voitu löytää id:tä.");
        }
        
        boolean correctOrder = (list.get(0).equals(score3)) 
                && (list.get(1).equals(score2)) && (list.get(2).equals(score1));
        assertEquals(true, correctOrder);
    }
}
