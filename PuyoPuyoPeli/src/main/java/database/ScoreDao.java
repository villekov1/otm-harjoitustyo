package database;

import domain.Score;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A Dao class that is used to modify database containing the scores.
 */
public class ScoreDao {
    private Database database;
    
    /**
    * A constructor of the class ScoreDao
    * @param   database   The database that the ScoreDao uses
    */
    public ScoreDao(Database database) {
        this.database = database;
    }
    
    /**
    * The method finds all of the Scores that are stored in the database.
    * @return   List that contains all of the scores.
    */
    public List<Score> findAll() throws SQLException {
        List<Score> tulokset = new ArrayList<>();
        
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT id, nimi, tulos FROM Tulos");
        
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            tulokset.add(new Score(rs.getInt("id"), rs.getInt("tulos"), rs.getString("nimi")));
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return tulokset;
    }
    
    /**
    * The method finds all of the Scores stored in the database ordered by the points.
    * @return   List that contains all of the scores ordered by points.
    */
    public List<Score> findAllInOrderByPoints() throws SQLException {
        List<Score> tulokset = new ArrayList<>();
        
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT id, nimi, tulos FROM Tulos ORDER BY tulos DESC");
        
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            tulokset.add(new Score(rs.getInt("id"), rs.getInt("tulos"), rs.getString("nimi")));
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return tulokset;
    }
    
    /**
    * The method finds all of the Scores stored in the database ordered by name.
    * @return   List that contains all of the scores ordered by name.
    */
    public List<Score> findAllInOrderByName() throws SQLException {
        List<Score> tulokset = new ArrayList<>();
        
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT id, nimi, tulos FROM Tulos ORDER BY nimi ASC");
        
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            tulokset.add(new Score(rs.getInt("id"), rs.getInt("tulos"), rs.getString("nimi")));
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return tulokset;
    }
    
    /**
    * The method finds a Score that has the id given by the parameter.
    * It may return null if such an id is not found.
    * @param   id   The id of the Score
    * @return   Score that has the id given by the parameter.
    */
    public Score findById(int id) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT id, nimi, tulos FROM Tulos WHERE id = ?");
        stmt.setInt(1, id);
        
        ResultSet rs = stmt.executeQuery();
        Score tulos;
        
        if (rs.next()) {
            tulos = new Score(rs.getInt("id"), rs.getInt("tulos"), rs.getString("nimi"));
            rs.close();
            stmt.close();
            conn.close();
            
            return tulos;
        } else {
            rs.close();
            stmt.close();
            conn.close();
            
            return null;
        }
    }
    
    /**
    * The method deletes a Score in the database that has the id given by the parameter.
    * @param   key   The id of the Score
    */
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Tulos WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
    
    /**
    * The method finds the id of a Score given by the parameter.
    * It returns -1 if such a Score is not found on the database.
    * @param   score   The score whose id is looked for.
    * @return   The integer-valued id of the Score.
    */
    public int findId(Score score) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tulos WHERE nimi = ? AND tulos = ?");
        stmt.setString(1, score.getName());
        stmt.setInt(2, score.getScore());

        ResultSet rs = stmt.executeQuery();
        int id;
        
        if (rs.next()) {
            id = rs.getInt("id");
            stmt.close();
            rs.close();
            conn.close();
            return id;
        }
        
        stmt.close();
        rs.close();
        conn.close();
        return -1;
        
        
        
    }
    
    /**
    * The method saves the Score to the database if there isn't a score
    * with the same name and points as the Score given by the parameter.
    * @param   score   The Score that is saved to the database
    */
    public void saveIfNotInTheDatabase(Score score) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tulos WHERE nimi = ? AND tulos = ?");
        stmt.setString(1, score.getName());
        stmt.setInt(2, score.getScore());

        ResultSet rs = stmt.executeQuery();
        
        if (!rs.next()) {
            save(score);
        }
        
        stmt.close();
        rs.close();
        conn.close();
    }
    
    /**
    * The method saves the Score given by the parameter to the database.
    * @param   Score   The Score that is saved to the database
    * @return   The Score that is saved to the database
    */
    public Score save(Score score) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Tulos(tulos, nimi) VALUES (?, ?)");
        stmt.setInt(1, score.getScore());
        stmt.setString(2, score.getName());
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("SELECT * FROM Tulos WHERE nimi = ? AND tulos = ?");
        stmt.setString(1, score.getName());
        stmt.setInt(2, score.getScore());        
        ResultSet rs = stmt.executeQuery();
        rs.next(); // vain 1 tulos
        Score t = new Score(rs.getInt("id"), rs.getInt("tulos"), rs.getString("nimi"));

        stmt.close();
        rs.close();
        conn.close();

        return t;
    }

}
