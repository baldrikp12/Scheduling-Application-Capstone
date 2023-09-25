package kbaldr2.controller;


import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This class provides methods to manage a database connection using JDBC.
 */
public class DBConnection {
    
    private static final String protocol = "jdbc";
    
    //Local Database Info
    private static final String vendor_Local = ":mysql:";
    private static final String location_Local = "//localhost:3306/";
    private static final String username_Local = "root";
    private static final String password_Local = "Bypass12";
    
    
    //Remote Database Info
    private static final String vendor_Remote = ":mariadb:";
    //private static final String location_Remote = "//pi.local:3306/";
    private static final String location_Remote = "//192.168.1.144:3306/";
    
    //Database Name
    private static final String databaseName = "client_schedule";
    
    private static final String jdbcUrl_Local = protocol + vendor_Local + location_Local + databaseName;
    static final String jdbcUrl_Remote = protocol + vendor_Remote + location_Remote + databaseName;
    
    static final String username_Remote = "capstone";
    static final String password_Remote = "password";
    
    private static final String driver_Local = "com.mysql.cj.jdbc.Driver"; //driver for database on pc
    private static final String driver_Remote = "org.mariadb.jdbc.Driver"; //driver for database on pc
    public static Connection connection = null;  // Connection Interface
    
    /**
     * Opens a database connection using the specified parameters.
     */
    public static void openConnection() {
        
        try {
            Class.forName(driver_Remote); // Locate Driver
            //connection = DriverManager.getConnection(jdbcUrl_Local, username_Local, password_Local); // Reference Connection object on pc
            connection = DriverManager.getConnection(jdbcUrl_Remote, username_Remote, password_Remote); //  Reference Connection object on Raspberry Pi
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
    
    /**
     * Closes the active database connection.
     */
    public static void closeConnection() {
        
        try {
            connection.close();
            connection = null;
        } catch (Exception e) {
            System.out.println("DB controller: Error:" + e.getMessage());
        }
    }
    
    /**
     * Returns the current database connection.
     *
     * @return the current {@link Connection} object
     */
    public static Connection getConnection() {
        
        return connection;
    }
    
}
