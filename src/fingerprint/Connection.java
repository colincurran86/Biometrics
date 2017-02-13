/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fingerprint;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class Connection {

    public static void main(String[] args) throws ClassNotFoundException {

        Connection conn = null;
        

        try {
            // Connect method
            Class.forName("org.postgresql.Driver");
            String dbURL = "jdbc:postgresql://localhost:5432/Student";
            String user = "postgres";
            String pass = "v5v36jwd";

            conn = (Connection) DriverManager.getConnection(dbURL, user, pass);
            if (conn != null) {
                System.out.println("Connected to database!!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Failed to connect to DB");
        }

    }
}
