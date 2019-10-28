package MobileRechargeStore.ModifingMenu;

import MobileRechargeStore.DbConnection;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddCellCarrierController {

    @FXML TextField addCellCarrierIdField;
    @FXML TextField addCellCarrierNameField;

    @FXML public PreparedStatement addCellCarrierCommand() throws SQLException {
        PreparedStatement addCellCarrierPreparedStatement= DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO cell_carriers(id, carrier_name) VALUES (?,?)");
        addCellCarrierPreparedStatement.setString(1, addCellCarrierIdField.getText());
        addCellCarrierPreparedStatement.setString(2, addCellCarrierNameField.getText().toUpperCase());
        return addCellCarrierPreparedStatement;

    }
}
