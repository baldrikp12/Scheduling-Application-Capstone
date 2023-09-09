package kbaldr2.model.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kbaldr2.model.Contact;
import kbaldr2.model.DataCache;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A Data Access Object (DAO) class for managing Contact data.
 * Extends the generic DAO class with DataCache type.
 * <p>
 * NOTE: Only used to retrieve contact data.
 */
public class ContactDAO extends DAO<DataCache> {
    
    private static final ObservableList<DataCache> contactData = FXCollections.observableArrayList();
    
    /**
     * Constructs an ContactDAO with the specified connection.
     *
     * @param connection The database connection to use
     */
    public ContactDAO(Connection connection) {
        
        super(connection);
    }
    
    /**
     * Retrieves all contacts from the database.
     *
     * @return An ObservableList containing all contacts
     * @throws SQLException If there is an issue executing the query
     */
    @Override public ObservableList<DataCache> getAll() throws SQLException {
        
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("Select Contact_ID, Contact_Name, Email FROM contacts");
        int id;
        String name;
        String email;
        
        while (rs.next()) {
            id = rs.getInt("Contact_ID");
            name = rs.getString("Contact_Name");
            email = rs.getString("Email");
            contactData.add(new Contact(id, name, email));
        }
        
        return contactData;
    }
    
    /**
     * Not Used
     */
    @Override public void create(DataCache item) {
    
    }
    
    /**
     * Not Used
     */
    @Override public void update(DataCache item) {
    
    }
    
    /**
     * Not Used
     */
    @Override public void delete(int id) throws SQLException {
    
    }
    
}
