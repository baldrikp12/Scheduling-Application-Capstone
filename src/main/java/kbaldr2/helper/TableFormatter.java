package kbaldr2.helper;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import kbaldr2.model.DataCache;

import java.time.LocalDateTime;

/**
 * A utility class for constructing and formatting tables with specific
 * columns and properties for appointment and customer data.
 */
public class TableFormatter {
    
    private static final TableColumn<DataCache, Integer> app_AppointmentIdCol = new TableColumn<>("Appointment ID");
    private static final TableColumn<DataCache, String> app_TitleCol = new TableColumn<>("Title");
    private static final TableColumn<DataCache, String> app_DescriptionCol = new TableColumn<>("Description");
    private static final TableColumn<DataCache, String> app_LocationCol = new TableColumn<>("Location");
    private static final TableColumn<DataCache, String> app_ContactCol = new TableColumn<>("Contact");
    private static final TableColumn<DataCache, String> app_TypeCol = new TableColumn<>("Type");
    private static final TableColumn<DataCache, LocalDateTime> app_StartDateAndTimeCol = new TableColumn<>("Start Date and Time");
    private static final TableColumn<DataCache, LocalDateTime> app_EndDateAndTimeCol = new TableColumn<>("End Date and Time");
    private static final TableColumn<DataCache, Integer> app_CustomerIdCol = new TableColumn<>("Customer ID");
    private static final TableColumn<DataCache, Integer> app_UserIdCol = new TableColumn<>("User ID");
    private static final TableColumn<DataCache, Integer> cust_CustIdCol = new TableColumn<>("Customer ID");
    private static final TableColumn<DataCache, String> cust_NameCol = new TableColumn<>("Name");
    private static final TableColumn<DataCache, String> cust_AddressCol = new TableColumn<>("Address");
    private static final TableColumn<DataCache, String> cust_PostalCol = new TableColumn<>("Postal Code");
    private static final TableColumn<DataCache, String> cust_PhoneCol = new TableColumn<>("Phone #");
    private static final TableColumn<DataCache, Integer> cust_DivisionIdCol = new TableColumn<>("Division ID");
    private static TableView<DataCache> dataCacheTable;
    
    /**
     * Builds the appointment table with appropriate columns and properties.
     */
    public static void buildAppTables() {
        //builds appointments table
        app_AppointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        dataCacheTable.getColumns().add(app_AppointmentIdCol);
        
        app_TitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        dataCacheTable.getColumns().add(app_TitleCol);
        
        app_DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        dataCacheTable.getColumns().add(app_DescriptionCol);
        
        app_LocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        dataCacheTable.getColumns().add(app_LocationCol);
        
        app_TypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        dataCacheTable.getColumns().add(app_TypeCol);
        
        app_StartDateAndTimeCol.setCellValueFactory(new PropertyValueFactory<>("StartDateAndTimeFormatted"));
        dataCacheTable.getColumns().add(app_StartDateAndTimeCol);
        
        app_EndDateAndTimeCol.setCellValueFactory(new PropertyValueFactory<>("EndDateAndTimeFormatted"));
        dataCacheTable.getColumns().add(app_EndDateAndTimeCol);
        
        app_CustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        dataCacheTable.getColumns().add(app_CustomerIdCol);
        
        app_UserIdCol.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        dataCacheTable.getColumns().add(app_UserIdCol);
        
        app_ContactCol.setCellValueFactory(new PropertyValueFactory<>("ContactID"));
        dataCacheTable.getColumns().add(app_ContactCol);
    }
    
    /**
     * Builds the customer table with appropriate columns and properties.
     */
    public static void buildCustTables() {
        
        //builds customers table
        cust_CustIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        dataCacheTable.getColumns().add(cust_CustIdCol);
        
        cust_NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        dataCacheTable.getColumns().add(cust_NameCol);
        
        cust_AddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        dataCacheTable.getColumns().add(cust_AddressCol);
        
        cust_PostalCol.setCellValueFactory(new PropertyValueFactory<>("PostalCode"));
        dataCacheTable.getColumns().add(cust_PostalCol);
        
        cust_PhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        dataCacheTable.getColumns().add(cust_PhoneCol);
        
        cust_DivisionIdCol.setCellValueFactory(new PropertyValueFactory<>("DivisionID"));
        dataCacheTable.getColumns().add(cust_DivisionIdCol);
        
    }
    
    /**
     * Sets the TableView instance to be used by the TableFormatter.
     *
     * @param theTableView The TableView instance to be used for formatting
     */
    public static void setTableView(TableView theTableView) {
        
        dataCacheTable = theTableView;
        
    }
    
}
