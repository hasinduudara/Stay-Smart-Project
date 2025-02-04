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
import lk.ijse.gdse.staysmartproject.dao.custom.HouseDAO;
import lk.ijse.gdse.staysmartproject.dao.custom.impl.HouseDAOImpl;
import lk.ijse.gdse.staysmartproject.dto.tm.HouseTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageHouseDashboardController implements Initializable {

    @FXML
    private Button btnHouseStatusReset;

    @FXML
    private Button btnRentPriceChange;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<HouseTM, String> colHouseId;

    @FXML
    private TableColumn<HouseTM, Double> colRentPrice;

    @FXML
    private TableColumn<HouseTM, String> colStatus;

    @FXML
    private Label lblHouseStatus;

    @FXML
    private Label lblRentPrice;

    @FXML
    private AnchorPane manageHouseDashboard;

    @FXML
    private TableView<HouseTM> tableManageHouse;

    @FXML
    private TextField txtHouseId;

    @FXML
    private TextField txtRentPrice;

    @FXML
    private Button btnClearValues;

//    private HouseModel houseModel = new HouseModel();
    HouseDAO houseDAO = new HouseDAOImpl();

    @FXML
    void btnHouseStatusResetAction(ActionEvent event) {
        String houseId = txtHouseId.getText();
        try {
            boolean isReset = houseDAO.resetHouseStatus(houseId);
            if (isReset) {
                new Alert(Alert.AlertType.INFORMATION, "House status reset to Available").show();
                loadHouseData(); // Refresh the table to reflect changes
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to reset house status").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnRentPriceChangeAction(ActionEvent event) {
        String houseId = txtHouseId.getText();
        double newRentPrice;
        try {
            newRentPrice = Double.parseDouble(txtRentPrice.getText());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid rent price").show();
            return;
        }

        try {
            boolean isUpdated = houseDAO.updateRentPrice(houseId, newRentPrice);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Rent price updated successfully").show();
                loadHouseData(); // Refresh the table to reflect changes
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update rent price").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSearchAction(ActionEvent event) {
        String houseId = txtHouseId.getText();
        try {
            HouseTM house = houseDAO.findHouseById(houseId);
            if (house != null) {
                lblHouseStatus.setText(house.getStatus());
                txtRentPrice.setText(String.valueOf(house.getRent_Price()));
            } else {
                new Alert(Alert.AlertType.WARNING, "House not found").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colHouseId.setCellValueFactory(new PropertyValueFactory<>("House_ID"));
        colRentPrice.setCellValueFactory(new PropertyValueFactory<>("Rent_Price"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));

        try {
            loadHouseData();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        txtHouseId.setOnAction(event -> btnSearch.fire());
    }

    private void loadHouseData() throws SQLException, ClassNotFoundException {
        ArrayList<HouseTM> allHouses = houseDAO.getAllHouses();
        ObservableList<HouseTM> houseTMS = FXCollections.observableArrayList(allHouses);
        tableManageHouse.setItems(houseTMS);
    }

    @FXML
    void onClickTable(MouseEvent event) {
        HouseTM selectedItem = tableManageHouse.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtHouseId.setText(selectedItem.getHouse_ID());
            lblHouseStatus.setText(selectedItem.getStatus());
            txtRentPrice.setText(String.valueOf(selectedItem.getRent_Price()));
        }
    }

    @FXML
    void btnClearValuesAction(ActionEvent event) {
        txtHouseId.clear();
        lblHouseStatus.setText("");
        txtRentPrice.clear();
    }

}
