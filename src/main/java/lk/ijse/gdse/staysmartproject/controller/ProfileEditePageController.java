package lk.ijse.gdse.staysmartproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.staysmartproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileEditePageController {

    @FXML
    private Button btnChangeUserName;

    @FXML
    private Button btnProfimeImageUpload;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblName;

    @FXML
    private Label lblUserName;

    @FXML
    private AnchorPane profileEditePage;

    @FXML
    private ImageView profileEditePageImage;

    @FXML
    private TextField txtNewUserName;

    @FXML
    private Button btnChangeEmail;

    @FXML
    private TextField txtNewEmail;

    @FXML
    void btnChangeUserNameAction(ActionEvent event) {
        String newUserName = txtNewUserName.getText();

        try {
            boolean isUpdated = CrudUtil.execute("UPDATE User SET User_Name = ? WHERE User_ID = ?", newUserName, "U001");
            if (isUpdated) {
                lblUserName.setText(newUserName);
                new Alert(Alert.AlertType.INFORMATION, "User name updated successfully.").show();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update user name.").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while updating the user name.").show();
        }
    }

    public void initialize() {
        loadUserProfile();

        txtNewUserName.setOnAction(event -> btnChangeUserName.fire());
        txtNewEmail.setOnAction(event -> btnChangeEmail.fire());
    }

    private void loadUserProfile() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT Name, User_Name, Email FROM User WHERE User_ID = ?", "U001");
            if (resultSet.next()) {
                lblName.setText(resultSet.getString("Name"));
                lblUserName.setText(resultSet.getString("User_Name"));
                lblEmail.setText(resultSet.getString("Email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtNewUserName.clear();
        txtNewEmail.clear();
    }

    @FXML
    void btnChangeEmailAction(ActionEvent event) {
        String newEmail = txtNewEmail.getText();

        try {
            boolean isUpdated = CrudUtil.execute("UPDATE User SET Email = ? WHERE User_ID = ?", newEmail, "U001");
            if (isUpdated) {
                lblEmail.setText(newEmail);
                new Alert(Alert.AlertType.INFORMATION, "Email updated successfully.").show();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update email.").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while updating the email.").show();
        }
    }

}
