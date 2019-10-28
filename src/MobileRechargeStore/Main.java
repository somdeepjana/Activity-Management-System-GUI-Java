package MobileRechargeStore;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
//        primaryStage.setScene(new Scene(root, 900, 600));
//        primaryStage.setTitle("Hello World");
//        primaryStage.show();
        FXMLLoader connectionWindowFXML = new FXMLLoader(getClass().getResource("connectionWindow.fxml"));
        connectionController tempController= connectionWindowFXML.getController();
        Parent root= connectionWindowFXML.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Login to MobileRechargeStore Database");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
