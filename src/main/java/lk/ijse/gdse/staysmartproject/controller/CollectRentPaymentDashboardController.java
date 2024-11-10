package lk.ijse.gdse.staysmartproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.staysmartproject.dto.HouseDTO;
import lk.ijse.gdse.staysmartproject.dto.RentPaymentDTO;
import lk.ijse.gdse.staysmartproject.dto.TenantDTO;
import lk.ijse.gdse.staysmartproject.dto.tm.HouseTM;
import lk.ijse.gdse.staysmartproject.dto.tm.RentPaymentTM;
import lk.ijse.gdse.staysmartproject.dto.tm.TenantTM;
import lk.ijse.gdse.staysmartproject.model.RentPaymentModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CollectRentPaymentDashboardController implements Initializable {

    @FXML
    private Button btnPrintBill;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnSubmitPayment;

    @FXML
    private TableColumn<RentPaymentTM, LocalDate> colDate;

    @FXML
    private TableColumn<HouseTM, String> colHouseId;

    @FXML
    private TableColumn<HouseTM, String> colName;

    @FXML
    private TableColumn<RentPaymentTM, String> colPaymentId;

    @FXML
    private TableColumn<HouseTM, Double> colRentPrice;

    @FXML
    private TableColumn<TenantTM, String> colTenantId;

    @FXML
    private AnchorPane collectRentPaymentDashboard;

    @FXML
    private DatePicker dpDate;

    @FXML
    private Label lblName;

    @FXML
    private Label lblRentPaymentId;

    @FXML
    private Label lblRentPrice;

    @FXML
    private Label lblTenantId;

    @FXML
    private TableView<RentPaymentTM> tableCollectRentPayment;

    @FXML
    private TextField txtHouseId;

    RentPaymentModel rentPaymentModel = new RentPaymentModel();

    @FXML
    void btnPrintBillAction(ActionEvent event) {

    }

    @FXML
    void btnSearchAction(ActionEvent event) {
        LocalDate selectedDate = dpDate.getValue();
        if (selectedDate == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a date").show();
            return;
        }

        try {
            RentPaymentDTO rentPaymentDTO = rentPaymentModel.getRentPaymentByDate(java.sql.Date.valueOf(selectedDate));
            if (rentPaymentDTO != null) {
                String houseId = rentPaymentDTO.getHouse_ID();
                String tenantId = rentPaymentDTO.getTenant_ID();

                HouseDTO houseDTO = rentPaymentModel.getHouseById(houseId);
                TenantDTO tenantDTO = rentPaymentModel.getTenantById(tenantId);

                if (houseDTO != null && tenantDTO != null) {
                    txtHouseId.setText(houseDTO.getHouse_ID());
                    lblTenantId.setText(tenantDTO.getTenant_ID());
                    lblName.setText(tenantDTO.getName());
                    lblRentPrice.setText(String.valueOf(houseDTO.getRent_Price()));
                    //dpDate.setValue(rentPaymentDTO.getPayment_Date().toLocalDate());
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "No house or tenant found for the selected date").show();
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "No rent payment found for the selected date").show();
            }
            dpDate.setValue(null); // Clear the date picker after search
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to search house and tenant details").show();
        }
    }

    @FXML
    void btnSubmitPaymentAction(ActionEvent event) {

    }

    @FXML
    void dpDateAction(ActionEvent event) {

    }

    @FXML
    void onMouseClicked(MouseEvent event) {

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("Rent_Payment_ID"));
        colHouseId.setCellValueFactory(new PropertyValueFactory<>("House_ID"));
        colTenantId.setCellValueFactory(new PropertyValueFactory<>("Tenant_ID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colRentPrice.setCellValueFactory(new PropertyValueFactory<>("Rent_Amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Payment_Date"));

        try {
            refreshPage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        String nextRentPaymentId = rentPaymentModel.getNextRentPaymentId();
        lblRentPaymentId.setText(nextRentPaymentId);

        txtHouseId.setText("");
        lblTenantId.setText("");
        lblName.setText("");
        lblRentPrice.setText("");
        dpDate.setValue(null);

        btnSubmitPayment.setDisable(false);
        btnPrintBill.setDisable(false);
        btnSearch.setDisable(false);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<RentPaymentDTO> rentPaymentDTOS = rentPaymentModel.getAllRentPayments();
        ObservableList<RentPaymentTM> rentPaymentTMS = FXCollections.observableArrayList();
        for (RentPaymentDTO rentPaymentDTO : rentPaymentDTOS) {
            RentPaymentTM rentPaymentTM = new RentPaymentTM(
                    rentPaymentDTO.getRent_Payment_ID(),
                    rentPaymentDTO.getRent_Amount(),
                    rentPaymentDTO.getPayment_Date(),
                    rentPaymentDTO.getTenant_ID(),
                    rentPaymentDTO.getHouse_ID()
            );
            rentPaymentTMS.add(rentPaymentTM);
        }
        tableCollectRentPayment.setItems(rentPaymentTMS);
    }

}