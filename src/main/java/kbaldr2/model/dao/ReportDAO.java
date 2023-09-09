package kbaldr2.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Data Access Object for generating various reports based on the data in the database.
 */
public class ReportDAO {
    
    protected static Connection connection;
    
    /**
     * Constructs a ReportDAO with the specified database connection.
     *
     * @param connection The database connection to use
     */
    public ReportDAO(Connection connection) {
        
        this.connection = connection;
    }
    
    /**
     * Returns a map of appointment counts by type and month.
     *
     * @return A map of appointment counts by type and month
     */
    public Map<Integer, Map<Integer, Map<String, Integer>>> getAppointmentsByTypeAndMonth() {
        
        String query = "SELECT type, YEAR(start) AS year, MONTH(start) AS month, COUNT(*) as total FROM appointments GROUP BY year, month, type ORDER BY year, month, type;";
        
        Map<Integer, Map<Integer, Map<String, Integer>>> result = new HashMap<>();
        
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                int year = rs.getInt("year");
                int month = rs.getInt("month");
                String type = rs.getString("type");
                int count = rs.getInt("total");
                
                if (!result.containsKey(year)) {
                    result.put(year, new HashMap<>());
                }
                
                if (!result.get(year).containsKey(month)) {
                    result.get(year).put(month, new HashMap<>());
                }
                
                result.get(year).get(month).put(type, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    /**
     * Returns a map of appointments by customer and contact.
     *
     * @return A map of appointments by customer and contact
     */
    public Map<String, Set<String>> getAppointmentsByCustomerAndContact() {
        
        String query = "SELECT c.Customer_Name AS Customer, co.Contact_Name AS Contact " + "FROM appointments a " + "INNER JOIN customers c ON a.Customer_ID = c.Customer_ID " + "INNER JOIN contacts co ON a.Contact_ID = co.Contact_ID " + "GROUP BY a.Customer_ID, a.Contact_ID " + "ORDER BY c.Customer_Name, co.Contact_Name;";
        
        Map<String, Set<String>> appointmentsByCustomer = new LinkedHashMap<>();
        
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                String customer = rs.getString("Customer");
                String contact = rs.getString("Contact");
                
                if (!appointmentsByCustomer.containsKey(customer)) {
                    appointmentsByCustomer.put(customer, new LinkedHashSet<>());
                }
                
                appointmentsByCustomer.get(customer).add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return appointmentsByCustomer;
    }
    
}
