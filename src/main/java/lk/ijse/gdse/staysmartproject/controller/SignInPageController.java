package lk.ijse.gdse.staysmartproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.staysmartproject.dto.UserDTO;
import lk.ijse.gdse.staysmartproject.model.UserModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class SignInPageController {

    @FXML
    private PasswordField PFSignInPassword;

    @FXML
    private Button btnSignInCreateAcc;

    @FXML
    private Button btnSignInForgotPassword;

    @FXML
    private Button btnSignInLoging;

    @FXML
    private ImageView btnSignInView;

    @FXML
    private AnchorPane singInPage;

    @FXML
    private TextField txtSignInPassword;

    @FXML
    private TextField txtSignInUserName;

    private UserModel userModel = new UserModel();

    @FXML
    void PFSignInPasswordAction(ActionEvent event) {

    }

    @FXML
    void btnSignInCreateAccAction(ActionEvent event) throws IOException {
        singInPage.getChildren().clear();
        AnchorPane newPage = FXMLLoader.load(getClass().getResource("/view/SignUpPage.fxml"));
        singInPage.getChildren().add(newPage);
    }

    @FXML
    void btnSignInForgotPasswordAction(ActionEvent event) throws IOException {
        singInPage.getChildren().clear();
        AnchorPane newPage = FXMLLoader.load(getClass().getResource("/view/ForgotPasswordPage.fxml"));
        singInPage.getChildren().add(newPage);
    }

    @FXML
    void btnSignInLogingAction(ActionEvent event) throws SQLException {
        if (checkUserNameAndPassword()){
            loadUI("/view/MainDashboardPage.fxml");
        } else {
            showErrorMessage("Incorrect username or password. Please try again.");
        }
    }

    @FXML
    void btnSignInViewAction(MouseEvent event) {

    }

    @FXML
    void txtSignInPasswordAction(ActionEvent event) {

    }

    private boolean checkUserNameAndPassword() throws SQLException {
        List<UserDTO> allUsers = userModel.getAllUsers();

        for (UserDTO user : allUsers) {
            if (user.getName().equals(txtSignInUserName.getText()) && user.getPassword().equals(PFSignInPassword.getText())) {
                return true;
            }
        }
        return true;
    }

    private void loadUI(String resource) {
        singInPage.getChildren().clear();
        try {
            singInPage.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
