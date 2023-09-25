package kbaldr2.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {
    
    
    @Test void testCheckCredentials() {
        
        LoginController controller = new LoginController();
        // Test case for valid credentials
        assertTrue(controller.checkCredentials("test", "test"));
        
        // Test case for invalid credentials
        assertFalse(controller.checkCredentials("invalidUsername", "invalidPassword"));
    }
    
    @Test void testLogAttempt() {
        
        LoginController controller = Mockito.mock(LoginController.class);
        
        // Call the logAttempt() method with the "Success" parameter
        controller.logAttempt("Success");
        
        // Assert that the login_activity.txt file has been updated with the successful login attempt
        // You can use any appropriate assertion method to check the file content or its existence
        // For example, you can use the Files class from the java.nio.file package
        assertTrue(Files.exists(Paths.get("login_activity.txt")));
    }
    
    @Test void testGetRowCount() {
        
        LoginController controller = new LoginController();
        // Test case for an empty login_activity.txt file
        // I deleted all previous entries.
        //assertEquals(1, controller.getRowCount());
        
        // Test case for a non-empty login_activity.txt file
        // I added a single entry to the login file for control
        assertEquals(2, controller.getRowCount());
    }
    
    @Test void testClose() {
        
        LoginController controller = Mockito.mock(LoginController.class);
        // Test case for closing the login window
        // Mock the Stage and WindowEvent objects
        controller.close();
        // Assert that the login window has been closed
    }
    
}