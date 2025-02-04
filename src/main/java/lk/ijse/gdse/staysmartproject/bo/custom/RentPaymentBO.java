package lk.ijse.gdse.staysmartproject.bo.custom;

import lk.ijse.gdse.staysmartproject.bo.SuperBO;
import lk.ijse.gdse.staysmartproject.dto.RentPaymentDTO;
import lk.ijse.gdse.staysmartproject.dto.TenantDTO;
import lk.ijse.gdse.staysmartproject.dto.tm.HouseTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface RentPaymentBO extends SuperBO {
    public String getNextRentPaymentId() throws SQLException;

    public ArrayList<RentPaymentDTO> getAllRentPayments() throws SQLException;

    public Optional<String> getHouseIdByDate(java.sql.Date date) throws SQLException, ClassNotFoundException;

    public Optional<RentPaymentDTO> getRentPaymentByDate(java.sql.Date date) throws SQLException, ClassNotFoundException;

    public HouseTM getHouseById(String houseId) throws SQLException, ClassNotFoundException;

    public Optional<TenantDTO> getTenantById(String tenantId) throws SQLException;

    public boolean saveRentPayment(RentPaymentDTO rentPayment) throws SQLException, ClassNotFoundException;
}
