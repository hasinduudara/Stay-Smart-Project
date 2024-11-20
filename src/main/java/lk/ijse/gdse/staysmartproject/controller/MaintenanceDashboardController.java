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
import lk.ijse.gdse.staysmartproject.dto.MaintenanceDTO;
import lk.ijse.gdse.staysmartproject.dto.tm.MaintenanceTM;
import lk.ijse.gdse.staysmartproject.model.ExpensesDataModel;
import lk.ijse.gdse.staysmartproject.model.MaintenanceModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.Date;

public class MaintenanceDashboardController implements Initializable {

    private final String url = "jdbc:mysql://localhost:3306/staysmart";
    private final String user = "root";
    private final String password = "hasindu12345";

    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnGetReport;

    @FXML
    private TableColumn<MaintenanceTM, Double> colAmount;

    @FXML
    private TableColumn<MaintenanceTM, Date> colDate;

    @FXML
    private TableColumn<MaintenanceTM, String> colDescription;

    @FXML
    private TableColumn<MaintenanceTM, String> colHouseId;

    @FXML
    private TableColumn<MaintenanceTM, String> colMaintenanceId;

    @FXML
    private DatePicker dpDate;

    @FXML
    private Label lblMaintenanceId;

    @FXML
    private AnchorPane maintenanceDashboard;

    @FXML
    private TableView<MaintenanceTM> tableMaintenance;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtHouseId;

    @FXML
    private Button btnReset;

    MaintenanceModel maintenanceModel = new MaintenanceModel();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colMaintenanceId.setCellValueFactory(new PropertyValueFactory<>("MT_ID"));
        colHouseId.setCellValueFactory(new PropertyValueFactory<>("House_ID"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));

        try {
            refreshPage();
            getMaintenanceCostSum();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSubmitAction(ActionEvent event) {
        String MT_ID = lblMaintenanceId.getText();
        String House_ID = txtHouseId.getText();
        double Amount = Double.parseDouble(txtAmount.getText());
        String Description = txtDescription.getText();
        String dateString = dpDate.getValue().toString();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            MaintenanceDTO maintenanceDTO = new MaintenanceDTO(MT_ID, House_ID, Amount, Description, date);
            boolean isSaved = maintenanceModel.saveMaintenance(maintenanceDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Maintenance add successfully").show();
                refreshPage();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add Maintenance").show();
            }
        } catch (ParseException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void dpDateAction(ActionEvent event) {

    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        String getNextMaintenanceId = maintenanceModel.getNextMaintenanceId();
        lblMaintenanceId.setText(getNextMaintenanceId);

        txtHouseId.setText("");
        txtAmount.setText("");
        txtDescription.setText("");
        dpDate.setValue(null);

        btnSubmit.setDisable(false);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<MaintenanceDTO> allMaintenances = maintenanceModel.getAllMaintenances();
        ObservableList<MaintenanceTM> maintenanceTMS = FXCollections.observableArrayList();
        for (MaintenanceDTO maintenanceDTO : allMaintenances) {
            MaintenanceTM maintenanceTM = new MaintenanceTM(
                    maintenanceDTO.getMT_ID(),
                    maintenanceDTO.getHouse_ID(),
                    maintenanceDTO.getAmount(),
                    maintenanceDTO.getDescription(),
                    maintenanceDTO.getDate()
            );
            maintenanceTMS.add(maintenanceTM);
        }
        tableMaintenance.setItems(maintenanceTMS);
    }

    @FXML
    void btnGetReportAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("todayDate", LocalDate.now().toString());
            parameters.put("time", LocalTime.now().toString());

            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/MainteinsReport.jrxml"));

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

    public void getMaintenanceCostSum() {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            // Query to get the sum of Amount from Maintains table
            String totalExpensesQuery = "SELECT SUM(Amount) AS totalExpenses FROM Maintains";
            ResultSet totalExpensesResult = statement.executeQuery(totalExpensesQuery);
            double totalExpenses = 0;
            if (totalExpensesResult.next()) {
                totalExpenses = totalExpensesResult.getDouble("totalExpenses");
            }

            // Update the Expenses in Finances table
            String updateExpensesQuery = "UPDATE Finances SET Expenses = " + totalExpenses;
            statement.executeUpdate(updateExpensesQuery);

            // Set the totalExpenses in ExpensesDataModel
            ExpensesDataModel.getInstance().setTotalExpenses(totalExpenses);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearFields() {
        txtHouseId.clear();
        txtAmount.clear();
        txtDescription.clear();
        dpDate.setValue(null);
    }

    @FXML
    void onMouseClick(MouseEvent event) {
        MaintenanceTM selectedItem = tableMaintenance.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblMaintenanceId.setText(selectedItem.getMT_ID());
            txtHouseId.setText(selectedItem.getHouse_ID());
            txtAmount.setText(String.valueOf(selectedItem.getAmount()));
            txtDescription.setText(selectedItem.getDescription());
            dpDate.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(selectedItem.getDate())));
            btnSubmit.setDisable(true);
        }
    }

    @FXML
    void btnResetAction(ActionEvent event) {
        txtHouseId.clear();
        txtAmount.clear();
        txtDescription.clear();
        dpDate.setValue(null);
        lblMaintenanceId.setText("");

        btnSubmit.setDisable(false);

        try {
            String nextRentPaymentId = maintenanceModel.getNextMaintenanceId();
            lblMaintenanceId.setText(nextRentPaymentId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}