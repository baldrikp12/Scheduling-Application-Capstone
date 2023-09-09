package kbaldr2.model;

/**
 * Represents a Customer, extending the DataCache class.
 * Contains customer-specific data such as name, address, postal code, phone number, and division ID.
 */
public class Customer extends DataCache {
    
    private int customerID;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionID;
    private String createdBy;
    private String updatedBy;
    
    /**
     * Constructs a new Customer object with the specified parameters.
     *
     * @param customerID the unique identifier for the customer
     * @param name       the name of the customer
     * @param address    the address of the customer
     * @param postalCode the postal code of the customer
     * @param phone      the phone number of the customer
     * @param divisionID the division ID associated with the customer
     */
    public Customer(int customerID, String name, String address, String postalCode, String phone, int divisionID) {
        
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
        
    }
    
    /**
     * Returns the unique identifier for the customer.
     *
     * @return The customer ID.
     */
    @Override public int getId() {
        
        return customerID;
    }
    
    /**
     * Sets the unique identifier for the customer.
     *
     * @param customerID The customer ID.
     */
    public void setCustomerID(int customerID) {
        
        this.customerID = customerID;
    }
    
    /**
     * Returns the name of the customer.
     *
     * @return The name of the customer.
     */
    public String getName() {
        
        return name;
    }
    
    /**
     * Sets the name of the customer.
     *
     * @param name The name of the customer.
     */
    public void setName(String name) {
        
        this.name = name;
    }
    
    /**
     * Returns the address of the customer.
     *
     * @return The address of the customer.
     */
    public String getAddress() {
        
        return address;
    }
    
    /**
     * Sets the address of the customer.
     *
     * @param address The address of the customer.
     */
    public void setAddress(String address) {
        
        this.address = address;
    }
    
    /**
     * Returns the postal code of the customer.
     *
     * @return The postal code of the customer.
     */
    public String getPostalCode() {
        
        return postalCode;
    }
    
    /**
     * Sets the postal code of the customer.
     *
     * @param postalCode The postal code of the customer.
     */
    public void setPostalCode(String postalCode) {
        
        this.postalCode = postalCode;
    }
    
    /**
     * Returns the phone number of the customer.
     *
     * @return The phone number of the customer.
     */
    public String getPhone() {
        
        return phone;
    }
    
    /**
     * Sets the phone number of the customer.
     *
     * @param phone The phone number of the customer.
     */
    public void setPhone(String phone) {
        
        this.phone = phone;
    }
    
    /**
     * Returns the division ID associated with the customer.
     *
     * @return The division ID.
     */
    public int getDivisionID() {
        
        return divisionID;
    }
    
    /**
     * Sets the division ID associated with the customer.
     *
     * @param divisionID The division ID.
     */
    public void setDivisionID(int divisionID) {
        
        this.divisionID = divisionID;
    }
    
    /**
     * Returns the user who created the customer.
     *
     * @return The user who created the customer.
     */
    public String getCreatedBy() {
        
        return createdBy;
    }
    
    /**
     * Sets the user who created the customer.
     *
     * @param createdBy The user who created the customer.
     */
    public void setCreatedBy(String createdBy) {
        
        this.createdBy = createdBy;
    }
    
    /**
     * Returns the user who last updated the customer.
     *
     * @return The user who last updated the customer.
     */
    public String getUpdatedBy() {
        
        return updatedBy;
    }
    
    /**
     * Sets the user who last updated the customer.
     *
     * @param updatedBy The user who last updated the customer.
     */
    public void setUpdatedBy(String updatedBy) {
        
        this.updatedBy = updatedBy;
    }
    
}
