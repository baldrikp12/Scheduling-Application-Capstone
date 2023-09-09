package kbaldr2.model.dao;

import javafx.collections.ObservableList;
import kbaldr2.model.DataCache;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * An abstract Data Access Object (DAO) class for managing data in a database.
 * The DAO class is parameterized by a type T that extends DataCache.
 *
 * @param <T> The type of DataCache this DAO class manages
 */
public abstract class DAO<T extends DataCache> {
    
    protected static Connection connection;
    
    protected static String user;
    
    /**
     * Constructs a DAO with the specified database connection.
     *
     * @param connection The database connection to use
     */
    public DAO(Connection connection) {
        
        DAO.connection = connection;
    }
    
    /**
     * Retrieves the current username.
     *
     * @return The current username
     */
    public static String getUsername() {
        
        return user;
    }
    
    /**
     * Sets the current username.
     *
     * @param username The username to set
     */
    public static void setUsername(String username) {
        
        user = username;
    }
    
    /**
     * Retrieves all records of type T from the database.
     *
     * @return An ObservableList containing all records of type T
     * @throws SQLException If there is an issue executing the query
     */
    public abstract ObservableList<T> getAll() throws SQLException;
    
    /**
     * Creates a new record of type T in the database.
     *
     * @param item The record of type T to create
     */
    public abstract void create(T item);
    
    /**
     * Updates an existing record of type T in the database.
     *
     * @param item The record of type T with updated data
     */
    public abstract void update(T item);
    
    /**
     * Deletes a record of type T from the database using its ID.
     *
     * @param id The ID of the record to delete
     * @throws SQLException If there is an issue executing the deletion
     */
    public abstract void delete(int id) throws SQLException;
    
}
