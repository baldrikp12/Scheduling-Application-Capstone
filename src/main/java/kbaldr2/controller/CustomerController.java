package kbaldr2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kbaldr2.helper.Alerts;
import kbaldr2.helper.SceneManager;
import kbaldr2.model.Customer;
import kbaldr2.model.DataCache;
import kbaldr2.model.Location;
import kbaldr2.model.dao.CustomerDAO;
import kbaldr2.model.dao.DAO;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    
    private Customer customerToMod;
    private boolean isAdding = true;
    @FXML
    private AnchorPane parentPane;
    @FXML
    private Label addModLabel;
    @FXML
    private Label appIDField;
    @FXML
    private Button addModifyButton;
    @FXML
    private ComboBox<String> divisionCombo;
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField postalField;
    @FXML
    private ToggleGroup regionGroup;
    @FXML
    private RadioButton usRadio;
    @FXML
    private RadioButton ukRadio;
    @FXML
    private RadioButton caRadio;
    @FXML
    private Label userLabel;
    @FXML
    private Label divisionLabel;
    
    
    /**
     * Initializes the controller class.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        
        divisionCombo.setPromptText("States");
        divisionCombo.setItems(Location.getUs());
        userLabel.setText(DAO.getUsername());
    }
    
    /**
     * Sets a customer to be modified.
     *
     * @param theItem The customer to modify.
     */
    public void setCustomerToModify(DataCache theItem) {
        
        addModLabel.setText("Update Appointment");
        addModifyButton.setText("Update");
        customerToMod = (Customer) theItem;
        isAdding = false;
        populateForm();
    }
    
    /**
     * Populates the form with the customer's data.
     */
    private void populateForm() {
        
        appIDField.setText(Integer.toString(customerToMod.getId()));
        nameField.setText(customerToMod.getName());
        addressField.setText(customerToMod.getAddress());
        postalField.setText(customerToMod.getPostalCode());
        phoneField.setText(customerToMod.getPhone());
        switch (DataCache.getCountryIDFromDivisionID(customerToMod.getDivisionID())) {
            case 1:
                usRadio.setSelected(true);
                break;
            case 2:
                ukRadio.setSelected(true);
                break;
            case 3:
                caRadio.setSelected(true);
                break;
        }
        radioSelectAction();
        divisionCombo.setValue(DataCache.getDivisionName(customerToMod.getDivisionID()));
        
    }
    
    /**
     * Sets the prompt text of the division combobox based on the selected radio button.
     */
    @FXML private void radioSelectAction() {
        // get the selected radio button
        RadioButton selectedRadio = (RadioButton) regionGroup.getSelectedToggle();
        // set the prompt text of the combobox based on the selected radio button
        if (selectedRadio == usRadio) {
            divisionCombo.setItems(Location.getUs());
            divisionLabel.setText("States");
        } else if (selectedRadio == caRadio) {
            
            divisionCombo.setItems(Location.getCa());
            divisionLabel.setText("Provinces");
        } else if (selectedRadio == ukRadio) {
            divisionCombo.setItems(Location.getUk());
            divisionLabel.setText("Countries");
        }
        
    }
    
    /**
     * Adds or modifies a customer.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML private void addModifyCust(ActionEvent event) {
        
        if (isFilledOut()) {
            if (!containsDivision()) {
                String name = nameField.getText();
                String address = addressField.getText();
                String postal = postalField.getText();
                String phone = phoneField.getText();
                int firstDivision = DataCache.getFirstDivisionID(divisionCombo.getValue());
                
                DBConnection.openConnection();
                DAO<DataCache> dao = new CustomerDAO(DBConnection.getConnection());
                
                if (isAdding) {
                    Customer newCust = new Customer(0, name, address, postal, phone, firstDivision);
                    String createdBy = DAO.getUsername();
                    newCust.setCreatedBy(createdBy);
                    
                    dao.create(newCust);
                    DataCache.addCustomer(newCust);
                    
                } else {
                    String updatedBy = DAO.getUsername();
                    customerToMod.setName(name);
                    customerToMod.setAddress(address);
                    customerToMod.setPostalCode(postal);
                    customerToMod.setPhone(phone);
                    customerToMod.setDivisionID(firstDivision);
                    customerToMod.setUpdatedBy(updatedBy);
                    
                    dao.update(customerToMod);
                    
                }
                
                DBConnection.closeConnection();
                close();
            }
        } else {
            Alerts.showAlert("Please fill form out completely", "Empty Fields");
        }
    }
    
    /**
     * Checks if the form is completely filled out.
     *
     * @return True if the form is completely filled out, false otherwise.
     */
    private boolean isFilledOut() {
        
        boolean isAllFilled = true;
        for (Node node : parentPane.getChildren()) {
            if (node instanceof TextField tf) {
                if (tf.getText().trim().isEmpty()) {
                    tf.setStyle("-fx-border-color: RED;");
                    isAllFilled = false;
                } else {
                    tf.setStyle("");
                }
            } else if (node instanceof ComboBox cb) {
                if (cb.getSelectionModel().isEmpty()) {
                    cb.setStyle("-fx-border-color: RED;");
                    isAllFilled = false;
                } else {
                    cb.setStyle("");
                }
            }
        }
        return isAllFilled;
    }
    
    /**
     * Checks if the address contains a division.
     *
     * @return True if the address contains a division, false otherwise.
     */
    private boolean containsDivision() {
        
        for (DataCache item : DataCache.getAllLocations()) {
            Location loc = (Location) item;
            if (addressField.getText().contains(loc.getDivisionName().toLowerCase())) {
                Alerts.showAlert("Address should only contain home and street number", "Invalid Address");
                return true;
            }
        }
        return false;
    }
    
    /**
     * Closes the current window.
     */
    @FXML private void close() {
        
        Stage custStage = SceneManager.getStage("customer");
        SceneManager.getStage("customer").fireEvent(new WindowEvent(custStage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    
}
