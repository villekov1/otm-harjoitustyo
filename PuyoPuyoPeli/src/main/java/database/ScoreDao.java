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
    
    public ScoreDao(Database database) {
        this.database = database;
    }
    
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
    
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Tulos WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
    
    public int findId(Score tulos) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tulos WHERE nimi = ? AND tulos = ?");
        stmt.setString(1, tulos.getName());
        stmt.setInt(2, tulos.getScore());

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
    
    public void saveIfNotInTheDatabase(Score tulos) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tulos WHERE nimi = ? AND tulos = ?");
        stmt.setString(1, tulos.getName());
        stmt.setInt(2, tulos.getScore());

        ResultSet rs = stmt.executeQuery();
        
        if (!rs.next()) {
            save(tulos);
        }
        
        stmt.close();
        rs.close();
        conn.close();
    }
    
    public Score save(Score tulos) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Tulos(tulos, nimi) VALUES (?, ?)");
        stmt.setInt(1, tulos.getScore());
        stmt.setString(2, tulos.getName());
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("SELECT * FROM Tulos WHERE nimi = ? AND tulos = ?");
        stmt.setString(1, tulos.getName());
        stmt.setInt(2, tulos.getScore());        
        ResultSet rs = stmt.executeQuery();
        rs.next(); // vain 1 tulos
        Score t = new Score(rs.getInt("id"), rs.getInt("tulos"), rs.getString("nimi"));

        stmt.close();
        rs.close();
        conn.close();

        return t;
    }

}
