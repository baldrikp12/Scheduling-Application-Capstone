package kbaldr2.helper;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * A utility class for displaying various types of alert dialogs.
 */
public class Alerts {
    
    /**
     * Displays a confirmation dialog with the specified message and title.
     *
     * @param message the message to display in the dialog
     * @param title   the title of the dialog
     * @return true if the user clicks the OK button, false otherwise
     */
    public static boolean showConfirmationDialog(String message, String title) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
    
    /**
     * Displays an information alert dialog with the specified message and title.
     *
     * @param message the message to display in the dialog
     * @param title   the title of the dialog
     */
    public static void showAlert(String message, String title) {
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Displays a warning alert dialog with the specified message and title.
     *
     * @param message the message to display in the dialog
     * @param title   the title of the dialog
     */
    public static void showWarning(String message, String title) {
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
}
