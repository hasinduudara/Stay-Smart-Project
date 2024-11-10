package lk.ijse.gdse.staysmartproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainDashboardPageController implements Initializable {

    @FXML
    private Button btnAddTenant;

    @FXML
    private Button btnCollectRentPayment;

    @FXML
    private Button btnManageFinances;

    @FXML
    private Button btnSentNotification;

    @FXML
    private AnchorPane loardAnchorPain;

    @FXML
    private AnchorPane mainDashboardPage;

    @FXML
    private Button btnMaintenance;

    @FXML
    private Button btnManageHouse;

    @FXML
    void btnAddTenantAction(ActionEvent event) {
        navigateTo("/view/AddTenantDashboard.fxml");
    }

    @FXML
    void btnCollectRentPaymentAction(ActionEvent event) {
        navigateTo("/view/CollectRentPaymentDashboard.fxml");
    }

    @FXML
    void btnSentNotificationAction(ActionEvent event) {
        navigateTo("/view/SendNotificationDashboard.fxml");
    }

    @FXML
    void btnMaintenanceAction(ActionEvent event) {
        navigateTo("/view/MaintenanceDashboard.fxml");
    }

    @FXML
    void btnManageFinancesAction(ActionEvent event) {
        navigateTo("/view/ManageFinancesDashboard.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/AddTenantDashboard.fxml");
    }

    @FXML
    void btnManageHouseAction(ActionEvent event) {
        navigateTo("/view/ManageHouseDashboard.fxml");
    }


    public void navigateTo(String fxmlPath) {
        try {
            loardAnchorPain.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            loardAnchorPain.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }
}
