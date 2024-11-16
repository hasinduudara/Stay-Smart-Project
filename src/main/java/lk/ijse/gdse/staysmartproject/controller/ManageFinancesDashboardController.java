package lk.ijse.gdse.staysmartproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.staysmartproject.dto.FinancesDTO;
import lk.ijse.gdse.staysmartproject.model.ExpensesDataModel;
import lk.ijse.gdse.staysmartproject.model.SharedDataModel;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

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
    private TableColumn<FinancesDTO, Double> colAllExpenses;

    @FXML
    private TableColumn<FinancesDTO, Double> colAllIncome;

    @FXML
    private TableColumn<FinancesDTO, Double> colProfit;

    @FXML
    private TableView<FinancesDTO> tableFinance;

    private final String url = "jdbc:mysql://localhost:3306/StaySmart";
    private final String user = "yourUsername";
    private final String password = "yourPassword";

    @FXML
    void btnCalculateProfitAction(ActionEvent event) {
        double totalRentAmount = SharedDataModel.getInstance().getTotalRentAmount();
        double totalExpenses = ExpensesDataModel.getInstance().getTotalExpenses();
        double profit = totalRentAmount - totalExpenses;
        lblViewProfit.setText(String.valueOf(profit));

        // Save the values in the Finances table
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String updateQuery = "UPDATE Finances SET Income = ?, Expenses = ? WHERE Finance_ID = 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setDouble(1, totalRentAmount);
                preparedStatement.setDouble(2, totalExpenses);
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Refresh the table
        refreshTable();
    }

    @FXML
    void btnGenerateReportAction(ActionEvent event) {
        // Generate report logic here
    }

    @FXML
    void onMouseClick(MouseEvent event) {
        // Handle mouse click event here
    }

    public void initialize() {
        // Get the totalRentAmount from SharedDataModel and set it to lblAlltenantPayments
        double totalRentAmount = SharedDataModel.getInstance().getTotalRentAmount();
        lblAlltenantPayments.setText(String.valueOf(totalRentAmount));

        // Get the totalExpenses from ExpensesDataModel and set it to lblAllMaintenanceCosts
        double totalExpenses = ExpensesDataModel.getInstance().getTotalExpenses();
        lblAllMaintenanceCosts.setText(String.valueOf(totalExpenses));

        // Initialize the table columns
        colAllIncome.setCellValueFactory(new PropertyValueFactory<>("income"));
        colAllExpenses.setCellValueFactory(new PropertyValueFactory<>("expenses"));
        colProfit.setCellValueFactory(new PropertyValueFactory<>("profit"));

        // Load the table data
        refreshTable();
    }

    private void refreshTable() {
        ObservableList<FinancesDTO> financesList = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM Finances";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    double income = resultSet.getDouble("Income");
                    double expenses = resultSet.getDouble("Expenses");
                    double profit = income - expenses;
                    financesList.add(new FinancesDTO(String.valueOf(income), expenses, profit));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tableFinance.setItems(financesList);
    }
}