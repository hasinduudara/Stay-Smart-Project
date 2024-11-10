package lk.ijse.gdse.staysmartproject.model;

import lk.ijse.gdse.staysmartproject.db.DBConnection;
import lk.ijse.gdse.staysmartproject.dto.tm.HouseTM;
import lk.ijse.gdse.staysmartproject.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;

public class HouseModel {
    public ArrayList<HouseTM> getAllHouses() throws SQLException, ClassNotFoundException {
        ArrayList<HouseTM> houses = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "SELECT * FROM House";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            String houseId = resultSet.getString("House_ID");
            double rentPrice = resultSet.getDouble("Rent_Price");
            String status = resultSet.getString("Status");
            houses.add(new HouseTM(houseId, rentPrice, status));
        }

        return houses;
    }

    public HouseTM findHouseById(String houseId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "SELECT * FROM House WHERE House_ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, houseId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new HouseTM(
                    resultSet.getString("House_ID"),
                    resultSet.getDouble("Rent_Price"),
                    resultSet.getString("Status")
            );
        }
        return null;
    }

    public boolean updateRentPrice(String houseId, double newRentPrice) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "UPDATE House SET Rent_Price = ? WHERE House_ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDouble(1, newRentPrice);
        preparedStatement.setString(2, houseId);
        return preparedStatement.executeUpdate() > 0;
    }

    public boolean updateHouseStatus(String houseId, String status) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "UPDATE House SET Status = ? WHERE House_ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, status);
        preparedStatement.setString(2, houseId);
        return preparedStatement.executeUpdate() > 0;
    }

    public boolean resetHouseStatus(String houseId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "UPDATE House SET Status = 'Available' WHERE House_ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, houseId);
        return preparedStatement.executeUpdate() > 0;
    }

    public double findRentPriceById(String houseId) throws SQLException, ClassNotFoundException {
        // Implement the logic to find rent price by house ID from the database
        ResultSet rst = CrudUtil.execute("SELECT Rent_Price FROM House WHERE House_ID=?", houseId);
        if (rst.next()) {
            return rst.getDouble(1);
        }
        return 0.0;
    }

}