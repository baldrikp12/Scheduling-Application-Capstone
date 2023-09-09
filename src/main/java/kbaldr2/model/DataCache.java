package kbaldr2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents an abstract DataCache class that stores the data for appointments, customers, users, contacts, and locations.
 */
public abstract class DataCache {
    
    private static final ObservableList<DataCache> allAppointments = FXCollections.observableArrayList();
    private static final ObservableList<DataCache> allCustomers = FXCollections.observableArrayList();
    private static final ObservableList<DataCache> allUsers = FXCollections.observableArrayList();
    private static final ObservableList<DataCache> allContacts = FXCollections.observableArrayList();
    private static final ObservableList<DataCache> allLocations = FXCollections.observableArrayList();
    
    private static int userId;
    
    /**
     * Returns a list of all appointments.
     *
     * @return an ObservableList of Appointment objects
     */
    public static ObservableList<DataCache> getAllAppointments() {
        
        return allAppointments;
    }
    
    /**
     * Sets the list of all appointments.
     *
     * @param allAppointments an ObservableList of Appointment objects
     */
    public static void setAllAppointments(ObservableList<DataCache> allAppointments) {
        
        DataCache.allAppointments.setAll(allAppointments);
    }
    
    /**
     * Adds an appointment to the list of all appointments.
     *
     * @param app an Appointment object
     */
    public static void addAppointment(Appointment app) {
        
        allAppointments.add(app);
    }
    
    /**
     * Adds a customer to the list of all customers.
     *
     * @param app a Customer object
     */
    public static void addCustomer(Customer app) {
        
        allCustomers.add(app);
    }
    
    /**
     * Sets the user ID.
     *
     * @param userId an integer representing the user ID
     */
    public static void setUserId(int userId) {
        
        DataCache.userId = userId;
    }
    
    /**
     * Clears all data objects.
     */
    public static void clearObjects() {
        
        allAppointments.clear();
        allCustomers.clear();
        allUsers.clear();
        allContacts.clear();
        allLocations.clear();
    }
    
    /**
     * Returns the user ID associated with the given user name.
     *
     * @param userName the name of the user
     * @return the ID of the user, or -1 if the user is not found
     */
    public static int getUserID(String userName) {
        
        int id = -1;
        for (DataCache item : getAllUsers()) {
            
            if (((User) item).getUserName().equals(userName)) {
                return ((User) item).getId();
            }
        }
        return id;
    }
    
    /**
     * Returns a list of all users.
     * <p>
     * * @return an ObservableList of User objects
     */
    public static ObservableList<DataCache> getAllUsers() {
        
        return allUsers;
    }
    
    /**
     * Sets the list of all users.
     *
     * @param allUsers an ObservableList of User objects
     */
    public static void setAllUsers(ObservableList<DataCache> allUsers) {
        
        DataCache.allUsers.setAll(allUsers);
    }
    
    /**
     * Returns the customer ID associated with the given customer name.
     *
     * @param customerName the name of the customer
     * @return the ID of the customer, or -1 if the customer is not found
     */
    public static int getCustomerID(String customerName) {
        
        int id = -1;
        for (DataCache item : getAllCustomers()) {
            if (((Customer) item).getName().equals(customerName)) {
                return ((Customer) item).getId();
            }
        }
        return id;
    }
    
    /**
     * Returns a list of all customers.
     *
     * @return an ObservableList of Customer objects
     */
    public static ObservableList<DataCache> getAllCustomers() {
        
        return allCustomers;
    }
    
    /**
     * Sets the list of all customers.
     *
     * @param allCustomers an ObservableList of Customer objects
     */
    public static void setAllCustomers(ObservableList<DataCache> allCustomers) {
        
        DataCache.allCustomers.setAll(allCustomers);
    }
    
    /**
     * Returns the contact ID associated with the given contact name.
     *
     * @param contactName the name of the contact
     * @return the ID of the contact, or -1 if the contact is not found
     */
    public static int getContactID(String contactName) {
        
        int id = -1;
        for (DataCache item : getAllContacts()) {
            if (((Contact) item).getContactName().equals(contactName)) {
                return ((Contact) item).getId();
            }
        }
        return id;
    }
    
    /**
     * Returns a list of all contacts.
     *
     * @return an ObservableList of Contact objects
     */
    public static ObservableList<DataCache> getAllContacts() {
        
        return allContacts;
    }
    
    /**
     * Sets the list of all contacts.
     *
     * @param allContacts an ObservableList of Contact objects
     */
    public static void setAllContacts(ObservableList<DataCache> allContacts) {
        
        DataCache.allContacts.setAll(allContacts);
    }
    
    /**
     * Returns the division name associated with the given division ID.
     *
     * @param divisionID the ID of the division
     * @return the name of the division, or an empty string if the division is not found
     */
    public static String getDivisionName(int divisionID) {
        
        String name = "";
        for (DataCache item : getAllLocations()) {
            if (((Location) item).getId() == divisionID) {
                return ((Location) item).getDivisionName();
            }
        }
        return name;
    }
    
    /**
     * Returns a list of all locations.
     *
     * @return an ObservableList of Location objects
     */
    public static ObservableList<DataCache> getAllLocations() {
        
        return allLocations;
    }
    
    /**
     * Sets the list of all locations.
     *
     * @param allFirstLevelDivision an ObservableList of Location objects
     */
    public static void setAllLocations(ObservableList<DataCache> allFirstLevelDivision) {
        
        DataCache.allLocations.setAll(allFirstLevelDivision);
    }
    
    /**
     * Returns the country ID associated with the given division ID.
     *
     * @param divisionID the ID of the division
     * @return the ID of the country, or -1 if the division is not found
     */
    public static int getCountryIDFromDivisionID(int divisionID) {
        
        int id = -1;
        for (DataCache item : getAllLocations()) {
            Location theLocation = (Location) item;
            if (theLocation.getId() == divisionID) {
                return theLocation.getCountryID();
            }
        }
        return id;
    }
    
    /**
     * Returns the first division ID associated with the given division name.
     *
     * @param divisionName the name of the division
     * @return the ID of the first matching division, or -1 if the division is not found
     */
    public static int getFirstDivisionID(String divisionName) {
        
        int id = -1;
        for (DataCache item : getAllLocations()) {
            Location theDivision = (Location) item;
            if (theDivision.getDivisionName().equals(divisionName)) {
                return theDivision.getId();
            }
        }
        return id;
    }
    
    /**
     * Returns the contact name associated with the given contact ID.
     *
     * @param contactID the ID of the contact
     * @return the name of the contact, or an empty string if the contact is not found
     */
    public static String getContactName(int contactID) {
        
        String name = "";
        for (DataCache item : getAllContacts()) {
            Contact theContact = (Contact) item;
            if (theContact.getId() == contactID) {
                return theContact.getContactName();
            }
        }
        return name;
    }
    
    /**
     * Returns the customer name associated with the given customer ID.
     *
     * @param customerID the ID of the customer
     * @return the name of the customer, or an empty string if the customer is not found
     */
    public static String getCustomerName(int customerID) {
        
        String name = "";
        for (DataCache item : getAllCustomers()) {
            Customer theCustomer = (Customer) item;
            if (theCustomer.getId() == customerID) {
                return theCustomer.getName();
            }
        }
        return name;
    }
    
    /**
     * Returns the ID of the object.
     *
     * @return an integer representing the object ID
     */
    public abstract int getId();
    
}
