package kbaldr2.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import kbaldr2.model.dao.ReportDAO;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Controller for the Schedule Report scene.
 * Initializes the scene and generates reports.
 */
public class CountReportController implements Initializable {
    
    @FXML
    private TextArea contactCountArea;
    
    /**
     * Initializes the controller.
     *
     * @param url            the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized.
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        
        DBConnection.openConnection();
        ReportDAO dao = new ReportDAO(DBConnection.getConnection());
        
        String report = generateReport(dao.getAppointmentsByCustomerAndContact());
        contactCountArea.setText(report);
        
    }
    
    /**
     * Generates a report based on the appointments by customer and contact.
     *
     * @param appointmentsByCustomer a map of appointments grouped by customer and contact.
     * @return a string representation of the generated report.
     */
    public String generateReport(Map<String, Set<String>> appointmentsByCustomer) {
        
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("Customer Report\n");
        reportBuilder.append("==============\n\n");
        
        for (Map.Entry<String, Set<String>> entry : appointmentsByCustomer.entrySet()) {
            String customer = entry.getKey();
            Set<String> contacts = entry.getValue();
            
            reportBuilder.append(String.format("%s\n", customer));
            
            for (String contact : contacts) {
                reportBuilder.append(String.format("   - %s\n", contact));
            }
            
            reportBuilder.append("\n");
        }
        
        return reportBuilder.toString();
    }
    
}
