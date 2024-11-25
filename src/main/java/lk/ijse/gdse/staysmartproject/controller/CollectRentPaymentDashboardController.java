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
import lk.ijse.gdse.staysmartproject.db.DBConnection;
import lk.ijse.gdse.staysmartproject.dto.RentPaymentDTO;
import lk.ijse.gdse.staysmartproject.dto.TenantDTO;
import lk.ijse.gdse.staysmartproject.dto.tm.HouseTM;
import lk.ijse.gdse.staysmartproject.dto.tm.RentPaymentTM;
import lk.ijse.gdse.staysmartproject.dto.tm.TenantTM;
import lk.ijse.gdse.staysmartproject.model.HouseModel;
import lk.ijse.gdse.staysmartproject.model.RentPaymentModel;
import lk.ijse.gdse.staysmartproject.model.SharedDataModel;
import lk.ijse.gdse.staysmartproject.model.TenantModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CollectRentPaymentDashboardController implements Initializable {

    private final String url = "jdbc:mysql://localhost:3306/staysmart";
    private final String user = "root";
    private final String password = "hasindu12345";

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
    private Button btnReset;

    @FXML
    private TableView<RentPaymentTM> tableCollectRentPayment;

    @FXML
    private TextField txtHouseId;

    RentPaymentModel rentPaymentModel = new RentPaymentModel();
    TenantModel tenantModel = new TenantModel();
    HouseModel houseModel = new HouseModel();

    @FXML
    void btnPrintBillAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("todayDate", LocalDate.now().toString());
            parameters.put("time", LocalTime.now().toString());

            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/LastRentPaymentBill.jrxml"));

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load report..!");
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Data empty..!");
            e.printStackTrace();
        }
    }

    @FXML
    void btnSearchAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String houseId = txtHouseId.getText(); // Get the entered House_ID from the text field

        if (!houseId.isEmpty()) {
            ArrayList<TenantDTO> tenantsByHouseId = tenantModel.getTenantsByHouseId(houseId);
            final HouseTM houseById = houseModel.findHouseById(houseId);

            if (!tenantsByHouseId.isEmpty()) {
                TenantDTO tenant = tenantsByHouseId.getFirst();

                lblTenantId.setText(tenant.getTenant_ID());
                lblName.setText(tenant.getName());
                dpDate.setValue(LocalDate.now());
                lblRentPrice.setText(String.valueOf(houseById.getRent_Price()));
                dpDate.setValue(LocalDate.now());
            } else {
                lblName.setText("");
                lblTenantId.setText("");
                lblRentPrice.setText("");
            }
        } else {
            lblName.setText("");
        }
    }

    @FXML
    void btnSubmitPaymentAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (lblName.getText().isEmpty()) {
            showErrorMessage("Please select valid house id");
        } else {
            final boolean isSaved = rentPaymentModel.saveRentPayment(new RentPaymentDTO(
                    lblRentPaymentId.getText(),
                    Double.valueOf(lblRentPrice.getText()),
                    Date.valueOf(dpDate.getValue()),
                    lblTenantId.getText(),
                    txtHouseId.getText()
            ));
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "saved successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "save failed").show();
            }
        }
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void dpDateAction(ActionEvent event) {

    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        RentPaymentTM selectedItem = tableCollectRentPayment.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }

        lblRentPaymentId.setText(selectedItem.getRent_Payment_ID());
        txtHouseId.setText(selectedItem.getHouse_ID());
        lblTenantId.setText(selectedItem.getTenant_ID());
        lblName.setText(selectedItem.getName());
        lblRentPrice.setText(String.valueOf(selectedItem.getRent_Amount()));
        dpDate.setValue(new java.sql.Date(selectedItem.getPayment_Date().getTime()).toLocalDate());

        btnSubmitPayment.setDisable(true);
        btnSearch.setDisable(true);
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
            getRentPaymentAndIncomeSum();
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
        btnSearch.setDisable(false);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<RentPaymentDTO> rentPaymentDTOS = rentPaymentModel.getAllRentPayments();
        ObservableList<RentPaymentTM> rentPaymentTMS = FXCollections.observableArrayList();

        for (RentPaymentDTO rentPaymentDTO : rentPaymentDTOS) {
            String houseId = rentPaymentDTO.getHouse_ID();
            String tenantName = "";

            ArrayList<TenantDTO> tenantsByHouseId = tenantModel.getTenantsByHouseId(houseId);
            if (!tenantsByHouseId.isEmpty()) {
                tenantName = tenantsByHouseId.get(0).getName();
            }

            RentPaymentTM rentPaymentTM = new RentPaymentTM(
                    rentPaymentDTO.getRent_Payment_ID(),
                    rentPaymentDTO.getRent_Amount(),
                    rentPaymentDTO.getPayment_Date(),
                    rentPaymentDTO.getTenant_ID(),
                    rentPaymentDTO.getHouse_ID(),
                    tenantName
            );
            rentPaymentTMS.add(rentPaymentTM);
        }
        tableCollectRentPayment.setItems(rentPaymentTMS);
    }

    public void getRentPaymentAndIncomeSum() {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            // Query to get the sum of Rent_Amount from Rent_Payment table
            String rentAmountQuery = "SELECT SUM(Rent_Amount) AS totalRentAmount FROM Rent_Payment";
            ResultSet rentAmountResult = statement.executeQuery(rentAmountQuery);
            double totalRentAmount = 0;
            if (rentAmountResult.next()) {
                totalRentAmount = rentAmountResult.getDouble("totalRentAmount");
            }

            // Update the Income in Finances table
            String updateIncomeQuery = "UPDATE Finances SET Income = " + totalRentAmount;
            statement.executeUpdate(updateIncomeQuery);

            // Set the totalRentAmount in SharedDataModel
            SharedDataModel.getInstance().setTotalRentAmount(totalRentAmount);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnResetAction(ActionEvent event) {
        txtHouseId.clear();
        lblTenantId.setText("");
        lblName.setText("");
        lblRentPrice.setText("");
        dpDate.setValue(null);
        lblRentPaymentId.setText("");

        btnSubmitPayment.setDisable(false);
        btnSearch.setDisable(false);

        try {
            String nextRentPaymentId = rentPaymentModel.getNextRentPaymentId();
            lblRentPaymentId.setText(nextRentPaymentId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}