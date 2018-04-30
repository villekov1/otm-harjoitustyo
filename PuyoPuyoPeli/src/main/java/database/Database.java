package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A class that represents a database.
 * It contains the address of a database in addition to methods for creating a new Connection.
 */
public class Database {

    private String databaseAddress;
    
    /**
    * A constructor of the class Database.
    * @param   databaseAddress   The address of the database.
    */
    public Database(String databaseAddress) {
        this.databaseAddress = databaseAddress;
    }
    
    /**
    * The method returns the address of a Database.
    * @return   String databaseAddress.
    */
    public String getDatabaseAddress() {
        return this.databaseAddress;
    }
    
    /**
    * The method returns a Connection using DriverManager class' getConnection() -method.
    * @return   Connection to the database given by the address.
    */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }
    
}

