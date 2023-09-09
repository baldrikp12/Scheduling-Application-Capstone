package kbaldr2.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import kbaldr2.model.dao.ReportDAO;

import java.net.URL;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

/**
 * Controller for the Appointment Report scene.
 * Initializes the scene and generates reports.
 */
public class AppointmentReportController implements Initializable {
    
    @FXML
    private TextArea typeMonthReport;
    
    /**
     * Initializes the Appointment Report scene.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        
        DBConnection.openConnection();
        ReportDAO dao = new ReportDAO(DBConnection.getConnection());
        
        String report = generateReport(dao.getAppointmentsByTypeAndMonth());
        typeMonthReport.setText(report);
    }
    
    /**
     * Generates a report based on appointments by type and month.
     *
     * @param appointmentsByTypeAndMonth A map of appointments grouped by type and month.
     * @return The generated report as a string.
     */
    private String generateReport(Map<Integer, Map<Integer, Map<String, Integer>>> appointmentsByTypeAndMonth) {
        
        StringBuilder reportBuilder = new StringBuilder();
        
        reportBuilder.append("Appointments Report\n");
        reportBuilder.append("===================\n\n");
        
        List<Integer> years = new ArrayList<>(appointmentsByTypeAndMonth.keySet());
        Collections.sort(years, Collections.reverseOrder());
        
        for (Integer year : years) {
            Map<Integer, Map<String, Integer>> months = appointmentsByTypeAndMonth.get(year);
            reportBuilder.append(String.format("Year: %d\n", year));
            
            List<Integer> monthNumbers = new ArrayList<>(months.keySet());
            Collections.sort(monthNumbers);
            
            for (Integer month : monthNumbers) {
                String monthName = Month.of(month).getDisplayName(TextStyle.FULL, Locale.getDefault());
                Map<String, Integer> types = months.get(month);
                reportBuilder.append(String.format("   Month: %s\n", monthName));
                
                for (Map.Entry<String, Integer> typeEntry : types.entrySet()) {
                    String type = typeEntry.getKey();
                    int count = typeEntry.getValue();
                    reportBuilder.append(String.format("      - %-10s %d\n", type, count));
                }
            }
            reportBuilder.append("\n");
        }
        
        return reportBuilder.toString();
    }
    
}
