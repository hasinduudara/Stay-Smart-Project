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
import lk.ijse.gdse.staysmartproject.dao.custom.TenantDAO;
import lk.ijse.gdse.staysmartproject.dao.custom.impl.TenantDAOImpl;
import lk.ijse.gdse.staysmartproject.db.DBConnection;
import lk.ijse.gdse.staysmartproject.dto.TenantDTO;
import lk.ijse.gdse.staysmartproject.dto.tm.TenantTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class AddTenantDashboardController implements Initializable {

    @FXML
    private AnchorPane addTenantDashboard;

    @FXML
    private Button btnAddTenantContractReport;

    @FXML
    private Button btnAddTenantDelete;

    @FXML
    private Button btnAddTenantReset;

    @FXML
    private Button btnAddTenantSave;

    @FXML
    private Button btnAddTenantUpdate;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colHouseId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTenantId;

    @FXML
    private TableColumn<?, ?> colEndOfDate;

    @FXML
    private Label lblTenantId;

    @FXML
    private TableView<TenantTM> tableAddTenant;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtHouseId;

    @FXML
    private TextField txtName;

    @FXML
    private DatePicker dpEndOfDate;

    @FXML
    private Button btnContract;

//    TenantModel tenantModel = new TenantModel();
//    HouseModel houseModel = new HouseModel();
    TenantDAO tenantDAO = new TenantDAOImpl();

    @FXML
    void btnAddTenantContractReportAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Map<String, Object> parameters = new HashMap<>();

            parameters.put("todayDate", LocalDate.now().toString());
            parameters.put("time", LocalTime.now().toString());

            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/TenantReport1.jrxml"));

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
    void btnAddTenantDeleteAction(ActionEvent event) {
        String tenantId = lblTenantId.getText();

        Alert alart = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this tenant?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alart.showAndWait();

        if (buttonType.get() == ButtonType.YES) {
            try {
                boolean isDeleted = tenantDAO.deleteTenant(tenantId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Tenant deleted successfully").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete tenant").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void btnAddTenantResetAction(ActionEvent event) {
        try {
            refreshPage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void dpEndOfDate(ActionEvent event) {

    }

    @FXML
    void btnAddTenantUpdateAction(ActionEvent event) {
        String Tenant_ID = lblTenantId.getText();
        String House_ID = txtHouseId.getText();
        String Name = txtName.getText();
        String Email = txtEmail.getText();
        //Date End_Of_Date = new Date();
        java.sql.Date End_Of_Date = java.sql.Date.valueOf(dpEndOfDate.getValue());

        String namePattern = "^[A-Za-z\\s]{3,}$";
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        boolean validName = Name.matches(namePattern);
        boolean validEmail = Email.matches(emailPattern);

        // Reset input field styles
        txtName.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1px 0; -fx-background-color: white");
        txtEmail.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1px 0; -fx-background-color: white");

        if (!validName) {
            txtName.setStyle("-fx-border-color: red");
            return;
        }

        if (!validEmail) {
            txtEmail.setStyle("-fx-border-color: red");
            return;
        }

        // Update tenant if all fields are valid
        if (validName && validEmail) {
            TenantDTO tenantDTO = new TenantDTO(Tenant_ID, House_ID, Name, Email, new Date());

            try {
                boolean isUpdated = tenantDAO.updateTenant(tenantDTO);
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Tenant updated successfully").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update tenant").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTenantId.setCellValueFactory(new PropertyValueFactory<>("Tenant_ID"));
        colHouseId.setCellValueFactory(new PropertyValueFactory<>("House_ID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colEndOfDate.setCellValueFactory(new PropertyValueFactory<>("End_Of_Date"));

        txtHouseId.setOnAction(event -> txtName.requestFocus());
        txtName.setOnAction(event -> txtEmail.requestFocus());
        txtEmail.setOnAction(event -> dpEndOfDate.requestFocus());

        try {
            refreshPage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddTenantSaveAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String Tenant_ID = lblTenantId.getText();
        String House_ID = txtHouseId.getText();
        String Name = txtName.getText();
        String Email = txtEmail.getText();
        Date End_Of_Date = java.sql.Date.valueOf(dpEndOfDate.getValue());

        String namePattern = "^[A-Za-z\\s]{3,}$";
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        boolean validName = Name.matches(namePattern);
        boolean validEmail = Email.matches(emailPattern);

        // Reset input field styles
        txtName.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1px 0; -fx-background-color: white");
        txtEmail.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1px 0; -fx-background-color: white");

        if (!validName) {
            txtName.setStyle("-fx-border-color: red");
            return;
        }

        if (!validEmail) {
            txtEmail.setStyle("-fx-border-color: red");
            return;
        }


        // Save tenant if all fields are valid
        if (validName && validEmail) {
            TenantDTO tenantDTO = new TenantDTO(Tenant_ID, House_ID, Name, Email, End_Of_Date);

            boolean isSaved = tenantDAO.saveTenant(tenantDTO); // Call the method on the instance

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Tenant saved successfully").show();
                refreshPage(); // Refresh the page to update the table
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save tenant").show();
            }
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        String nextTenantId = tenantDAO.getNextTenantId();
        lblTenantId.setText(nextTenantId);

        txtName.setText("");
        txtHouseId.setText("");
        txtEmail.setText("");
        dpEndOfDate.setValue(null);

        btnAddTenantSave.setDisable(false);
        btnAddTenantDelete.setDisable(true);
        btnAddTenantUpdate.setDisable(true);
        btnAddTenantReset.setDisable(true);
        btnContract.setDisable(false);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<TenantDTO> allTenants = tenantDAO.getAllTenants();
        ObservableList<TenantTM> tenantTMS = FXCollections.observableArrayList();

        for (TenantDTO tenantDTO : allTenants) {
            TenantTM tenantTM = new TenantTM(
                    tenantDTO.getTenant_ID(),
                    tenantDTO.getHouse_ID(),
                    tenantDTO.getName(),
                    tenantDTO.getEmail(),
                    tenantDTO.getEnd_Of_Date()
            );
            tenantTMS.add(tenantTM);
        }
        tableAddTenant.setItems(tenantTMS);
    }

    @FXML
    void onClickTable(MouseEvent event) {
        TenantTM selectedItem = tableAddTenant.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }

        lblTenantId.setText(selectedItem.getTenant_ID());
        txtHouseId.setText(selectedItem.getHouse_ID());
        txtName.setText(selectedItem.getName());
        txtEmail.setText(selectedItem.getEmail());
        dpEndOfDate.setValue(new java.sql.Date(selectedItem.getEnd_Of_Date().getTime()).toLocalDate());

        btnAddTenantSave.setDisable(true);
        btnAddTenantDelete.setDisable(false);
        btnAddTenantUpdate.setDisable(false);
        btnAddTenantReset.setDisable(false);
        btnContract.setDisable(true);
    }

    @FXML
    void btnContractAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Map<String, Object> parameters = new HashMap<>();

            parameters.put("todayDate", LocalDate.now().toString());
            parameters.put("time", LocalTime.now().toString());

            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/LastTenantReport.jrxml"));

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
}
