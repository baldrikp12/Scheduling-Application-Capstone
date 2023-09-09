package kbaldr2.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an appointment, extending the DataCache class.
 * Contains appointment-specific data such as title, description, location, type, start and end date times, customer, user, and contact.
 */
public class Appointment extends DataCache {
    
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startDateAndTime;
    private LocalDateTime endDateAndTime;
    private String createdBy;
    private String updatedBy;
    private int customerID;
    private int userID;
    private int contactID;
    private String startDateAndTimeFormatted;
    
    /**
     * Constructs an Appointment object.
     *
     * @param appointmentID    The unique identifier of the appointment.
     * @param title            The title of the appointment.
     * @param description      The description of the appointment.
     * @param location         The location of the appointment.
     * @param type             The type of appointment.
     * @param startDateAndTime The start date and time of the appointment.
     * @param endDateAndTime   The end date and time of the appointment.
     * @param customerID       The unique identifier of the customer.
     * @param userID           The unique identifier of the user.
     * @param contactID        The unique identifier of the contact.
     */
    public Appointment(int appointmentID, String title, String description, String location, String type, LocalDateTime startDateAndTime, LocalDateTime endDateAndTime, String createdBy, int customerID, int userID, int contactID) {
        
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateAndTime = startDateAndTime;
        this.endDateAndTime = endDateAndTime;
        this.createdBy = createdBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }
    
    /**
     * Returns the unique identifier of the appointment.
     *
     * @return The appointment ID.
     */
    @Override public int getId() {
        
        return appointmentID;
    }
    
    /**
     * Sets the unique identifier of the appointment.
     *
     * @param appointmentID The appointment ID.
     */
    public void setAppointmentID(int appointmentID) {
        
        this.appointmentID = appointmentID;
    }
    
    /**
     * Returns the title of the appointment.
     *
     * @return The title of the appointment.
     */
    public String getTitle() {
        
        return title;
    }
    
    /**
     * Sets the title of the appointment.
     *
     * @param title The title of the appointment.
     */
    public void setTitle(String title) {
        
        this.title = title;
    }
    
    /**
     * Returns the description of the appointment.
     *
     * @return The description of the appointment.
     */
    public String getDescription() {
        
        return description;
    }
    
    /**
     * Sets the description of the appointment.
     *
     * @param description The description of the appointment.
     */
    public void setDescription(String description) {
        
        this.description = description;
    }
    
    /**
     * Returns the location of the appointment.
     *
     * @return The location of the appointment.
     */
    public String getLocation() {
        
        return location;
    }
    
    /**
     * Sets the location of the appointment.
     *
     * @param location The location of the appointment.
     */
    public void setLocation(String location) {
        
        this.location = location;
    }
    
    /**
     * Returns the type of appointment.
     *
     * @return The type of appointment.
     */
    public String getType() {
        
        return type;
    }
    
    /**
     * Sets the type of appointment.
     *
     * @param type The type of appointment.
     */
    public void setType(String type) {
        
        this.type = type;
    }
    
    /**
     * Returns the start date and time of the appointment.
     *
     * @return The start date and time of the appointment.
     */
    public LocalDateTime getStartDateAndTime() {
        
        return startDateAndTime;
    }
    
    /**
     * Sets the start date and time of the appointment.
     *
     * @param startDateAndTime The start date and time of the appointment.
     */
    public void setStartDateAndTime(LocalDateTime startDateAndTime) {
        
        this.startDateAndTime = startDateAndTime;
    }
    
    /**
     * Returns the formatted start date and time of the appointment.
     *
     * @return The formatted start date and time of the appointment.
     */
    public String getStartDateAndTimeFormatted() {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        return startDateAndTime.format(formatter);
    }
    
    /**
     * Returns the end date and time of the appointment.
     *
     * @return The end date and time
     */
    public LocalDateTime getEndDateAndTime() {
        
        return endDateAndTime;
    }
    
    /**
     * Sets the end date and time of the appointment.
     *
     * @param endDateAndTime The end date and time of the appointment.
     */
    public void setEndDateAndTime(LocalDateTime endDateAndTime) {
        
        this.endDateAndTime = endDateAndTime;
    }
    
    /**
     * Returns the formatted end date and time of the appointment.
     *
     * @return The formatted end date and time of the appointment.
     */
    public String getEndDateAndTimeFormatted() {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        return endDateAndTime.format(formatter);
    }
    
    /**
     * Returns the user who created the appointment.
     *
     * @return The user who created the appointment.
     */
    public String getCreatedBy() {
        
        return createdBy;
    }
    
    /**
     * Sets the user who created the appointment.
     *
     * @param createdBy The user who created the appointment.
     */
    public void setCreatedBy(String createdBy) {
        
        this.createdBy = createdBy;
    }
    
    /**
     * Returns the unique identifier of the customer.
     *
     * @return The customer ID.
     */
    public int getCustomerID() {
        
        return customerID;
    }
    
    /**
     * Sets the unique identifier of the customer.
     *
     * @param customerID The customer ID.
     */
    public void setCustomerID(int customerID) {
        
        this.customerID = customerID;
    }
    
    /**
     * Returns the unique identifier of the user.
     *
     * @return The user ID.
     */
    public int getUserID() {
        
        return userID;
    }
    
    /**
     * Sets the unique identifier of the user.
     *
     * @param userID The user ID.
     */
    public void setUserID(int userID) {
        
        this.userID = userID;
    }
    
    /**
     * Returns the unique identifier of the contact.
     *
     * @return The contact ID.
     */
    public int getContactID() {
        
        return contactID;
    }
    
    /**
     * Sets the unique identifier of the contact.
     *
     * @param contactID The contact ID.
     */
    public void setContactID(int contactID) {
        
        this.contactID = contactID;
    }
    
    /**
     * Returns the user who last updated the appointment.
     *
     * @return The user who last updated the appointment.
     */
    public String getUpdatedBy() {
        
        return updatedBy;
    }
    
    /**
     * Sets the user who last updated the appointment.
     *
     * @param updatedBy The user who last updated the appointment.
     */
    public void setUpdatedBy(String updatedBy) {
    
    }
    
}
