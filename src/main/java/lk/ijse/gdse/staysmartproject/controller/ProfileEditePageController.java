package lk.ijse.gdse.staysmartproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private TextField txtConformUserName;

    @FXML
    private TextField txtNewUserName;

    @FXML
    void btnChangeUserNameAction(ActionEvent event) {

    }

    @FXML
    void btnProfimeImageUploadAction(ActionEvent event) {

    }

    public void initialize() {
        loadUserProfile();
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

}
