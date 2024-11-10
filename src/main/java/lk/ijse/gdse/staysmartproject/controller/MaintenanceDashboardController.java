package lk.ijse.gdse.staysmartproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class MaintenanceDashboardController {

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnSubmit;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colHouseId;

    @FXML
    private TableColumn<?, ?> colMaintenanceId;

    @FXML
    private DatePicker dpDate;

    @FXML
    private Label lblMaintenanceId;

    @FXML
    private AnchorPane maintenanceDashboard;

    @FXML
    private TableView<?> tableMaintenance;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtHouseId;

    @FXML
    void btnSearchAction(ActionEvent event) {

    }

    @FXML
    void btnSubmitAction(ActionEvent event) {

    }

    @FXML
    void dpDateAction(ActionEvent event) {

    }

}
