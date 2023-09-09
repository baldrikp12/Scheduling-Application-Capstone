package kbaldr2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import kbaldr2.model.Appointment;
import kbaldr2.model.Contact;
import kbaldr2.model.DataCache;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller for the Contact Count Report scene.
 * Initializes the scene and generates reports.
 */
public class ScheduleReportController implements Initializable {
    
    private static ObservableList<DataCache> contactList = FXCollections.observableArrayList();
    private static final TableColumn<DataCache, String> app_ContactCol = new TableColumn<>("Contact");
    private static final TableColumn<DataCache, Integer> app_AppointmentIdCol = new TableColumn<>("Appointment ID");
    private static final TableColumn<DataCache, String> app_TitleCol = new TableColumn<>("Title");
    private static final TableColumn<DataCache, String> app_TypeCol = new TableColumn<>("Type");
    private static final TableColumn<DataCache, String> app_DescriptionCol = new TableColumn<>("Description");
    private static final TableColumn<DataCache, LocalDateTime> app_StartDateAndTimeCol = new TableColumn<>("Start Date and Time");
    private static final TableColumn<DataCache, LocalDateTime> app_EndDateAndTimeCol = new TableColumn<>("End Date and Time");
    private static final TableColumn<DataCache, Integer> app_CustomerIdCol = new TableColumn<>("Customer ID");
    private static TableView<DataCache> dataCacheTable;
    @FXML
    private ComboBox<String> contactCombo;
    
    @FXML
    private TableView<DataCache> appReportTable;
    
    /**
     * Initializes the appointments table and contact combo box.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        
        //builds appointments table
        app_AppointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        appReportTable.getColumns().add(app_AppointmentIdCol);
        
        app_TitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        appReportTable.getColumns().add(app_TitleCol);
        
        app_TypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        appReportTable.getColumns().add(app_TypeCol);
        
        app_DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        appReportTable.getColumns().add(app_DescriptionCol);
        
        app_StartDateAndTimeCol.setCellValueFactory(new PropertyValueFactory<>("StartDateAndTimeFormatted"));
        appReportTable.getColumns().add(app_StartDateAndTimeCol);
        
        app_EndDateAndTimeCol.setCellValueFactory(new PropertyValueFactory<>("EndDateAndTimeFormatted"));
        appReportTable.getColumns().add(app_EndDateAndTimeCol);
        
        app_CustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        appReportTable.getColumns().add(app_CustomerIdCol);
        
        app_ContactCol.setCellValueFactory(new PropertyValueFactory<>("ContactID"));
        appReportTable.getColumns().add(app_ContactCol);
        
        for (DataCache item : DataCache.getAllContacts()) {
            Contact contact = (Contact) item;
            String contactOption = contact.getContactName();
            contactCombo.getItems().add(contactOption);
        }
    }
    
    /**
     * Updates the appointments table with the appointments related to the selected contact.
     *
     * @param event The action event that triggered the update.
     */
    @FXML private void updateTable(ActionEvent event) {
        
        contactList = DataCache.getAllAppointments().stream().filter(a -> a instanceof Appointment && ((Appointment) a).getContactID() == DataCache.getContactID(contactCombo.getValue())).collect(Collectors.toCollection(FXCollections::observableArrayList));
        
        appReportTable.setItems(contactList);
    }
    
}
