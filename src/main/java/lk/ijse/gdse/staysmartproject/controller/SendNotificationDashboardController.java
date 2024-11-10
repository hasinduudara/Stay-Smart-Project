package lk.ijse.gdse.staysmartproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.staysmartproject.dto.TenantDTO;
import lk.ijse.gdse.staysmartproject.model.TenantModel;

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
        // Implement the send notification logic here
    }

}