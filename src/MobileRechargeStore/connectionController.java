package MobileRechargeStore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class connectionController {

    @FXML GridPane loginWindow;

    @FXML TextField serverLocationField;
    @FXML TextField portField;
    @FXML TextField userIdField;
    @FXML PasswordField passwordField;

    @FXML public void logedIn(){
        Stage acurePrimaryStage=(Stage) loginWindow.getScene().getWindow();
        if(serverLocationField.getText().trim().isEmpty()){
            DbConnection.getInstance().setServerLocation("localhost");
        }else{
            DbConnection.getInstance().setServerLocation(serverLocationField.getText().trim());
        }
        if(portField.getText().trim().isEmpty()){
            DbConnection.getInstance().setServerPort("3306");
        }else{
            DbConnection.getInstance().setServerPort(portField.getText().trim());
        }
        if(userIdField.getText().trim().isEmpty()){
            DbConnection.getInstance().setDbUserId("root");
        }else{
            DbConnection.getInstance().setDbUserId(userIdField.getText().trim());
        }
        DbConnection.getInstance().setDbPassword(passwordField.getText().trim());
        try{
            if(DbConnection.getInstance().getConnection().isValid(2)){
                try {
                    Parent mainScene= FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
                    acurePrimaryStage.setScene(new Scene(mainScene,900,600));
                    acurePrimaryStage.setTitle("Mobile Recharge Store");
                    acurePrimaryStage.show();
                }catch (Exception e){
                    System.out.println("Cannot Load the mainWindow.fxml: "+ e.getMessage());
                }
            }
        }catch (Exception e){
            System.out.println("Cannot Open the DB Connection: ");
            e.getStackTrace();
            Alert databaseNotConnect = new Alert(Alert.AlertType.ERROR);
            databaseNotConnect.setTitle("Database Error");
            databaseNotConnect.setHeaderText("Cannot Connect To the Database");
            databaseNotConnect.setContentText("Check The Details");
            databaseNotConnect.showAndWait();
        }
    }

    @FXML public void closeApplication(){
        System.exit(0);
    }
}
