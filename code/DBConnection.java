package code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Untuk MySQL 8.0 ke atas
    private static final String URL = "jdbc:mysql://localhost:3306/gudang_game?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // ganti dengan username MySQL Anda
    private static final String PASSWORD = ""; // ganti dengan password MySQL Anda
    
    public static Connection getConnection() throws SQLException {
        try {
            // Untuk MySQL 8.0+
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Untuk MySQL 5.x (jika menggunakan versi lama)
            // Class.forName("com.mysql.jdbc.Driver");
            
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }
}