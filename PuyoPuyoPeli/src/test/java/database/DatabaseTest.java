package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
public class DatabaseTest {
    
    Database database;
    
    public DatabaseTest() {
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
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void databaseAddressIsCorrect() {
        assertEquals("jdbc:sqlite:testit.db", database.getDatabaseAddress());
    }
    
    @Test
    public void getConnectionDoesntReturnNull() {
        try {
            assertFalse(database.getConnection().equals(null));
        } catch(Exception e) {
            System.out.println("Ei saatu yhteyttä tietokantaan.");
        }
    }
}
