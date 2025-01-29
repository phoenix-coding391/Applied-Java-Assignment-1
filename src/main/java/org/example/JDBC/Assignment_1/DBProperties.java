package org.example.JDBC.Assignment_1;

import java.io.PrintStream;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBProperties {

    public static final String DATABASE_URL = "jdbc:mariadb://localhost:3306";

    public static final String DATABASE_USER = "root";

    //TODO Update this with your Password!
    public static final String DATABASE_PASSWORD = "nbudmaria";

    public static final String DATABASE_URL_COMPLETE = "jdbc:mariadb://localhost:3306?user="+ DATABASE_USER +"&password=" + DATABASE_PASSWORD;

    /**
     * Register the driver using two options - Class.forName and Driver class
     * //TODO clean this method up / split it into two methods?
     * @param printStream where you will print failure information (System.err recommended)
     * @return true if registered successfully
     */
    public static boolean isDriverRegistered(PrintStream printStream){
        //Option 1: Find the class
        try {
            Class.forName("org.mariadb.jdbc.Driver").newInstance();
            printStream.println("Option 1: Find the class worked!");
        } catch (ClassNotFoundException ex) {
            printStream.println("Error: unable to load driver class!");
            return false;
        } catch (IllegalAccessException ex) {
            printStream.println("Error: access problem while loading!");
            return false;
        } catch (InstantiationException ex) {
            printStream.println("Error: unable to instantiate driver!");
            return false;
        }

        //Option 2: Register the Driver
        try {
            Driver myDriver = new org.mariadb.jdbc.Driver();
            DriverManager.registerDriver(myDriver);
            printStream.println("Option 2: Register the Driver worked!");
        } catch (SQLException ex) {
            printStream.println("Error: unable to load driver class!");
            return false;
        }
        return true;
    }


}
