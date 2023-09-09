package kbaldr2.main;

import javafx.application.Application;
import javafx.stage.Stage;
import kbaldr2.helper.SceneManager;

import java.io.IOException;

/**
 * The main entry point for the application.
 */
public class application extends Application {
    
    /**
     * The main method that launches the application.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        
        launch();
    }
    
    /**
     * Starts the application by building and displaying the login scene.
     *
     * @param stage The primary stage for the application (not used)
     * @throws IOException If there is an issue loading the login scene
     */
    @Override public void start(Stage stage) throws IOException {
        
        SceneManager.buildLoginScene();
        
    }
    
}