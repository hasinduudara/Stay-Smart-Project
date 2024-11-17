package lk.ijse.gdse.staysmartproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.staysmartproject.dto.TenantDTO;
import lk.ijse.gdse.staysmartproject.model.TenantModel;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendNotificationDashboardController {

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnSend;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblName;

    @FXML
    private AnchorPane sendNotificationDashboard;

    @FXML
    private TextArea txtBody;

    @FXML
    private TextField txtSubject;

    @FXML
    private TextField txtTenantId;

    private TenantModel tenantModel = new TenantModel();


    @FXML
    void btnSearchAction(ActionEvent event) {
        String tenantId = txtTenantId.getText();
        if (tenantId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter a Tenant ID").show();
            return;
        }

        try {
            TenantDTO tenantDTO = tenantModel.getTenantById(tenantId);
            if (tenantDTO != null) {
                lblName.setText(tenantDTO.getName());
                lblEmail.setText(tenantDTO.getEmail());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "No tenant found with the given ID").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load tenant details").show();
        }
    }

    @FXML
    void btnSendAction(ActionEvent event) {
        final String FROM = "hasiduudara@gmail.com";

        String subject = txtSubject.getText();
        String body = txtBody.getText();

        if (subject.isEmpty() || body.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter a subject and body").show();
            return;
        }
        System.out.println(FROM+""+ lblEmail.getText()+""+ subject+""+ body);
        sendEmailWithGmail(FROM, lblEmail.getText(), subject, body);
    }

    private void sendEmailWithGmail(String from, String to, String subject, String body) {
        String PASSWORD = "bkfqvkbzogkkdaku";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);

            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully").show();
        } catch (MessagingException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to send email").show();
        }
    }

}