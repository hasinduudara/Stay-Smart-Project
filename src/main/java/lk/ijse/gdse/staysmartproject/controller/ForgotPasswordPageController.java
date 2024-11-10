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

public class ForgotPasswordPageController {

    @FXML
    private PasswordField FPFPNewPassword;

    @FXML
    private PasswordField PFFPComPassword;

    @FXML
    private ImageView btnFPComPasswordView;

    @FXML
    private Button btnFPForgotPassword;

    @FXML
    private Button btnFPGetCode;

    @FXML
    private ImageView btnFPNewPasswordView;

    @FXML
    private AnchorPane forgotPasswordPage;

    @FXML
    private TextField txtFPComPassword;

    @FXML
    private TextField txtFPEnterCode;

    @FXML
    private TextField txtFPNewPassword;

    @FXML
    void FPFPNewPasswordAction(ActionEvent event) {

    }

    @FXML
    void PFFPComPasswordAction(ActionEvent event) {

    }

    @FXML
    void btnFPComPasswordViewAction(MouseEvent event) {

    }

    @FXML
    void btnFPForgotPasswordAction(ActionEvent event) throws IOException {
        forgotPasswordPage.getChildren().clear();
        AnchorPane newPage = FXMLLoader.load(getClass().getResource("/view/SignInPage.fxml"));
        forgotPasswordPage.getChildren().add(newPage);
    }

    @FXML
    void btnFPGetCodeAction(ActionEvent event) {

    }

    @FXML
    void btnFPNewPasswordViewAction(MouseEvent event) {

    }

    @FXML
    void txtFPComPasswordAction(ActionEvent event) {

    }

    @FXML
    void txtFPNewPasswordAction(ActionEvent event) {

    }

}
