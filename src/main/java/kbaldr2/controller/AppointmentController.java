package kbaldr2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kbaldr2.helper.Alerts;
import kbaldr2.helper.Formatter;
import kbaldr2.helper.SceneManager;
import kbaldr2.model.Appointment;
import kbaldr2.model.Contact;
import kbaldr2.model.Customer;
import kbaldr2.model.DataCache;
import kbaldr2.model.dao.AppointmentDAO;
import kbaldr2.model.dao.DAO;

import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {
    
    private Appointment appointToMod;
    private int localOpenHour;
    private int localOpenMinute;
    private int localCloseHour;
    private int localCloseMinute;
    private boolean isAdding = true;
    @FXML
    private AnchorPane parentPane;
    @FXML
    private ComboBox<String> customerCombo;
    @FXML
    private ComboBox<String> contactCombo;
    @FXML
    private Label appIDField;
    @FXML
    private Label userLabel;
    @FXML
    private Label addModLabel;
    @FXML
    private Button addModifyButton;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextArea descArea;
    @FXML
    private ListView<String> endTimeListView;
    @FXML
    private TextField locationField;
    @FXML
    private ListView<String> startTimeListView;
    @FXML
    private TextField titleField;
    @FXML
    private TextField typeField;
    @FXML
    private Label createdByLabel;
    
    /**
     * Initializes the AppointmentController.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        
        initializeBusinessHours();
        
        // Check if the current time is after business hours
        LocalTime currentTime = LocalTime.now();
        LocalTime closeTime = LocalTime.of(localCloseHour, localCloseMinute);
        LocalDate updatedDate = LocalDate.now();
        if (currentTime.isAfter(closeTime)) {
            updatedDate = LocalDate.now().plusDays(1);
        }
        final LocalDate setDateTime = updatedDate;
        
        datePicker.setValue(setDateTime);
        datePicker.setDayCellFactory(datePicker -> new DateCell() {
            @Override public void updateItem(LocalDate date, boolean empty) {
                
                super.updateItem(date, empty);
                if (date.isBefore(setDateTime)) {
                    setDisable(true);
                }
            }
        });
        updateStartTime();
        populateCustomerCombo();
        populateContactCombo();
        
        createdByLabel.setText(DAO.getUsername());
        userLabel.setText(DAO.getUsername());
        
    }
    
    /**
     * Initializes business hours by converting the business hours in Eastern Time to the local time zone.
     */
    private void initializeBusinessHours() {
        
        LocalTime easternTimeOpen = LocalTime.of(8, 00);
        LocalTime easternTimeClose = LocalTime.of(22, 00);
        ZoneId easternZoneId = ZoneId.of("America/New_York");
        
        ZonedDateTime localDateTime = ZonedDateTime.now();
        
        ZonedDateTime easternDateTimeO = ZonedDateTime.of(localDateTime.toLocalDate(), easternTimeOpen, easternZoneId);
        ZonedDateTime localOpenTime = easternDateTimeO.withZoneSameInstant(localDateTime.getZone());
        localOpenHour = localOpenTime.getHour(); // Open HOUR in local time
        localOpenMinute = localOpenTime.getMinute(); // Open MINUTES in local time
        
        //Closing time
        ZonedDateTime easternDateTimeC = ZonedDateTime.of(localDateTime.toLocalDate(), easternTimeClose, easternZoneId);
        ZonedDateTime localCloseTime = easternDateTimeC.withZoneSameInstant(localDateTime.getZone());
        localCloseHour = localCloseTime.getHour();// Closing HOUR in local time
        localCloseMinute = localCloseTime.getMinute();// Closing MINUTES in local time
    }
    
    /**
     * Updates the list of start times based on the selected date and business hours.
     */
    @FXML private void updateStartTime() {
        
        LocalTime bod = Formatter.ESTtoLocal(LocalTime.of(8, 0));
        LocalTime eod = Formatter.ESTtoLocal(LocalTime.of(22, 0));
        startTimeListView.getSelectionModel().clearSelection();
        endTimeListView.getItems().clear();
        // Get the selected date from the DatePicker
        LocalDate selectedDate = datePicker.getValue();
        
        // Check if the selected date is not today
        if (LocalTime.now().isBefore(bod) || LocalTime.now().isAfter(eod) || LocalDate.now().isBefore(selectedDate)) {
            ObservableList<String> items = FXCollections.observableArrayList();
            LocalTime start = Formatter.ESTtoLocal(LocalTime.of(8, 0));
            LocalTime end = LocalTime.of(localCloseHour, localCloseMinute);
            for (LocalTime time = start; time.isBefore(end); time = time.plusMinutes(15)) {
                items.add(time.toString());
            }
            startTimeListView.setItems(items);
        } else {
            int minute = LocalTime.now().getMinute();
            int roundedMinute;
            if (minute > 45) {
                roundedMinute = 00;
            } else {
                roundedMinute = (minute / 15) * 15 + 15;
            }
            LocalTime roundedTime = LocalTime.now().withMinute(roundedMinute).withSecond(0).withNano(0);
            ObservableList<String> items = FXCollections.observableArrayList();
            LocalTime start = LocalTime.of(roundedTime.getHour(), roundedTime.getMinute());
            LocalTime end = LocalTime.of(localCloseHour, localCloseMinute);
            for (LocalTime time = start; time.isBefore(end); time = time.plusMinutes(15)) {
                items.add(time.toString());
            }
            startTimeListView.setItems(items);
            startTimeListView.scrollTo(0);
        }
    }
    
    /**
     * Populates the customer ComboBox with a list of customer names.
     */
    private void populateCustomerCombo() {
        
        for (DataCache item : DataCache.getAllCustomers()) {
            String customerOption = ((Customer) item).getName();
            customerCombo.getItems().add(customerOption);
        }
    }
    
    /**
     * Populates the contact ComboBox with a list of contact names.
     */
    private void populateContactCombo() {
        
        for (DataCache item : DataCache.getAllContacts()) {
            String contactOption = ((Contact) item).getContactName();
            contactCombo.getItems().add(contactOption);
        }
    }
    
    /**
     * Sets an appointment to modify and populates the form with the appointment's data.
     *
     * @param theItem The appointment item to modify.
     */
    public void setAppointmentToModify(DataCache theItem) {
        
        addModLabel.setText("Modify Appointment");
        addModifyButton.setText("Update");
        appointToMod = (Appointment) theItem;
        isAdding = false;
        populateForm();
    }
    
    /**
     * Populates the form with the appointment data.
     */
    private void populateForm() {
        
        appIDField.setText(Integer.toString(appointToMod.getId()));
        customerCombo.setValue(DataCache.getCustomerName(appointToMod.getCustomerID()));
        contactCombo.setValue(DataCache.getContactName(appointToMod.getContactID()));
        titleField.setText(appointToMod.getTitle());
        typeField.setText(appointToMod.getType());
        locationField.setText(appointToMod.getLocation());
        descArea.setText(appointToMod.getDescription());
        datePicker.setValue(appointToMod.getStartDateAndTime().toLocalDate());
        startTimeListView.getSelectionModel().select(appointToMod.getStartDateAndTime().toLocalTime().toString());
        startTimeListView.scrollTo(startTimeListView.getSelectionModel().getSelectedIndex());
        updateEndTime();
        endTimeListView.getSelectionModel().select(appointToMod.getEndDateAndTime().toLocalTime().toString());
        endTimeListView.scrollTo(endTimeListView.getSelectionModel().getSelectedIndex());
        createdByLabel.setText(appointToMod.getCreatedBy());
    }
    
    /**
     * Updates the list of end times based on the selected start time and business hours.
     */
    @FXML private void updateEndTime() {
        
        endTimeListView.getSelectionModel().clearSelection();
        //gets starting time from starting time list
        String theTime = startTimeListView.getSelectionModel().getSelectedItem();
        //converts string to localtime object
        LocalTime theStartingTime = Formatter.parseTime(theTime);
        //gets the hour and minute from localtime object
        int theHour = theStartingTime.getHour();
        int theMinute = theStartingTime.getMinute();
        
        ObservableList<String> items = FXCollections.observableArrayList();
        
        LocalTime endTime = LocalTime.of(theHour, theMinute);
        
        while (endTime.isBefore(LocalTime.of(localCloseHour, localCloseMinute))) {
            endTime = endTime.plusMinutes(15);
            items.add(Formatter.formatTime(endTime));
        }
        endTimeListView.setItems(items);
        endTimeListView.scrollTo(0);
    }
    
    /**
     * Handles the Add/Modify button click by creating or modifying an appointment.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML private void addModifyApp(ActionEvent event) {
        
        if (isFilledOut()) {
            String title = titleField.getText();
            String description = descArea.getText();
            String location = locationField.getText();
            String type = typeField.getText();
            LocalDateTime startDateTime = getStartDateTime();
            LocalDateTime endDateTime = getEndDateTime();
            String createdBy = createdByLabel.getText();
            int customerID = DataCache.getCustomerID(customerCombo.getValue());
            int contactID = DataCache.getContactID(contactCombo.getValue());
            int userID = DataCache.getUserID(userLabel.getText());
            
            if (!hasAppOverlap()) {
                DBConnection.openConnection();
                DAO<DataCache> dao = new AppointmentDAO(DBConnection.getConnection());
                
                if (isAdding) {
                    Appointment newApp = new Appointment(0, title, description, location, type, startDateTime, endDateTime, createdBy, customerID, userID, contactID);
                    
                    dao.create(newApp);
                    DataCache.addAppointment(newApp);
                } else {
                    String updatedBy = DAO.getUsername();
                    appointToMod.setTitle(title);
                    appointToMod.setDescription(description);
                    appointToMod.setLocation(location);
                    appointToMod.setType(type);
                    appointToMod.setStartDateAndTime(startDateTime);
                    appointToMod.setEndDateAndTime(endDateTime);
                    appointToMod.setCustomerID(customerID);
                    appointToMod.setUserID(userID);
                    appointToMod.setContactID(contactID);
                    
                    appointToMod.setUpdatedBy(updatedBy);
                    
                    dao.update(appointToMod);
                    
                }
                
                DBConnection.closeConnection();
                close();
            }
        } else {
            Alerts.showAlert("Please fill form out completely", "Empty Fields");
        }
    }
    
    /**
     * Checks if every node in the scene is filled out. Returns true if filled
     * and false if any field or combobox is null or blank.
     *
     * @return true if all fields are filled out, false otherwise
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
            } else if (node instanceof TextArea ta) {
                if (ta.getText().trim().isEmpty()) {
                    ta.setStyle("-fx-border-color: RED;");
                    isAllFilled = false;
                } else {
                    ta.setStyle("");
                }
            } else if (node instanceof ListView lv) {
                if (lv.getSelectionModel().isEmpty()) {
                    lv.setStyle("-fx-border-color: RED;");
                    isAllFilled = false;
                } else {
                    lv.setStyle("");
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
        System.out.println(endTimeListView.getSelectionModel().getSelectedItem());
        return isAllFilled;
    }
    
    /**
     * Returns a localdatetime object for the appointment start time.
     *
     * @return localStart The local start time
     */
    private LocalDateTime getStartDateTime() {
        
        LocalDate selectedDate = datePicker.getValue();
        String startTime = startTimeListView.getSelectionModel().getSelectedItem();
        LocalTime start = LocalTime.parse(startTime);
        LocalDateTime localStart = LocalDateTime.of(selectedDate, start);
        return localStart;
    }
    
    /**
     * Returns a localdatetime object for the appointment end time.
     *
     * @return localEnd The local end time
     */
    private LocalDateTime getEndDateTime() {
        
        LocalDate selectedDate = datePicker.getValue();
        String endTime = endTimeListView.getSelectionModel().getSelectedItem();
        LocalTime end = LocalTime.parse(endTime);
        LocalDateTime localEnd = LocalDateTime.of(selectedDate, end);
        return localEnd;
    }
    
    /**
     * Checks if the appointment time frame overlaps with any other appointment for the customer.
     *
     * @return boolean indicating if there is an overlap or not
     */
    private boolean hasAppOverlap() {
        
        String customerName = customerCombo.getValue();
        for (DataCache item : DataCache.getAllAppointments()) {
            Appointment app = (Appointment) item;
            if (app.getCustomerID() == DataCache.getCustomerID(customerName) && (appointToMod != null) & app.getId() != appointToMod.getId()) {
                if ((getStartDateTime().isAfter(app.getStartDateAndTime()) && getStartDateTime().isBefore(app.getEndDateAndTime())) || (getEndDateTime().isAfter(app.getStartDateAndTime()) && getEndDateTime().isBefore(app.getEndDateAndTime()))) {
                    Alerts.showWarning("An appointment already exists in this time period", "Appointment Overlap");
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Closes the current window.
     */
    @FXML private void close() {
        
        Stage appStage = SceneManager.getStage("appointment");
        SceneManager.getStage("appointment").fireEvent(new WindowEvent(appStage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    
}