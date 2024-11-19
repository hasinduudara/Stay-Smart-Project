package lk.ijse.gdse.staysmartproject.controller;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.gdse.staysmartproject.db.DBConnection;
import lk.ijse.gdse.staysmartproject.model.FinancesModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ManageFinancesDashboardController implements Initializable {


    @FXML
    private Button btnGenerateReport;

    @FXML
    private Label lblFinanceId;

    @FXML
    private Label lblAllMaintenanceCosts;

    @FXML
    private Label lblAlltenantPayments;

    @FXML
    private Label lblViewProfit;

    @FXML
    private AnchorPane manageFinancesDashboard;

    FinancesModel financesModel = new FinancesModel();


    private final String url = "jdbc:mysql://localhost:3306/StaySmart";
    private final String user = "root";
    private final String password = "hasindu12345";

    @FXML
    void btnGenerateReportAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Map<String, Object> parameters = new HashMap<>();

            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/Profit.jrxml"));

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Double> allMaintenanceCosts = financesModel.getFullCost();
            lblAllMaintenanceCosts.setText(String.valueOf(allMaintenanceCosts.get(0)));
            lblAlltenantPayments.setText(String.valueOf(allMaintenanceCosts.get(1)));
            lblViewProfit.setText(String.valueOf(allMaintenanceCosts.get(1) - allMaintenanceCosts.get(0)));
    }

}