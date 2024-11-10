package lk.ijse.gdse.staysmartproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class WelcomePageController {

    @FXML
    private Button btnStartNow;

    @FXML
    private AnchorPane welcomePage;

    @FXML
    private Button btnSignInLoging;

    @FXML
    void btnStartNowAction(ActionEvent event) throws IOException {
        welcomePage.getChildren().clear();
        welcomePage.getChildren().add(FXMLLoader.load(getClass().getResource("/view/SignInPage.fxml")));
    }

    @FXML
    void btnSignInLogingAction(ActionEvent event) {

    }

}
