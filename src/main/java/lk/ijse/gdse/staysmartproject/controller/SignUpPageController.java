package lk.ijse.gdse.staysmartproject.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.gdse.staysmartproject.dto.UserDTO;
import lk.ijse.gdse.staysmartproject.model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpPageController {

    @FXML
    private PasswordField PFSignUpPassword;

    @FXML
    private Button btnSignUpCreateAcc;

    @FXML
    private Button btnSignUpLogingAcc;

    @FXML
    private ImageView btnSignUpView;

    @FXML
    private AnchorPane signUpPage;

    @FXML
    private TextField txtSignUpEmail;

    @FXML
    private TextField txtSignUpName;

    @FXML
    private TextField txtSignUpPassword;

    @FXML
    private TextField txtSignUpUsername;

    @FXML
    private Label lblErrorMessage;

    private UserDTO registeringUser = new UserDTO();
    private UserModel userModel = new UserModel();

    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_]{5,15}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    @FXML
    void PFSignUpPasswordAction(ActionEvent event) {

    }

    @FXML
    void btnSignUpCreateAccAction(ActionEvent event) throws SQLException, IOException {
        saveUser();
    }

    @FXML
    void btnSignUpLogingAccAction(ActionEvent event) throws IOException, SQLException {
        signUpPage.getChildren().clear();
        AnchorPane newPage = FXMLLoader.load(getClass().getResource("/view/SignInPage.fxml"));
        signUpPage.getChildren().add(newPage);
    }

    @FXML
    void btnSignUpViewAction(MouseEvent event) {

    }

    @FXML
    void txtSignUpPasswordAction(ActionEvent event) {

    }

    private void saveUser() throws SQLException {
        if (areFieldsEmpty()) {
            showErrorMessage("Required fields cannot be empty.");
        } else if (!isValidUsername(txtSignUpUsername.getText())) {
            showErrorMessage("Username must be 5-15 characters, containing only letters, digits, or underscores.");
        } else if (!isValidPassword(PFSignUpPassword.getText())) {
            showErrorMessage("Password must be at least 8 characters long, contain a digit, a lowercase letter, an uppercase letter, and a special character.");
        } else if (!isValidEmail(txtSignUpEmail.getText())) {
            showErrorMessage("Invalid email format.");
        } else {
            registeringUser.setUser_ID(userModel.getNextUserId());
            registeringUser.setName(txtSignUpName.getText());
            registeringUser.setEmail(txtSignUpEmail.getText());
            registeringUser.setUser_Name(txtSignUpUsername.getText());
            registeringUser.setPassword(PFSignUpPassword.getText());

            if (userModel.saveUser(registeringUser)) {
                loadUI("/view/SignInPage.fxml");
            } else {
                showErrorMessage("*User not saved.");
            }
        }
    }

    private boolean isValidUsername(String username) {
        return username.matches(USERNAME_PATTERN);
    }

    private boolean isValidPassword(String password) {
        return password.matches(PASSWORD_PATTERN);
    }

    private boolean isValidEmail(String email) {
        return email.matches(EMAIL_PATTERN);
    }

    private void loadUI(String ui) {
        try {
            signUpPage.getChildren().clear();
            signUpPage.getChildren().add(FXMLLoader.load(getClass().getResource(ui)));
        } catch (IOException e) {
            showErrorMessage("Error loading the page: " + e.getMessage());
        }
    }

    private boolean areFieldsEmpty() {
        return txtSignUpUsername.getText().isEmpty() ||
                PFSignUpPassword.getText().isEmpty() ||
                txtSignUpEmail.getText().isEmpty() ||
                txtSignUpName.getText().isEmpty();
    }

    private void showErrorMessage(String message) {
        lblErrorMessage.setText(message);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(5),
                ae -> lblErrorMessage.setText("")
        ));
        timeline.play();
    }


}
