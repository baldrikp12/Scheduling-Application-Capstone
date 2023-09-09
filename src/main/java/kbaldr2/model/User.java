package kbaldr2.model;

/**
 * A class that represents a user and inherits from the DataCache class.
 */
public class User extends DataCache {
    
    private final int userID;
    private String userName;
    
    /**
     * Constructs a User object with the given ID and name.
     *
     * @param theId   the ID of the user.
     * @param theName the name of the user.
     */
    public User(int theId, String theName) {
        
        this.userID = theId;
        this.userName = theName;
        
    }
    
    /**
     * Returns the ID of the user.
     *
     * @return the ID of the user.
     */
    @Override public int getId() {
        
        return userID;
    }
    
    /**
     * Returns the name of the user.
     *
     * @return the name of the user.
     */
    public String getUserName() {
        
        return userName;
    }
    
}
