package lk.ijse.gdse.staysmartproject.controller;

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
import lk.ijse.gdse.staysmartproject.util.CrudUtil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ForgotPasswordPageController {

    @FXML
    private PasswordField FPFPNewPassword;

    @FXML
    private PasswordField PFFPComPassword;

    @FXML
    private Button btnCheckEmail;

    @FXML
    private ImageView btnFPComPasswordView;

    @FXML
    private Button btnFPForgotPassword;

    @FXML
    private Button btnFPGetCode;

    @FXML
    private ImageView btnFPNewPasswordView;

    @FXML
    private Button btnGoToSignInPage;

    @FXML
    private AnchorPane forgotPasswordPage;

    @FXML
    private Label lblErrorMessage;

    @FXML
    private Label lblYourEmail;

    @FXML
    private TextField txtFPComPassword;

    @FXML
    private TextField txtFPEnterCode;

    @FXML
    private TextField txtFPNewPassword;

    @FXML
    private TextField txtUserName;

    private String generatedOTP = "";

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";

    @FXML
    void FPFPNewPasswordAction(ActionEvent event) {

    }

    @FXML
    void PFFPComPasswordAction(ActionEvent event) {

    }

    @FXML
    void btnCheckEmailAction(ActionEvent event) {
        String userName = txtUserName.getText();
        try {
            ResultSet result = CrudUtil.execute("SELECT Email FROM User WHERE User_Name = ?", userName);
            if (result.next()) {
                String email = result.getString("Email");
                lblYourEmail.setText(email);
            } else {
                lblYourEmail.setText("User not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lblYourEmail.setText("Error retrieving email");
        }
    }

    @FXML
    void btnFPComPasswordViewAction(MouseEvent event) {

    }

    @FXML
    void btnFPForgotPasswordAction(ActionEvent event) {
        String newPassword = FPFPNewPassword.getText();
        String confirmPassword = PFFPComPassword.getText();
        String enteredOTP = txtFPEnterCode.getText();

        if (!newPassword.equals(confirmPassword)) {
            lblErrorMessage.setText("Passwords do not match.");
            return;
        }

        if (!enteredOTP.equals(generatedOTP)) {
            lblErrorMessage.setText("Invalid OTP code.");
            return;
        }

        String email = lblYourEmail.getText();
        try {
            CrudUtil.execute("UPDATE User SET Password = ? WHERE Email = ?", newPassword, email);
            lblErrorMessage.setText("Password updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            lblErrorMessage.setText("Error updating password.");
        }
    }

    @FXML
    void btnFPGetCodeAction(ActionEvent event) {
        String newPassword = FPFPNewPassword.getText();
        String confirmPassword = PFFPComPassword.getText();

        if (!newPassword.equals(confirmPassword)) {
            lblErrorMessage.setText("Passwords do not match.");
            return;
        }

        if (!newPassword.matches(PASSWORD_PATTERN)) {
            lblErrorMessage.setText("Password: 8+ chars, digit, lowercase, uppercase, special char");
            return;
        }

        String email = lblYourEmail.getText();
        if (email != null && !email.isEmpty()) {
            // Generate OTP code
            generatedOTP = generateOTP();

            // Send OTP code via Gmail
            sendOTPEmail(email, generatedOTP);

            lblErrorMessage.setText("OTP code sent to your email.");
        } else {
            lblErrorMessage.setText("Email address is not available.");
        }
    }

    @FXML
    void btnFPNewPasswordViewAction(MouseEvent event) {

    }

    @FXML
    void btnGoToSignInPageAction(ActionEvent event) throws IOException {
        forgotPasswordPage.getChildren().clear();
        AnchorPane newPage = FXMLLoader.load(getClass().getResource("/view/SignInPage.fxml"));
        forgotPasswordPage.getChildren().add(newPage);
    }

    @FXML
    void txtFPComPasswordAction(ActionEvent event) {

    }

    @FXML
    void txtFPNewPasswordAction(ActionEvent event) {

    }

    @FXML
    void initialize() {
        txtUserName.setOnAction(event -> btnCheckEmail.fire());
        FPFPNewPassword.setOnAction(event -> PFFPComPassword.requestFocus());
        PFFPComPassword.setOnAction(event -> btnFPGetCode.fire());
        txtFPEnterCode.setOnAction(event -> btnFPForgotPassword.fire());
    }

    private String generateOTP() {
        int randomPin = (int) (Math.random() * 90000) + 100000;
        return String.valueOf(randomPin);
    }

    private void sendOTPEmail(String recipientEmail, String otpCode) {
        String from = "hasiduudara@gmail.com";
        //final String username = "hasiduudara@gmail.com";
        //final String password = "your-email-password";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("hasiduudara@gmail.com", "bkfqvkbzogkkdaku");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("hasiduudara@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Your OTP Code");
            message.setText("Your OTP code is: " + otpCode);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
