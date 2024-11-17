package lk.ijse.gdse.staysmartproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.staysmartproject.dto.FinancesDTO;
import lk.ijse.gdse.staysmartproject.model.ExpensesDataModel;
import lk.ijse.gdse.staysmartproject.model.FinancesModel;
import lk.ijse.gdse.staysmartproject.model.SharedDataModel;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageFinancesDashboardController implements Initializable {

    @FXML
    private Button btnCalculateProfit;

    @FXML
    private Button btnGenerateReport;

    @FXML
    private Label lblAllMaintenanceCosts;

    @FXML
    private Label lblAlltenantPayments;

    @FXML
    private Label lblViewProfit;

    @FXML
    private Button btnSave;

    @FXML
    private AnchorPane manageFinancesDashboard;

    FinancesModel financesModel = new FinancesModel();


    private final String url = "jdbc:mysql://localhost:3306/StaySmart";
    private final String user = "root";
    private final String password = "hasindu12345";

    @FXML
    void btnCalculateProfitAction(ActionEvent event) {
        double totalRentAmount = SharedDataModel.getInstance().getTotalRentAmount();
        double totalExpenses = ExpensesDataModel.getInstance().getTotalExpenses();
        double profit = totalRentAmount - totalExpenses;
        lblViewProfit.setText(String.valueOf(profit));
    }

    @FXML
    void btnGenerateReportAction(ActionEvent event) {
        // Generate report logic here
    }

    @FXML
    void btnSaveAction(ActionEvent event) {
        try {
            String financeId = new FinancesModel().getNextFinancesId();
            double income = SharedDataModel.getInstance().getTotalRentAmount();
            double expenses = ExpensesDataModel.getInstance().getTotalExpenses();
            double profit = income - expenses;

            FinancesDTO financesDTO = new FinancesDTO(financeId, income, expenses, profit);
            boolean isSaved = FinancesModel.saveFinances(financesDTO);

            if (isSaved) {
                System.out.println("Finance record saved successfully.");
            } else {
                System.out.println("Failed to save finance record.");
            }
        } catch (SQLException | ClassNotFoundException e) {
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