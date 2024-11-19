package lk.ijse.gdse.staysmartproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
    private Label lblTime;

    @FXML
    private Label lblDate;

    @FXML
    private ImageView profileImage;

    @FXML
    private Button btnProfile;

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

    @FXML
    void btnManageHouseAction(ActionEvent event) {
        navigateTo("/view/ManageHouseDashboard.fxml");
    }

    @FXML
    void btnProfileAction(ActionEvent event) {
        navigateTo("/view/ProfileEditePage.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/AddTenantDashboard.fxml");
        startClock();
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

    private void startClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            lblTime.setText(LocalTime.now().format(timeFormatter));

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            lblDate.setText(LocalDate.now().format(dateFormatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }
}
