package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    
    private final String USER = "root";
    private final String SENHA = "";
    private final String DATABASE = "dbbiblioteca";
    
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
        
        return DriverManager.getConnection("jdbc:mysql://localhost/" + DATABASE,USER, SENHA);
    }
}
