package kbaldr2.model.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kbaldr2.model.DataCache;
import kbaldr2.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A Data Access Object (DAO) class for managing USer data .
 * Extends the generic DAO class with DataCache type.
 * <p>
 * NOTE: Only used to retrieve user data.
 */
public class UserDAO extends DAO<DataCache> {
    
    private static final ObservableList<DataCache> userData = FXCollections.observableArrayList();
    
    /**
     * Constructs an UserDAO with the specified connection.
     *
     * @param connection The database connection to use
     */
    public UserDAO(Connection connection) {
        
        super(connection);
    }
    
    /**
     * Retrieves all users from the database.
     *
     * @return An ObservableList containing all users
     * @throws SQLException If there is an issue executing the query
     */
    @Override public ObservableList<DataCache> getAll() throws SQLException {
        
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("Select User_ID, User_Name FROM users");
        int id;
        String name;
        
        while (rs.next()) {
            id = rs.getInt("User_ID");
            name = rs.getString("User_Name");
            userData.add(new User(id, name));
        }
        
        return userData;
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
