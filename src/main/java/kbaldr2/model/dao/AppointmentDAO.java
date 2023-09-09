package kbaldr2.model.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kbaldr2.helper.Formatter;
import kbaldr2.model.Appointment;
import kbaldr2.model.DataCache;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * A Data Access Object (DAO) class for managing Appointment data.
 * Extends the generic DAO class with DataCache type.
 */
public class AppointmentDAO extends DAO<DataCache> {
    
    private static final String SELECT_ALL_STMT = "SELECT * FROM appointments";
    private static final String DELETE_RECORD_STMT = "DELETE FROM appointments WHERE Appointment_ID = ";
    private static final ObservableList<DataCache> appointmentData = FXCollections.observableArrayList();
    
    /**
     * Constructs an AppointmentDAO with the specified connection.
     *
     * @param connection The database connection to use
     */
    public AppointmentDAO(Connection connection) {
        
        super(connection);
    }
    
    /**
     * Retrieves all appointments from the database.
     *
     * @return An ObservableList containing all appointments
     * @throws SQLException If there is an issue executing the query
     */
    @Override public ObservableList<DataCache> getAll() throws SQLException {
        
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(SELECT_ALL_STMT);
        int appointmentID;
        String title;
        String description;
        String location;
        String type;
        LocalDateTime startDateAndTime;
        LocalDateTime endDateAndTime;
        String createdBy;
        int customerID;
        int userID;
        int contactID;
        
        while (rs.next()) {
            
            appointmentID = rs.getInt("Appointment_ID");
            title = rs.getString("Title");
            description = rs.getString("Description");
            location = rs.getString("Location");
            type = rs.getString("Type");
            startDateAndTime = rs.getObject("Start", LocalDateTime.class);
            endDateAndTime = rs.getObject("End", LocalDateTime.class);
            createdBy = rs.getString("Created_By");
            customerID = rs.getInt("Customer_ID");
            userID = rs.getInt("User_ID");
            contactID = rs.getInt("Contact_ID");
            //converts times to system time for display purposes.
            Appointment newApp = new Appointment(appointmentID, title, description, location, type, Formatter.UTCtoLocal(startDateAndTime), Formatter.UTCtoLocal(endDateAndTime),createdBy, customerID, userID, contactID);
            appointmentData.add(newApp);
        }
        return appointmentData;
    }
    
    /**
     * Creates a new appointment in the database.
     *
     * @param item The appointment to create
     */
    @Override public void create(DataCache item) {
        
        Appointment appointment = (Appointment) item;
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Customer_ID, User_ID, Contact_ID) " + "VALUES (?, ?, ?, ?, ?, ?, NOW(),?, NOW(), ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, appointment.getTitle());
            statement.setString(2, appointment.getDescription());
            statement.setString(3, appointment.getLocation());
            statement.setString(4, appointment.getType());
            statement.setTimestamp(5, Timestamp.valueOf(Formatter.localToUTC(appointment.getStartDateAndTime())));
            statement.setTimestamp(6, Timestamp.valueOf(Formatter.localToUTC(appointment.getEndDateAndTime())));
            statement.setString(7, appointment.getCreatedBy());
            statement.setInt(8, appointment.getCustomerID());
            statement.setInt(9, appointment.getUserID());
            statement.setInt(10, appointment.getContactID());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int primaryKey = generatedKeys.getInt(1);
                appointment.setAppointmentID(primaryKey);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Updates an existing appointment in the database.
     *
     * @param item The appointment with updated data
     */
    @Override public void update(DataCache item) {
        
        Appointment appointment = (Appointment) item;
        try (PreparedStatement statement = connection.prepareStatement("UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = NOW(), Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?")) {
            statement.setString(1, appointment.getTitle());
            statement.setString(2, appointment.getDescription());
            statement.setString(3, appointment.getLocation());
            statement.setString(4, appointment.getType());
            statement.setTimestamp(5, Timestamp.valueOf(Formatter.localToUTC(appointment.getStartDateAndTime())));
            statement.setTimestamp(6, Timestamp.valueOf(Formatter.localToUTC(appointment.getEndDateAndTime())));
            statement.setString(7, appointment.getUpdatedBy());
            statement.setInt(8, appointment.getCustomerID());
            statement.setInt(9, appointment.getUserID());
            statement.setInt(10, appointment.getContactID());
            statement.setInt(11, appointment.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Deletes an appointment from the database using its ID.
     *
     * @param id The ID of the appointment to delete
     * @throws SQLException If there is an issue executing the deletion
     */
    @Override public void delete(int id) throws SQLException {
        
        Statement statement = connection.createStatement();
        String deleteRecord = DELETE_RECORD_STMT + id + ";";
        statement.executeUpdate(deleteRecord);
        
    }
    
}
