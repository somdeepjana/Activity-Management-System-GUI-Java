package MobileRechargeStore;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;


public class AboutController extends Application {

    @FXML Hyperlink GitHubLink;

    public void initialize(){
        GitHubLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                getHostServices().showDocument("https://github.com/somdeepjana/JavaActivityManagement");
            }
        });
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
