package MobileRechargeStore;


import MobileRechargeStore.ModifingMenu.AddCarrierToCountryController;
import MobileRechargeStore.ModifingMenu.AddCellCarrierController;
import MobileRechargeStore.ModifingMenu.AddCountryController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.util.Optional;


public class Controller {

    @FXML Parent mainWindow;
    @FXML ComboBox<String> selectTable;
    @FXML TableView dataTable;
    @FXML TextField searchBox;

    ResultSet temprs;
    String sql= null;
    String condition= null;

    public void initialize(){
        selectTable.getItems().addAll("Countries", "Cell Carriers", "Carrier to Country", "Tariffs", "Redemtion Code", "Payments", "Users", "Login Activity");
        searchBox.setDisable(true);
    }
    @FXML public void loadDataIntoTable() {
        sql= null;
        try {
            System.out.println("Selected Item is: "+ selectTable.getSelectionModel().getSelectedItem());
            switch (selectTable.getSelectionModel().getSelectedItem()){
                case "Countries":
                    sql= "select " +
                            "ph_code as 'Phone Number', " +
                            "country_name AS 'Country Name' " +
                          "From " +
                            "countries";
                    System.out.println("Resultset is Created for Countries");
                    break;
                case "Cell Carriers":
                    sql= "select " +
                            "id as 'Carrier Id', " +
                            "carrier_name AS 'Carrier Name' " +
                          "From " +
                            "cell_carriers";
                    System.out.println("Resultset is Created for Cell Carriers");
                    break;
                case "Carrier to Country":
                    sql= "SELECT " +
                            "cell_carriers.carrier_name AS 'Carrier Name', " +
                            "countries.country_name AS 'Country Name' " +
                          "FROM " +
                            "cell_carriers INNER JOIN carrier_to_country ON cell_carriers.id = carrier_to_country.carrier_id " +
                            "INNER JOIN countries ON carrier_to_country.country_code = countries.ph_code";
                    System.out.println("Resultset is Created for Carrier to Country");
                    break;
                case "Tariffs":
                    sql= "SELECT " +
                            "tariffs.id AS 'Tariff ID', " +
                            "cell_carriers.carrier_name AS 'Carrier Name', " +
                            "countries.country_name AS 'Country Name', " +
                            "tariffs.plan_type AS 'Plan Type', " +
                            "tariffs.description AS 'Description', " +
                            "tariffs.active_status AS 'Active Status', " +
                            "tariffs.price AS 'Price', " +
                            "tariffs.last_updated AS 'Last Modified' " +
                          "FROM " +
                            "tariffs INNER JOIN cell_carriers ON tariffs.carrier_id = cell_carriers.id " +
                            "INNER JOIN countries ON tariffs.country_id = countries.ph_code";
                    System.out.println("Resultset is Created for Tariffs");
                    break;
                case "Redemtion Code":
                    sql= "SELECT " +
                            "offer_code AS 'Offer Code', " +
                            "description AS 'Description', " +
                            "active_status AS 'Active Status', " +
                            "times_used AS 'Times Used', " +
                            "price_discount AS 'Discounted Price', " +
                            "offer_creation_time AS 'Created On', " +
                            "offer_expired AS 'Expires On' " +
                          "FROM " +
                            "redemtion_codes";
                    System.out.println("Resultset is Created for Redemtion Code");
                    break;
                case "Payments":
                    sql= "SELECT " +
                            "payments.transaction_id AS 'Transaction ID', " +
                            "payments.user_id AS 'User ID', " +
                            "countries.country_name AS 'Country Name', " +
                            "payments.ph_no AS 'Phone Number', " +
                            "payments.coupon_sl_no AS 'Coupon No.', " +
                            "payments.redemtion_applied AS 'Redemtion Code', " +
                            "payments.price_paid AS 'Price Paid', " +
                            "payments.details AS 'Details', " +
                            "payments.payment_date AS 'Payment Date' " +
                          "FROM " +
                            "payments INNER JOIN countries on payments.country_code = countries.ph_code";
                    System.out.println("Resultset is Created for Payments");
                    break;
                case "Users":
                    sql= "SELECT " +
                            "users.id AS 'User ID', " +
                            "users.phone_country AS 'Verified Number', " +
                            "countries.country_name AS 'Country Name', " +
                            "users.active_status AS 'Active status', " +
                            "users.account_created AS 'Account Created', " +
                            "users.last_update AS 'last Updated' " +
                          "FROM " +
                            "users INNER JOIN countries ON users.country_code = countries.ph_code";
                    System.out.println("Resultset is Created for Users");
                    break;
                case "Login Activity":
                    sql= "SELECT " +
                            "user_id AS 'User ID', " +
                            "login_ip AS 'Login IP', " +
                            "login_time AS 'Login Time' " +
                          "FROM " +
                            "login_activity";
                    System.out.println("Resultset is Created for Login Activity");
                    break;
            }
            temprs = DbConnection.getInstance().getConnection().createStatement().executeQuery(sql);
            new DrawTable().setTableData(temprs, dataTable);
            searchBox.setDisable(false);
            System.out.println("Table Drawn");
        } catch (Exception e) {
            System.out.println("Cannot Query For the Database: " + e.getMessage());
        }
    }

    @FXML public void searchDataIntoTable(){
        try{
            condition= null;
            switch(selectTable.getSelectionModel().getSelectedItem()){
                case "Countries":
                    condition=  "WHERE " +
                                    "ph_code LIKE '%" + searchBox.getText() +
                                    "%' OR country_name LIKE '%" + searchBox.getText() + "%'";
                    break;
                case "Cell Carriers":
                    condition=  "WHERE " +
                                    "id LIKE '%" + searchBox.getText() +
                                    "%' OR carrier_name LIKE '%" + searchBox.getText() + "%'";
                    break;
                case "Carrier to Country":
                    condition= "WHERE " +
                                    "cell_carriers.carrier_name LIKE '%" + searchBox.getText() +
                                    "%' OR countries.country_name LIKE '%" + searchBox.getText() + "%'";
                    break;
                case "Tariffs":
                    condition= "WHERE " +
                                    "tariffs.id LIKE '%" + searchBox.getText() +
                                    "%' OR cell_carriers.carrier_name LIKE '%" + searchBox.getText() +
                                    "%' OR countries.country_name LIKE '%" + searchBox.getText() +
                                    "%' OR tariffs.plan_type LIKE '%" + searchBox.getText() +
                                    "%' OR tariffs.active_status LIKE '%" + searchBox.getText() +
                                    "%' OR tariffs.price LIKE '%" + searchBox.getText() +
                                    "%' OR tariffs.last_updated LIKE '%" + searchBox.getText() + "%'";
                    break;
                case "Redemtion Code":
                    condition= "WHERE " +
                                    "offer_code LIKE '%" + searchBox.getText() +
                                    "%' OR active_status LIKE '%" + searchBox.getText() +
                                    "%' OR times_used LIKE '%" + searchBox.getText() +
                                    "%' OR price_discount LIKE '%" + searchBox.getText() +
                                    "%' OR offer_creation_time LIKE '%" + searchBox.getText() +
                                    "%' OR offer_expired LIKE '%" + searchBox.getText() + "%'";
                    break;
                case "Payments":
                    condition= "WHERE " +
                            "payments.transaction_id LIKE '%" + searchBox.getText() +
                            "%' OR payments.user_id LIKE '%" + searchBox.getText() +
                            "%' OR countries.country_name LIKE '%" + searchBox.getText() +
                            "%' OR payments.ph_no LIKE '%" + searchBox.getText() +
                            "%' OR payments.coupon_sl_no LIKE '%" + searchBox.getText() +
                            "%' OR payments.redemtion_applied LIKE '%" + searchBox.getText() +
                            "%' OR payments.price_paid LIKE '%" + searchBox.getText() +
                            "%' OR payments.payment_date LIKE '%" + searchBox.getText() + "%'";
                    break;
                case "Users":
                    condition= "WHERE " +
                            "users.id LIKE '%" + searchBox.getText() +
                            "%' OR users.phone_country LIKE '%" + searchBox.getText() +
                            "%' OR countries.country_name LIKE '%" + searchBox.getText() +
                            "%' OR users.active_status LIKE '%" + searchBox.getText() +
                            "%' OR users.account_created LIKE '%" + searchBox.getText() +
                            "%' OR users.last_update LIKE '%" + searchBox.getText() + "%'";
                    break;
                case "Login Activity":
                    condition= "WHERE " +
                            "user_id LIKE '%" + searchBox.getText() +
                            "%' OR login_ip LIKE '%" + searchBox.getText() +
                            "%' OR login_time LIKE '%" + searchBox.getText() + "%'";
                    break;
            }
            System.out.println("Search SQL: "+ sql +" "+ condition);
            temprs = DbConnection.getInstance().getConnection().createStatement().executeQuery(sql+" "+condition);
            new DrawTable().setTableData(temprs, dataTable);
            System.out.println("Table Drawn");
        }catch (Exception e) {
            System.out.println("Cannot Search For the Database: " + e.getMessage());
        }
    }

    @FXML public void addCountry(){
        Dialog<ButtonType> addCountryDilog = new Dialog<>();
        addCountryDilog.initOwner(mainWindow.getScene().getWindow());
        addCountryDilog.setTitle("Adding Country");
        FXMLLoader addCountryFXML= new FXMLLoader(getClass().getResource("ModifingMenu/countryWindow.fxml"));
        try{
            addCountryDilog.getDialogPane().setContent(addCountryFXML.load());
        }catch (Exception e){
            System.out.println("DilogPane Scene graph Cannot be loaded: ");
            e.printStackTrace();
        }
        addCountryDilog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        addCountryDilog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> addCountryDilogResult= addCountryDilog.showAndWait();
        if(addCountryDilogResult.isPresent() && addCountryDilogResult.get() == ButtonType.APPLY){
            AddCountryController addAddCountryControllerTemp = addCountryFXML.getController();
            try {
                addAddCountryControllerTemp.addCountryCommand().executeUpdate();
                Alert countryAdded = new Alert(Alert.AlertType.INFORMATION);
                countryAdded.setTitle("Insertion Done");
                countryAdded.setHeaderText("The Country Added");
                countryAdded.showAndWait();
            }catch (Exception e){
                Alert countryNotAdded = new Alert(Alert.AlertType.ERROR);
                countryNotAdded.setTitle("Insert Error");
                countryNotAdded.setHeaderText("There was a problem while Inserting The Country");
                countryNotAdded.setContentText("It could be a network issue or the duplicate Ph Code. "+ e.getMessage());
                countryNotAdded.showAndWait();
            }
        }
        addCountryDilog.close();
    }

    @FXML public void addCellCarrier(){
        Dialog<ButtonType> addCellCountryDilog = new Dialog<>();
        addCellCountryDilog.initOwner(mainWindow.getScene().getWindow());
        addCellCountryDilog.setTitle("Adding Country");
        FXMLLoader addCellCarrierFXML= new FXMLLoader(getClass().getResource("ModifingMenu/cellCarrierWindow.fxml"));
        try{
            addCellCountryDilog.getDialogPane().setContent(addCellCarrierFXML.load());
        }catch (Exception e){
            System.out.println("DilogPane Scene graph Cannot be loaded: ");
            e.printStackTrace();
        }
        addCellCountryDilog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        addCellCountryDilog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> addCountryDilogResult= addCellCountryDilog.showAndWait();
        if(addCountryDilogResult.isPresent() && addCountryDilogResult.get() == ButtonType.APPLY){
            AddCellCarrierController addCellCarrierControllerTemp = addCellCarrierFXML.getController();
            try {
                addCellCarrierControllerTemp.addCellCarrierCommand().executeUpdate();
                Alert CarrierAdded = new Alert(Alert.AlertType.INFORMATION);
                CarrierAdded.setTitle("Insertion Done");
                CarrierAdded.setHeaderText("The Carrier Added");
                CarrierAdded.showAndWait();
            }catch (Exception e){
                Alert carrierNotAdded = new Alert(Alert.AlertType.ERROR);
                carrierNotAdded.setTitle("Insert Error");
                carrierNotAdded.setHeaderText("There was a problem while Inserting The Carrier");
                carrierNotAdded.setContentText("It could be a network issue or the duplicate Record. "+ e.getMessage());
                carrierNotAdded.showAndWait();
            }
        }
        addCellCountryDilog.close();
    }

    @FXML public void addCarrierToCountry(){
        Dialog<ButtonType> addCarrierToCountryDilog = new Dialog<>();
        addCarrierToCountryDilog.initOwner(mainWindow.getScene().getWindow());
        addCarrierToCountryDilog.setTitle("Adding Country");
        FXMLLoader addCarrierToCountryFXML= new FXMLLoader(getClass().getResource("ModifingMenu/carrierToCountryWindow.fxml"));
        try{
            addCarrierToCountryDilog.getDialogPane().setContent(addCarrierToCountryFXML.load());
        }catch (Exception e){
            System.out.println("DilogPane Scene graph Cannot be loaded: ");
            e.printStackTrace();
        }
        addCarrierToCountryDilog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        addCarrierToCountryDilog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> addCarrierToCountryDilogResult= addCarrierToCountryDilog.showAndWait();
        if(addCarrierToCountryDilogResult.isPresent() && addCarrierToCountryDilogResult.get() == ButtonType.APPLY){
            AddCarrierToCountryController addCarrierToControllerTemp = addCarrierToCountryFXML.getController();
            try {
                addCarrierToControllerTemp.addCarrierToCountryCommand().executeUpdate();
                Alert CarrierAdded = new Alert(Alert.AlertType.INFORMATION);
                CarrierAdded.setTitle("Insertion Done");
                CarrierAdded.setHeaderText("The Carrier To Country Added");
                CarrierAdded.showAndWait();
            }catch (Exception e){
                Alert carrierNotAdded = new Alert(Alert.AlertType.ERROR);
                carrierNotAdded.setTitle("Insert Error");
                carrierNotAdded.setHeaderText("There was a problem while Inserting The Carrier to Country");
                carrierNotAdded.setContentText("It could be a network issue or the duplicate Record. "+ e.getMessage());
                carrierNotAdded.showAndWait();
            }
        }
        addCarrierToCountryDilog.close();
    }
}
