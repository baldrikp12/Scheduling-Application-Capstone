package kbaldr2.model.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kbaldr2.model.Customer;
import kbaldr2.model.DataCache;

import java.sql.*;

/**
 * A Data Access Object (DAO) class for managing Customer data.
 * Extends the generic DAO class with DataCache type.
 */
public class CustomerDAO extends DAO<DataCache> {
    
    private static final String SELECT_ALL_STMT = "SELECT * FROM customers";
    private static final String DELETE_RECORD_STMT = "DELETE FROM customers WHERE Customer_ID = ";
    private static final ObservableList<DataCache> customerData = FXCollections.observableArrayList();
    
    /**
     * Constructs a CustomerDAO with the specified connection.
     *
     * @param connection The database connection to use
     */
    public CustomerDAO(Connection connection) {
        
        super(connection);
    }
    
    /**
     * Retrieves all customers from the database.
     *
     * @return An ObservableList containing all customers
     * @throws SQLException If there is an issue executing the query
     */
    @Override public ObservableList<DataCache> getAll() throws SQLException {
        
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(SELECT_ALL_STMT);
        int thecustomerID;
        String thename;
        String theaddress;
        String thepostalCode;
        String thephone;
        int thedivisionID;
        
        while (rs.next()) {
            
            thecustomerID = rs.getInt("Customer_ID");
            thename = rs.getString("Customer_Name");
            theaddress = rs.getString("Address");
            thepostalCode = rs.getString("Postal_Code");
            thephone = rs.getString("Phone");
            thedivisionID = rs.getInt("Division_ID");
            customerData.add(new Customer(thecustomerID, thename, theaddress, thepostalCode, thephone, thedivisionID));
            
        }
        return customerData;
    }
    
    /**
     * Creates a new customer in the database.
     *
     * @param item The customer to create
     */
    @Override public void create(DataCache item) {
        
        Customer customer = (Customer) item;
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO customers (Customer_Name,Address,Postal_Code,Phone,Create_Date,Created_By,Division_ID) " + "VALUES (?, ?, ?, ?, NOW(),?,?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getAddress());
            statement.setString(3, customer.getPostalCode());
            statement.setString(4, customer.getPhone());
            statement.setString(5, customer.getCreatedBy());
            statement.setInt(6, customer.getDivisionID());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int primaryKey = generatedKeys.getInt(1);
                customer.setCustomerID(primaryKey);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    /**
     * Updates an existing customer in the database.
     *
     * @param item The customer with updated data
     */
    @Override public void update(DataCache item) {
        
        Customer customer = (Customer) item;
        try (PreparedStatement statement = connection.prepareStatement("UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = NOW(), Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?")) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getAddress());
            statement.setString(3, customer.getPostalCode());
            statement.setString(4, customer.getPhone());
            statement.setString(5, customer.getUpdatedBy());
            statement.setInt(6, customer.getDivisionID());
            statement.setInt(7, customer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Deletes a customer from the database using its ID.
     *
     * @param id The ID of the customer to delete
     * @throws SQLException If there is an issue executing the deletion
     */
    @Override public void delete(int id) throws SQLException {
        
        Statement statement = connection.createStatement();
        String deleteRecord = DELETE_RECORD_STMT + id + ";";
        statement.executeUpdate(deleteRecord);
        
    }
    
}
