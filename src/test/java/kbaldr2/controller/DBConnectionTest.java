package kbaldr2.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class DBConnectionTest {
    
    private Connection testConnection;
    
    @BeforeEach void setUp() {
        // Open a database connection using DBConnection
        DBConnection.openConnection();
    }
    
    @Test void testOpenConnection() {
        // Check if the connection is not null after opening
        assertNotNull(DBConnection.getConnection());
    }
    
    @Test void testCloseConnection() {
        // Close the connection
        DBConnection.closeConnection();
        
        // Check if the connection is null after closing
        assertNull(DBConnection.getConnection());
    }
    
    @Test void testGetConnection() {
        // Check if the connection obtained from DBConnection is not null
        assertNotNull(DBConnection.getConnection());
    }
    
}
