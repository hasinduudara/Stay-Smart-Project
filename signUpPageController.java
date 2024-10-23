package lk.ijse.gdse.staysmartproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class signUpPageController {

    @FXML
    private PasswordField PFSignUpPassword;

    @FXML
    private Button btnSignUpCreateAcc;

    @FXML
    private Button btnSignUpLogingAcc;

    @FXML
    private ImageView btnSignUpView;

    @FXML
    private TextField txtSignUpEmail;

    @FXML
    private TextField txtSignUpName;

    @FXML
    private TextField txtSignUpPassword;

    @FXML
    private TextField txtSignUpUsername;

    @FXML
    private AnchorPane signUpPage;

    @FXML
    void PFSignUpPasswordAction(ActionEvent event) {

    }

    @FXML
    void btnSignUpCreateAccAction(ActionEvent event) throws IOException {
        signUpPage.getChildren().clear();
        signUpPage.getChildren().add(FXMLLoader.load(getClass().getResource("/view/signInPage.fxml")));
    }

    @FXML
    void btnSignUpLogingAccAction(ActionEvent event) {

    }

    @FXML
    void btnSignUpViewAction(MouseEvent event) {

    }

    @FXML
    void txtSignUpPasswordAction(ActionEvent event) {

    }

}
