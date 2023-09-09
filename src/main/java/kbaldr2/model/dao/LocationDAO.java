package kbaldr2.model.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kbaldr2.model.DataCache;
import kbaldr2.model.Location;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A Data Access Object (DAO) class for managing Location data (Country and first level divisions).
 * Extends the generic DAO class with DataCache type.
 * <p>
 * NOTE: Only used to retrieve location data.
 */
public class LocationDAO extends DAO<DataCache> {
    
    private static final ObservableList<DataCache> divisionData = FXCollections.observableArrayList();
    
    /**
     * Constructs an LocationDAO with the specified connection.
     *
     * @param connection The database connection to use
     */
    public LocationDAO(Connection connection) {
        
        super(connection);
    }
    
    /**
     * Retrieves all locations from the database.
     *
     * @return An ObservableList containing all locations
     * @throws SQLException If there is an issue executing the query
     */
    @Override public ObservableList<DataCache> getAll() throws SQLException {
        
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("Select first_level_divisions.Division_ID, first_level_divisions.Division, Countries.Country_ID, Countries.Country from first_level_divisions inner join Countries on first_level_divisions.Country_ID = Countries.Country_ID; ");
        int divisionID;
        int countryID;
        String divisionName;
        String countryName;
        while (rs.next()) {
            divisionID = rs.getInt("first_level_divisions.Division_ID");
            divisionName = rs.getString("first_level_divisions.Division");
            countryID = rs.getInt("Countries.Country_ID");
            countryName = rs.getString("Countries.Country");
            divisionData.add(new Location(divisionID, divisionName, countryID, countryName));
        }
        return divisionData;
        
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
