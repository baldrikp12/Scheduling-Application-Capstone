package kbaldr2.model;

/**
 * Represents a contact in the system, with an ID, name, and email address.
 */
public class Contact extends DataCache {
    
    private final int contactID;
    private String contactName;
    private String contactEmail;
    
    /**
     * Constructs a Contact object with the given ID, name, and email address.
     *
     * @param theId    the unique identifier of the contact
     * @param theName  the name of the contact
     * @param theEmail the email address of the contact
     */
    public Contact(int theId, String theName, String theEmail) {
        
        contactID = theId;
        contactName = theName;
        contactEmail = theEmail;
    }
    
    /**
     * Returns the contact ID.
     *
     * @return the unique identifier of the contact
     */
    @Override public int getId() {
        
        return contactID;
    }
    
    /**
     * Returns the contact name.
     *
     * @return the name of the contact
     */
    public String getContactName() {
        
        return contactName;
    }
    
    /**
     * Sets the contact name.
     *
     * @param contactName the new name of the contact
     */
    public void setContactName(String contactName) {
        
        this.contactName = contactName;
    }
    
    /**
     * Returns the contact email address.
     *
     * @return the email address of the contact
     */
    public String getContactEmail() {
        
        return contactEmail;
    }
    
    /**
     * Sets the contact email address.
     *
     * @param contactEmail the new email address of the contact
     */
    public void setContactEmail(String contactEmail) {
        
        this.contactEmail = contactEmail;
    }
    
}
