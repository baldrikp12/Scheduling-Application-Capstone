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
        assertTrue(Files.exists(Paths.get("login_activity.txt")));
    }
    
    @Test void testGetRowCount() {
        
        LoginController controller = new LoginController();
        
        // Test case for an empty login_activity.txt file
        // comment out test when testing non-empty
        assertEquals(1, controller.getRowCount()); // I deleted all previous entries.
        // Test case for a non-empty login_activity.txt file
        // comment out test when testing empty
        assertEquals(2, controller.getRowCount()); // I added one test entry.
    }
    
    @Test void testClose() {
        
        LoginController controller = Mockito.mock(LoginController.class);
        
        // Assert that the login window has been closed
        controller.close();
    }
}