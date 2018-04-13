package database;

import domain.Tulos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TulosDao {
    private Database database;
    
    public TulosDao(Database database){
        this.database = database;
    }
    
    public List<Tulos> findAll() throws SQLException{
        List<Tulos> tulokset = new ArrayList<>();
        
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT id, nimi, tulos FROM Tulos");
        
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            tulokset.add(new Tulos(rs.getInt("id"), rs.getInt("tulos"), rs.getString("nimi")));
        }
        
        rs.close();
        stmt.close();
        
        return tulokset;
    }
    
    public Tulos findById(int id) throws SQLException{
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT id, nimi, tulos FROM Tulos WHERE id = ?");
        stmt.setInt(1, id);
        
        ResultSet rs = stmt.executeQuery();
        Tulos tulos;
        
        if(rs.next()){
            tulos = new Tulos(rs.getInt("id"), rs.getInt("tulos"), rs.getString("nimi"));
            rs.close();
            stmt.close();

            return tulos;
        }else{
            rs.close();
            stmt.close();
            return null;
        }
    }
    
    public void delete(int id) throws SQLException{
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Tulos WHERE id = ?");

        stmt.setInt(1, id);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
    
    private Tulos save(Tulos tulos) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Tulos"
                + " (tulos, nimi)"
                + " VALUES (?, ?)");
        stmt.setInt(1, tulos.getTulos());
        stmt.setString(2, tulos.getNimi());

        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("SELECT * FROM Tulos"
                + " WHERE nimi = ? AND tulos = ?");
        stmt.setString(1, tulos.getNimi());
        stmt.setInt(2, tulos.getTulos());
        
        ResultSet rs = stmt.executeQuery();
        
        rs.next(); // vain 1 tulos

        Tulos t = new Tulos(rs.getInt("id"), rs.getInt("tulos"), rs.getString("nimi"));

        stmt.close();
        rs.close();
        conn.close();

        return t;
    }

}
