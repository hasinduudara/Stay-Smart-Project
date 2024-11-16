package lk.ijse.gdse.staysmartproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.staysmartproject.model.ExpensesDataModel;
import lk.ijse.gdse.staysmartproject.model.SharedDataModel;

public class ManageFinancesDashboardController {

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
    private AnchorPane manageFinancesDashboard;

    @FXML
    void btnCalculateProfitAction(ActionEvent event) {
        double totalRentAmount = SharedDataModel.getInstance().getTotalRentAmount();
        double totalExpenses = ExpensesDataModel.getInstance().getTotalExpenses();
        double profit = totalRentAmount - totalExpenses;
        lblViewProfit.setText(String.valueOf(profit));
    }

    @FXML
    void btnGenerateReportAction(ActionEvent event) {

    }

    public void initialize() {
        // Get the totalRentAmount from SharedDataModel and set it to lblAlltenantPayments
        double totalRentAmount = SharedDataModel.getInstance().getTotalRentAmount();
        lblAlltenantPayments.setText(String.valueOf(totalRentAmount));

        // Get the totalExpenses from ExpensesDataModel and set it to lblAllMaintenanceCosts
        double totalExpenses = ExpensesDataModel.getInstance().getTotalExpenses();
        lblAllMaintenanceCosts.setText(String.valueOf(totalExpenses));
    }
}