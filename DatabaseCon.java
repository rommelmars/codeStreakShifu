/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BlackJack;

/**
 *
 * @author Administrator
 */





import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseCon {
    private static final String DBS = "blackjackjava";
    private static final String URL = "jdbc:mysql://localhost/" + DBS;
    private static final String USER = "root";
    private static final String PASSWORD = ""; // XAMPP MySQL by default has no password for root

    public static Connection connect() {
        Connection conn = null;
        
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection to the database
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // pstmt.setString(2, "john@example.com");
            System.out.println("Successfully connected to the database.");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        Connection connection = connect();

        // Ensure the connection is closed after use
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            System.err.println("Error closing connection: " + ex.getMessage());
        }
    }
}

