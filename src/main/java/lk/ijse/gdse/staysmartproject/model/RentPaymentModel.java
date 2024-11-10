package lk.ijse.gdse.staysmartproject.model;

import lk.ijse.gdse.staysmartproject.dto.HouseDTO;
import lk.ijse.gdse.staysmartproject.dto.RentPaymentDTO;
import lk.ijse.gdse.staysmartproject.dto.TenantDTO;
import lk.ijse.gdse.staysmartproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RentPaymentModel {

    public String getNextRentPaymentId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Rent_Payment_ID FROM Rent_Payment ORDER BY Rent_Payment_ID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last tenant ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("RP%03d", newIdIndex); // Return the new tenant ID in format Tnnn
        }
        return "RP001";
    }

    public ArrayList<RentPaymentDTO> getAllRentPayments() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM Rent_Payment");
            ArrayList<RentPaymentDTO> allRentPayments = new ArrayList<>();
            while (rst.next()) {
                allRentPayments.add(new RentPaymentDTO(
                        rst.getString(1),
                        rst.getDouble(2),
                        rst.getDate(3),
                        rst.getString(4),
                        rst.getString(5)
                ));
            }
            return allRentPayments;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public String getHouseIdByDate(java.sql.Date date) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT House_ID FROM Rent_Payment WHERE Payment_Date = ?", date);
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    public RentPaymentDTO getRentPaymentByDate(java.sql.Date date) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Rent_Payment WHERE Payment_Date = ?", date);
        if (rst.next()) {
            return new RentPaymentDTO(
                    rst.getString("Rent_Payment_ID"),
                    rst.getDouble("Rent_Amount"),
                    rst.getDate("Payment_Date"),
                    rst.getString("Tenant_ID"),
                    rst.getString("House_ID")
            );
        }
        return null;
    }

    public HouseDTO getHouseById(String houseId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM House WHERE House_ID = ?", houseId);
        if (rst.next()) {
            return new HouseDTO(
                    rst.getString("House_ID"),
                    rst.getDouble("Rent_Price"),
                    rst.getString("Name")
            );
        }
        return null;
    }

    public TenantDTO getTenantById(String tenantId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Tenant WHERE Tenant_ID = ?", tenantId);
        if (rst.next()) {
            return new TenantDTO(
                    rst.getString("Tenant_ID"),
                    rst.getString("House_ID"),
                    rst.getString("Name"),
                    rst.getString("Email"),
                    rst.getDate("End_Of_Date")
            );
        }
        return null;
    }
}