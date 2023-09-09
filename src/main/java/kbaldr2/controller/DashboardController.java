package kbaldr2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.WindowEvent;
import kbaldr2.helper.Alerts;
import kbaldr2.helper.ObjectSearchUtil;
import kbaldr2.helper.SceneManager;
import kbaldr2.helper.TableFormatter;
import kbaldr2.model.Appointment;
import kbaldr2.model.Customer;
import kbaldr2.model.DataCache;
import kbaldr2.model.dao.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * DashboardController is responsible for controlling the application's dashboard
 * and managing appointments and customer data.
 * <p>
 */
public class DashboardController implements Initializable {
    
    private boolean viewingAppointments = true;
    @FXML
    private RadioButton allCustsRadio;
    @FXML
    private Label appAlertLabel;
    @FXML
    private Label appAlertInfoLabel;
    @FXML
    private Label viewingLabel;
    @FXML
    private TableView<DataCache> dataCacheTable;
    @FXML
    private Button closeAppAlertButton;
    @FXML
    private TextField searchField;
    
    /**
     * Initializes the dashboard controller, loads data from the database, and sets up the table view.
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        
        configureTableView();
        loadDataFromDatabase();
        dataCacheTable.setItems(DataCache.getAllAppointments());
        fifteenMinuteCheck();
    }
    
    /**
     * Configures the table view for the dashboard.
     */
    private void configureTableView() {
        
        TableFormatter.setTableView(dataCacheTable);
        TableFormatter.buildAppTables();
    }
    
    /**
     * Loads data from the database, including customers, appointments, contacts, users, and locations.
     */
    private void loadDataFromDatabase() {
        
        try {
            getAllCustomers();
            getAllAppointments();
            getAllContacts();
            getAllUsers();
            getAllLocations();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Checks for appointments within the next 15 minutes and displays an alert.
     */
    private void fifteenMinuteCheck() {
        
        LocalDateTime now = LocalDateTime.now();
        boolean appointmentInFifteen = false;
        Appointment nearestAppointment = null;
        
        for (DataCache item : DataCache.getAllAppointments()) {
            Appointment appointment = (Appointment) item;
            LocalDateTime appointmentDateTime = appointment.getStartDateAndTime();
            
            // Calculate the difference between the appointment time and the current time in minutes
            long difference = ChronoUnit.MINUTES.between(now, appointmentDateTime);
            
            // Check if the difference is less than or equal to 15 and is positive
            if (difference <= 15 && difference >= 0) {
                System.out.println("15 min check");
                appointmentInFifteen = true;
                nearestAppointment = appointment;
                break;
            }
        }
        
        if (appointmentInFifteen) {
            // Display the nearest appointment that is within 15 minutes
            appAlertLabel.setText("There is an appointment within 15 minutes.");
            appAlertLabel.setStyle("-fx-text-fill: RED; -fx-background-color: CCCCCC");
            appAlertInfoLabel.setText("[  Appointment " + nearestAppointment.getId() + " | " + nearestAppointment.getStartDateAndTime().toLocalDate() + " | " + nearestAppointment.getStartDateAndTime().toLocalTime() + "  ]");
            appAlertInfoLabel.setStyle("-fx-text-fill: RED; -fx-background-color: CCCCCC");
            appAlertInfoLabel.setVisible(true);
        } else {
            // Display that there are no appointments within 15 minutes
            appAlertLabel.setText("There are no appointments within 15 minutes.");
            appAlertLabel.setStyle("-fx-text-fill: BLUE; -fx-background-color: CCCCCC");
            appAlertInfoLabel.setVisible(false);
        }
        
        appAlertLabel.setVisible(true);
        closeAppAlertButton.setVisible(true);
    }
    
    /**
     * Retrieves all customer data from the database.
     *
     * @throws SQLException if there is an issue accessing the database
     */
    private void getAllCustomers() throws SQLException {
        
        DBConnection.openConnection();
        DAO<DataCache> dao = new CustomerDAO(DBConnection.getConnection());
        DataCache.setAllCustomers(dao.getAll());
        
        DBConnection.closeConnection();
    }
    
    /**
     * Retrieves all appointment data from the database.
     *
     * @throws SQLException if there is an issue accessing the database
     */
    private void getAllAppointments() throws SQLException {
        
        DBConnection.openConnection();
        DAO<DataCache> dao = new AppointmentDAO(DBConnection.getConnection());
        DataCache.setAllAppointments(dao.getAll());
        
        DBConnection.closeConnection();
        
    }
    
    /**
     * Retrieves all contact data from the database.
     *
     * @throws SQLException if there is an issue accessing the database
     */
    private void getAllContacts() throws SQLException {
        
        DBConnection.openConnection();
        DAO<DataCache> dao = new ContactDAO(DBConnection.getConnection());
        DataCache.setAllContacts(dao.getAll());
        
        
        DBConnection.closeConnection();
    }
    
    /**
     * Retrieves all user data from the database.
     *
     * @throws SQLException if there is an issue accessing the database
     */
    private void getAllUsers() throws SQLException {
        
        DBConnection.openConnection();
        DAO<DataCache> dao = new UserDAO(DBConnection.getConnection());
        DataCache.setAllUsers(dao.getAll());
        
        DBConnection.closeConnection();
    }
    
    /**
     * Retrieves all contact data from the database.
     *
     * @throws SQLException if there is an issue accessing the database
     */
    private void getAllLocations() throws SQLException {
        
        DBConnection.openConnection();
        DAO<DataCache> dao = new LocationDAO(DBConnection.getConnection());
        DataCache.setAllLocations(dao.getAll());
        
        
        DBConnection.closeConnection();
    }
    
    /**
     * Displays appointments scheduled for the current month.
     */
    @FXML private void viewAppsThisMonth() {
        
        viewingLabel.setText("Appointments");
        searchField.setPromptText("Search Monthly Appointments");
        if (!viewingAppointments) {
            dataCacheTable.getColumns().clear();
            TableFormatter.buildAppTables();
            viewingAppointments = true;
        }
        ObservableList<DataCache> filteredData = FXCollections.observableArrayList();
        
        LocalDateTime now = LocalDateTime.now();
        int currentMonth = now.getMonthValue();
        
        for (DataCache item : DataCache.getAllAppointments()) {
            Appointment appointment = (Appointment) item;
            if (appointment.getStartDateAndTime().getMonthValue() == currentMonth) {
                filteredData.add(appointment);
            }
        }
        dataCacheTable.setItems(filteredData);
    }
    
    /**
     * Displays appointments scheduled for the current week.
     */
    @FXML private void viewAppsThisWeek() {
        
        viewingLabel.setText("Appointments");
        searchField.setPromptText("Search Weekly Appointments");
        if (!viewingAppointments) {
            dataCacheTable.getColumns().clear();
            TableFormatter.buildAppTables();
            viewingAppointments = true;
        }
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.truncatedTo(ChronoUnit.DAYS).with(DayOfWeek.MONDAY);
        LocalDateTime endOfWeek = startOfWeek.plusDays(6);
        ObservableList<DataCache> currentWeekAppointments = FXCollections.observableArrayList();
        
        for (DataCache item : DataCache.getAllAppointments()) {
            Appointment appointment = (Appointment) item;
            LocalDateTime startDateTime = appointment.getStartDateAndTime();
            if (!startDateTime.isBefore(startOfWeek) && !startDateTime.isAfter(endOfWeek)) {
                currentWeekAppointments.add(appointment);
            }
        }
        dataCacheTable.setItems(currentWeekAppointments);
    }
    
    /**
     * Displays all appointments.
     */
    @FXML private void viewAllApps() {
        
        viewingLabel.setText("Appointments");
        searchField.setPromptText("Search All Appointments");
        if (!viewingAppointments) {
            dataCacheTable.getColumns().clear();
            TableFormatter.buildAppTables();
            viewingAppointments = true;
        }
        dataCacheTable.setItems(DataCache.getAllAppointments());
        dataCacheTable.refresh();
    }
    
    /**
     * Displays all customers.
     */
    @FXML private void viewAllCusts() {
        
        viewingLabel.setText("Customers");
        searchField.setPromptText("Search All Customers");
        if (viewingAppointments) {
            dataCacheTable.getColumns().clear();
            TableFormatter.buildCustTables();
            viewingAppointments = false;
        }
        dataCacheTable.setItems(DataCache.getAllCustomers());
        dataCacheTable.refresh();
    }
    
    /**
     * Closes the appointment alert.
     *
     * @param event an ActionEvent indicating the user clicked the close button
     * @throws IOException if there is an issue closing the alert
     */
    @FXML void closeAppAlert(ActionEvent event) throws IOException {
        
        appAlertLabel.setVisible(false);
        appAlertInfoLabel.setVisible(false);
        closeAppAlertButton.setVisible(false);
    }
    
    /**
     * This method is called when the user types within the search field. It
     * filters the parts displayed in the parts table based on the user's search
     * input. If the input is a number, it searches for a part with that ID. If
     * there are no matches it continues to search for part names containing that
     * number. If the input is not a number, it searches for parts with a name
     * containing the input. If the input is empty, it displays all parts.
     */
    @FXML
    private void searchSchedule() {
        
        
        String searchText = searchField.getText().toLowerCase();
        
        if (viewingAppointments) {
            List<Appointment> appointmentsList = DataCache.getAllAppointments()
                    .stream()
                    .map(item -> (Appointment) item)
                    .collect(Collectors.toList());
            
            List<Appointment> filteredApps = ObjectSearchUtil.searchObjectFields(
                    appointmentsList,
                    searchText,
                    Appointment.class
            );
            
            dataCacheTable.setItems(FXCollections.observableArrayList(filteredApps));
        } else {
            List<Customer> customersList = DataCache.getAllCustomers()
                    .stream()
                    .map(item -> (Customer) item)
                    .collect(Collectors.toList());
            
            List<Customer> filteredCusts = ObjectSearchUtil.searchObjectFields(
                    customersList,
                    searchText,
                    Customer.class
            );
            
            dataCacheTable.setItems(FXCollections.observableArrayList(filteredCusts));
        }
        
        dataCacheTable.refresh();
    }
    
    
    /**
     * Opens the Add Record dialog for adding customers or appointments.
     *
     * @throws IOException if there is an issue opening the dialog
     */
    @FXML private void addRecord() throws IOException {
        
        disableDashboard(true);
        
        if (allCustsRadio.isSelected()) {
            SceneManager.buildCustomerScene();
            SceneManager.getStage("customer").setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override public void handle(WindowEvent event) {
                    
                    disableDashboard(false);
                }
            });
        } else {
            SceneManager.buildAppointmentScene();
            SceneManager.getStage("appointment").setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override public void handle(WindowEvent event) {
                    
                    disableDashboard(false);
                }
            });
        }
    }
    
    /**
     * Disables or enables the dashboard.
     *
     * @param theOption a boolean value, true to disable the dashboard, false to enable it
     */
    public void disableDashboard(boolean theOption) {
        
        SceneManager.getStage("dashboard").getScene().getRoot().setDisable(theOption);
    }
    
    /**
     * Opens the Modify Record dialog for modifying customers or appointments.
     *
     * @throws IOException if there is an issue opening the dialog
     */
    @FXML private void modifyRecord() throws IOException {
        
        if (!dataCacheTable.getSelectionModel().isEmpty()) {
            DataCache item = dataCacheTable.getSelectionModel().getSelectedItem();
            disableDashboard(true);
            
            if (item instanceof Appointment) {
                SceneManager.buildAppointmentScene();
                SceneManager.getStage("appointment").setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override public void handle(WindowEvent event) {
                        
                        disableDashboard(false);
                        dataCacheTable.refresh();
                    }
                });
                SceneManager.getAppointmentController().setAppointmentToModify(item);
            } else {
                SceneManager.buildCustomerScene();
                SceneManager.getStage("customer").setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override public void handle(WindowEvent event) {
                        
                        disableDashboard(false);
                        dataCacheTable.refresh();
                    }
                });
                SceneManager.getCustomerController().setCustomerToModify(item);
            }
        } else {
            Alerts.showAlert("No record selected.", "Nothing selected");
        }
    }
    
    /**
     * Deletes a selected record (customer or appointment) from the database.
     *
     * @throws SQLException if there is an issue accessing the database
     */
    @FXML private void deleteRecord() throws SQLException {
        
        if (dataCacheTable.getSelectionModel().isEmpty()) {
            Alerts.showWarning("You have not selected anything to delete.", "Nothing Selected");
        } else {
            boolean result = Alerts.showConfirmationDialog("Are you sure you want to delete the selected record?", "Delete?");
            
            if (result) {
                DataCache recordToDelete = dataCacheTable.getSelectionModel().getSelectedItem();
                DBConnection.openConnection();
                DAO<DataCache> dao;
                if (dataCacheTable.getSelectionModel().getSelectedItem() instanceof Appointment) {
                    dao = new AppointmentDAO(DBConnection.getConnection());
                    int appID = recordToDelete.getId();
                    String appType = ((Appointment) recordToDelete).getType();
                    dao.delete(appID);
                    DataCache.getAllAppointments().remove(dataCacheTable.getSelectionModel().getSelectedItem());
                    DataCache tempList = dataCacheTable.getSelectionModel().getSelectedItem();
                    dataCacheTable.getItems().remove(tempList);
                    Alerts.showAlert("Appointment " + appID + " for " + appType + " has been cancelled.", "Appointment Cancellation");
                } else if (hasAppointments()) {
                    Alerts.showWarning("Customer still has appointments", "Open Appointments");
                } else {
                    dao = new CustomerDAO(DBConnection.getConnection());
                    String custName = ((Customer) recordToDelete).getName();
                    dao.delete(recordToDelete.getId());
                    DataCache.getAllCustomers().remove(dataCacheTable.getSelectionModel().getSelectedItem());
                    DataCache tempList = dataCacheTable.getSelectionModel().getSelectedItem();
                    dataCacheTable.getItems().remove(tempList);
                    Alerts.showAlert("Customer " + custName + " has been removed.", "Customer Removal");
                }
            }
        }
    }
    
    /**
     * Checks if a selected customer has any appointments.
     *
     * @return true if the customer has appointments, false otherwise
     */
    private boolean hasAppointments() {
        
        boolean hasAppointments = false;
        List<Appointment> listOfAppointments = new ArrayList<>();
        Customer cust = (Customer) dataCacheTable.getSelectionModel().getSelectedItem();
        Appointment app;
        for (DataCache data : DataCache.getAllAppointments()) {
            app = (Appointment) data;
            if (cust.getId() == app.getCustomerID()) {
                listOfAppointments.add(app);
                hasAppointments = true;
            }
        }
        return hasAppointments;
    }
    
    /**
     * Displays a report showing the number of appointments by type and month.
     *
     * @throws IOException if an input/output error occurs while building the report scene.
     */
    @FXML private void viewAppointmentReport() throws IOException {
        
        disableDashboard(true);
        SceneManager.buildReportScene("appointment");
        SceneManager.getStage("appointmentreport").setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override public void handle(WindowEvent event) {
                
                disableDashboard(false);
            }
        });
    }
    
    /**
     * Displays a report showing a list of appointments by contact.
     *
     * @throws IOException if an input/output error occurs while building the report scene.
     */
    @FXML private void viewScheduleReport() throws IOException {
        
        disableDashboard(true);
        SceneManager.buildReportScene("schedule");
        SceneManager.getStage("schedulereport").setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override public void handle(WindowEvent event) {
                
                disableDashboard(false);
            }
        });
    }
    
    /**
     * Displays a report showing a list of customers and the different contacts they've seen.
     *
     * @throws IOException if an input/output error occurs while building the report scene.
     */
    @FXML private void viewCountReport() throws IOException {
        
        disableDashboard(true);
        SceneManager.buildReportScene("count");
        SceneManager.getStage("countreport").setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override public void handle(WindowEvent event) {
                
                disableDashboard(false);
            }
        });
    }
    
    /**
     * Logs out the current user and returns to the login screen.
     *
     * @throws IOException if there is an issue returning to the login screen
     */
    @FXML private void logout() throws IOException {
        
        DataCache.clearObjects();
        SceneManager.buildLoginScene();
        SceneManager.getStage("dashboard").close();
    }
    
}


