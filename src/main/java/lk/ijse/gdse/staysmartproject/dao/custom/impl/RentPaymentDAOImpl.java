package lk.ijse.gdse.staysmartproject.dao.custom.impl;

import lk.ijse.gdse.staysmartproject.dao.custom.RentPaymentDAO;
import lk.ijse.gdse.staysmartproject.db.DBConnection;
import lk.ijse.gdse.staysmartproject.dto.HouseDTO;
import lk.ijse.gdse.staysmartproject.dto.RentPaymentDTO;
import lk.ijse.gdse.staysmartproject.dto.TenantDTO;
import lk.ijse.gdse.staysmartproject.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class RentPaymentDAOImpl implements RentPaymentDAO {

    public String getNextRentPaymentId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Rent_Payment_ID FROM Rent_Payment ORDER BY Rent_Payment_ID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            int newIdIndex = Integer.parseInt(lastId.substring(2)) + 1;
            return String.format("RP%03d", newIdIndex);
        }
        return "RP001";
    }

    public ArrayList<RentPaymentDTO> getAllRentPayments() throws SQLException {
        ArrayList<RentPaymentDTO> allRentPayments = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM Rent_Payment");
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
    }

    public Optional<String> getHouseIdByDate(java.sql.Date date) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT House_ID FROM Rent_Payment WHERE Payment_Date = ?", date);
        return rst.next() ? Optional.of(rst.getString(1)) : Optional.empty();
    }

    public Optional<RentPaymentDTO> getRentPaymentByDate(java.sql.Date date) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Rent_Payment WHERE Payment_Date = ?", date);
        if (rst.next()) {
            return Optional.of(new RentPaymentDTO(
                    rst.getString("Rent_Payment_ID"),
                    rst.getDouble("Rent_Amount"),
                    rst.getDate("Payment_Date"),
                    rst.getString("Tenant_ID"),
                    rst.getString("House_ID")
            ));
        }
        return Optional.empty();
    }

    public Optional<HouseDTO> getHouseById(String houseId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM House WHERE House_ID = ?", houseId);
        if (rst.next()) {
            return Optional.of(new HouseDTO(
                    rst.getString("House_ID"),
                    rst.getDouble("Rent_Price"),
                    rst.getString("Name")
            ));
        }
        return Optional.empty();
    }

    public Optional<TenantDTO> getTenantById(String tenantId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Tenant WHERE Tenant_ID = ?", tenantId);
        if (rst.next()) {
            return Optional.of(new TenantDTO(
                    rst.getString("Tenant_ID"),
                    rst.getString("House_ID"),
                    rst.getString("Name"),
                    rst.getString("Email"),
                    rst.getDate("End_Of_Date")
            ));
        }
        return Optional.empty();
    }

    public boolean saveRentPayment(RentPaymentDTO rentPayment) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            boolean isSaved = CrudUtil.execute(
                    "INSERT INTO Rent_Payment (Rent_Payment_ID, Rent_Amount, Payment_Date, Tenant_ID, House_ID) VALUES (?,?,?,?,?)",
                    rentPayment.getRent_Payment_ID(),
                    rentPayment.getRent_Amount(),
                    new java.sql.Date(rentPayment.getPayment_Date().getTime()),
                    rentPayment.getTenant_ID(),
                    rentPayment.getHouse_ID()
            );

            if (isSaved) {
                boolean isUpdated = CrudUtil.execute(
                        "UPDATE House SET Status = ? WHERE House_ID = ?", "Rented Out",
                        rentPayment.getHouse_ID());
                if (isUpdated) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            }
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
