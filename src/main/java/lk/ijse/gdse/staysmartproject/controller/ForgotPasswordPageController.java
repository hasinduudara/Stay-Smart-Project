package lk.ijse.gdse.staysmartproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.gdse.staysmartproject.util.CrudUtil;

import java.io.IOException;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ForgotPasswordPageController {

    private String generatedOTP;

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
    private TextField txtYourEmail;

    @FXML
    private Label lblErrorMessage;

    @FXML
    private Button btnGoToSignInPage;

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
    void btnFPForgotPasswordAction(ActionEvent event) {
//        if (isOTPValid()) {
//            String newPassword = FPFPNewPassword.getText();
//            String email = txtYourEmail.getText();
//
//            try (Connection connection = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/staysmart",
//                    "root",
//                    "hasindu12345")) {
////                String updateQuery = "UPDATE user SET password = ? WHERE email = ?";
////                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
////                preparedStatement.setString(1, newPassword);
////                preparedStatement.setString(2, email);
////                preparedStatement.executeUpdate();
//                boolean updateQuery = CrudUtil.execute("UPDATE user SET Password = ? WHERE Email = ?",newPassword,email);
//                System.out.println(updateQuery);
//                if (updateQuery) {
//                    lblErrorMessage.setText("Password updated successfully.");
//                    new Alert(Alert.AlertType.INFORMATION,"Password updated successfully.",ButtonType.OK).show();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//                lblErrorMessage.setText("Failed to update password. Please try again.");
//            }
//        } else {
//            lblErrorMessage.setText("Invalid OTP.");
//
//        }
        if (isOTPValid()) {
            String newPassword = FPFPNewPassword.getText();
            String email = txtYourEmail.getText();

            try (Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/staysmart",
                    "root",
                    "hasindu12345")) {
                String updateQuery = "UPDATE user SET Password = ? WHERE Email = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(1, newPassword);
                preparedStatement.setString(2, email);
                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    lblErrorMessage.setText("Password updated successfully.");
                    new Alert(Alert.AlertType.INFORMATION, "Password updated successfully.", ButtonType.OK).show();
                } else {
                    lblErrorMessage.setText("Failed to update password. No matching user found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                lblErrorMessage.setText("Failed to update password. Please try again.");
            }
        } else {
            lblErrorMessage.setText("Invalid OTP.");
        }
    }

    @FXML
    void btnFPGetCodeAction(ActionEvent event) {
        if (arePasswordsSame()) {
            generatedOTP = generateOTP();
            try {
                sendEmail(txtYourEmail.getText(),
                        "Your OTP Code",
                        "Your OTP code is: " + generatedOTP);
                lblErrorMessage.setText("OTP sent to your email.");
            } catch (MessagingException e) {
                e.printStackTrace();
                lblErrorMessage.setText("Failed to send OTP. Please try again.");
            }
        } else {
            lblErrorMessage.setText("Passwords do not match.");
        }
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

    private boolean arePasswordsSame() {
        return FPFPNewPassword.getText().equals(PFFPComPassword.getText());
    }

    private void sendEmail(String to, String subject, String body) throws MessagingException {
        String from = "hasiduudara@gmail.com"; // replace with your email
        //String host = "smtp.gmail.com"; // Gmail SMTP server

//        Properties properties = System.getProperties();
//        properties.setProperty("mail.smtp.host", host);
//        properties.setProperty("mail.smtp.port", "587");
//        properties.setProperty("mail.smtp.auth", "true");
//        properties.setProperty("mail.smtp.starttls.enable", "true");

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("hasiduudara@gmail.com",
                        "bkfqvkbzogkkdaku"); // replace with your email and app-specific password
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }

    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    private boolean isOTPValid() {
        return txtFPEnterCode.getText().equals(generatedOTP);
    }

    @FXML
    void btnGoToSignInPageAction(ActionEvent event) throws IOException {
        forgotPasswordPage.getChildren().clear();
        AnchorPane newPage = FXMLLoader.load(getClass().getResource("/view/SignInPage.fxml"));
        forgotPasswordPage.getChildren().add(newPage);
    }

}
