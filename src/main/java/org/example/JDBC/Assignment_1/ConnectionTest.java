package org.example.JDBC.Assignment_1;

import org.example.JDBC.Assignment_1.DBProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTest {

    public static void main(String[] args) {
        try{
            //Test the connection with a single complete URL
            Connection conn = DriverManager.getConnection(DBProperties.DATABASE_URL_COMPLETE);
            System.out.println("Connection Successful!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
