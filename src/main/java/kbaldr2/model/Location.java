package kbaldr2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a location, extending the DataCache class.
 */
public class Location extends DataCache {
    
    private static final ObservableList<String> us = FXCollections.observableArrayList();
    private static final ObservableList<String> uk = FXCollections.observableArrayList();
    private static final ObservableList<String> ca = FXCollections.observableArrayList();
    private final int divisionID;
    private final String divisionName;
    private final int countryID;
    private final String countryName;
    
    /**
     * Constructs a Location object with the specified division ID, division name, country ID, and country name, and adds the division name to the corresponding country list.
     *
     * @param theDivisionID   the ID of the division
     * @param theDivisionName the name of the division
     * @param theCountryID    the ID of the country
     * @param theCountryName  the name of the country
     */
    public Location(int theDivisionID, String theDivisionName, int theCountryID, String theCountryName) {
        
        this.divisionID = theDivisionID;
        this.divisionName = theDivisionName;
        this.countryID = theCountryID;
        this.countryName = theCountryName;
        addToList();
    }
    
    /**
     * Adds the division name to the corresponding country list.
     */
    private void addToList() {
        
        switch (countryID) {
            case 1:
                us.add(divisionName);
                break;
            case 2:
                uk.add(divisionName);
                break;
            case 3:
                ca.add(divisionName);
                break;
        }
        
    }
    
    /**
     * Returns an ObservableList of division names for Canada.
     *
     * @return an ObservableList of division names for Canada
     */
    public static ObservableList<String> getCa() {
        
        return ca;
    }
    
    /**
     * Returns an ObservableList of division names for the United States.
     *
     * @return an ObservableList of division names for the United States
     */
    public static ObservableList<String> getUs() {
        
        return us;
    }
    
    /**
     * Returns an ObservableList of division names for the United Kingdom.
     *
     * @return an ObservableList of division names for the United Kingdom
     */
    public static ObservableList<String> getUk() {
        
        return uk;
    }
    
    /**
     * @return
     */
    @Override public int getId() {
        
        return divisionID;
    }
    
    /**
     * Returns the division ID.
     *
     * @return the division ID
     */
    public String getDivisionName() {
        
        return divisionName;
    }
    
    /**
     * Returns the country ID.
     *
     * @return the country ID
     */
    public int getCountryID() {
        
        return countryID;
    }
    
}
