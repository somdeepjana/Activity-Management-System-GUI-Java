package MobileRechargeStore.ModifingMenu;

import MobileRechargeStore.DbConnection;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddCountryController {

    @FXML TextField addCountryNameField;
    @FXML TextField addCountryPhCodeField;

    @FXML public PreparedStatement addCountryCommand() throws SQLException {
        PreparedStatement addCountryPreparedStatement= DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO countries(ph_code, country_name) VALUES (?,?)");
        addCountryPreparedStatement.setString(1, addCountryPhCodeField.getText().toUpperCase());
        addCountryPreparedStatement.setString(2, addCountryNameField.getText());
        return addCountryPreparedStatement;

    }
}
