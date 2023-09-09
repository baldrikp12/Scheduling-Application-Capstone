package kbaldr2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kbaldr2.helper.SceneManager;
import kbaldr2.model.DataCache;
import kbaldr2.model.dao.DAO;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A controller class for handling user login and authentication. It manages the Login screen and its components, including user input validation and login activity logging.
 */
public class LoginController {
    
    private final Locale CURRENT_LOCALE = Locale.getDefault();
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label errorLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label locationLabel;
    
    /**
     * Initializes and configures the login.fxml scene.
     */
    @FXML public void initialize() {
        
        ZoneId zone = ZoneId.systemDefault();
        //Set the text for the location label based on the user's locale
        ResourceBundle messages = ResourceBundle.getBundle("Messages", CURRENT_LOCALE);
        
        usernameField.setPromptText(messages.getString("username.text"));
        passwordField.setPromptText(messages.getString("password.text"));
        loginButton.setText(messages.getString("login.button"));
        exitButton.setText(messages.getString("exit.button"));
        titleLabel.setText(messages.getString("title.label"));
        locationLabel.setText(messages.getString("location.label") + " " + zone);
        
        /**
         * Handles the login button click.
         * Lambda Expression #1
         * @param event The event that triggered the method
         */
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            
            if (username.isEmpty() || password.isEmpty() || !checkCredentials(username, password)) {
                logAttempt("Failed");
                errorLabel.setText(messages.getString("error.credentials"));
            } else {
                logAttempt("Success");
                try {
                    DAO.setUsername(username);
                    SceneManager.buildDashboardScene();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                SceneManager.getStage("login").close();
            }
            
        });
        
        /**
         * Handles the cancel button click.
         * Lambda Expression #2
         * @param event The event that triggered the method
         */
        exitButton.setOnAction(event -> {
            // Exit the program
            System.exit(0);
        });
    }
    
    /**
     * Checks the provided credentials against the database. If the credentials are valid, it sets the User_ID for DataCache.
     *
     * @param username The user's username
     * @param password The user's password
     * @return true if the credentials are valid, false otherwise
     */
    private boolean checkCredentials(String username, String password) {
        
        DBConnection.openConnection();
        Connection conn = DBConnection.getConnection();
        try {
            String sql = "SELECT * FROM users WHERE user_name = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                if (rs.getString("User_Name").equals(username)) {
                    if (rs.getString("Password").equals(password)) {
                        DataCache.setUserId(rs.getInt("User_ID"));
                        System.out.println("we got in");
                        return true;
                        
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
        
    }
    
    /**
     * Logs the login attempt to a file called login_activity.txt.
     *
     * @param theLoginResult A string describing the result of the login attempt, e.g., "Success" or "Failed"
     */
    private void logAttempt(String theLoginResult) {
        
        String username = "null";
        
        try {
            File logFile = new File("login_activity.txt");
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            int rows = getRowCount();
            
            FileWriter writer = new FileWriter(logFile, true);
            String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            
            if (!usernameField.getText().isEmpty()) {
                username = usernameField.getText();
            }
            
            writer.write(rows + "     " + date + "     " + time + "     " + username + "     " + theLoginResult + System.lineSeparator());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            
        }
    }
    
    /**
     * Returns the row count in the login_activity.txt file.
     *
     * @return The number of rows in the login_activity.txt file
     */
    private int getRowCount() {
        
        int rows = 1;
        try (BufferedReader br = new BufferedReader(new FileReader("login_activity.txt"))) {
            while (br.readLine() != null) {
                rows++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows;
    }
    
    /**
     * Closes the login window.
     */
    public void close() {
        
        Stage appStage = SceneManager.getStage("login");
        SceneManager.getStage("login").fireEvent(new WindowEvent(appStage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    
}
