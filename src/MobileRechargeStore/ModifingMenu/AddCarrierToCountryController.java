package MobileRechargeStore.ModifingMenu;

import MobileRechargeStore.DbConnection;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.CharArrayReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddCarrierToCountryController {
    @FXML ComboBox<String> selectCarrierCountryName;
    @FXML TextField addCarrierCountryIDField;
    @FXML ComboBox<String> selectCountryCarrierName;
    @FXML TextField addCountryCarrierIDField;

    public void initialize(){
        try {
            ResultSet fetchCountries = DbConnection.getInstance().getConnection().createStatement().executeQuery("SELECT country_name FROM countries");
            while (fetchCountries.next()){
                selectCarrierCountryName.getItems().add(fetchCountries.getString(1));
            }
        }catch (Exception e){
            System.out.println("Countries Cannot Be fetched:"+ e.getMessage());
        }
        try {
            ResultSet fetchCarrier = DbConnection.getInstance().getConnection().createStatement().executeQuery("SELECT carrier_name FROM cell_carriers");
            while (fetchCarrier.next()){
                selectCountryCarrierName.getItems().add(fetchCarrier.getString(1));
            }
        }catch (Exception e){
            System.out.println("Carriers Cannot be fetched: "+ e.getMessage());
        }
    }

    @FXML public PreparedStatement addCarrierToCountryCommand() throws SQLException {
        PreparedStatement addCellCarrierPreparedStatement= DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO carrier_to_country(country_code ,carrier_id) VALUES (?,?)");
        addCellCarrierPreparedStatement.setString(1, addCarrierCountryIDField.getText());
        addCellCarrierPreparedStatement.setString(2, addCountryCarrierIDField.getText());
        return addCellCarrierPreparedStatement;

    }

    @FXML public void populateCountryId(){
        try {
            ResultSet fetchCountryID = DbConnection.getInstance().getConnection().createStatement().executeQuery("SELECT ph_code FROM countries WHERE country_name = '" + selectCarrierCountryName.getSelectionModel().getSelectedItem( )+ "'");
            fetchCountryID.next();
            addCarrierCountryIDField.setText(fetchCountryID.getString(1));
        }catch (Exception e){
            System.out.println("Cannot Fetch the Country ID: "+e.getMessage());
        }
    }

    @FXML public void populateCarrierId(){
        try {
            ResultSet fetchCarrierId = DbConnection.getInstance().getConnection().createStatement().executeQuery("SELECT id FROM cell_carriers WHERE carrier_name = '" + selectCountryCarrierName.getSelectionModel().getSelectedItem( )+ "'");
            fetchCarrierId.next();
            addCountryCarrierIDField.setText(fetchCarrierId.getString(1));
        }catch (Exception e){
            System.out.println("Cannot Fetch the Country ID: "+e.getMessage());
        }
    }
}
