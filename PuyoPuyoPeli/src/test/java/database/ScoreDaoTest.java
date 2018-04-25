package database;

import domain.Score;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ville
 */
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
                return;
            }
        } catch(Exception e) {
            System.out.println("Virhe: "+e.getMessage());
        }
        
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:testit.db")) {
            if (conn != null) {
                System.out.println("Uusi tietokanta on luotu.");
                
                PreparedStatement stmt = conn.prepareStatement("DROP TABLE IF EXISTS Tulos");
                stmt.execute();
                
                PreparedStatement stmt2 = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Tulos("
                    + "id integer PRIMARY KEY, nimi varchar(200), tulos integer)");
                stmt2.execute();
                stmt.close();
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
    }
    
    @Test
    public void saveWorksProperly() {
        Score tulos = new Score(-1, 2000, "Ville");
        Score tulos2 = new Score(-1, 0, "");
        
        try {
            scoreDao.save(tulos);
        } catch (Exception e) {
            System.out.println("Tallentaminen tietokantaan ei onnistunut");
        }
        /*
        try{
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tulos WHERE nimi = 'Ville'");
        
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                tulos2 = new Score(rs.getInt("id"), rs.getInt("tulos"), rs.getString("nimi"));
                rs.close();
                stmt.close();
                conn.close();
            
            } else {
                rs.close();
                stmt.close();
                conn.close();
            }
            
        } catch (Exception e) {
            System.out.println("Yhteyttä ei voitu muodostaa.");
        }
        
        assertEquals("Ville: 2000", tulos2.toString());
        */
    }
}
