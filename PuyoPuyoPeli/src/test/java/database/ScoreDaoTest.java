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
}
