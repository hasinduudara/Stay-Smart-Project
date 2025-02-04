package lk.ijse.gdse.staysmartproject.dao.custom;

import lk.ijse.gdse.staysmartproject.dao.CrudDAO;
import lk.ijse.gdse.staysmartproject.dto.HouseDTO;
import lk.ijse.gdse.staysmartproject.dto.RentPaymentDTO;
import lk.ijse.gdse.staysmartproject.dto.TenantDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface RentPaymentDAO extends CrudDAO  {
    public String getNextRentPaymentId() throws SQLException;
    public ArrayList<RentPaymentDTO> getAllRentPayments() throws SQLException;
    public Optional<String> getHouseIdByDate(java.sql.Date date) throws SQLException, ClassNotFoundException;
    public Optional<RentPaymentDTO> getRentPaymentByDate(java.sql.Date date) throws SQLException, ClassNotFoundException;
    public Optional<HouseDTO> getHouseById(String houseId) throws SQLException;
    public Optional<TenantDTO> getTenantById(String tenantId) throws SQLException;
    public boolean saveRentPayment(RentPaymentDTO rentPayment) throws SQLException, ClassNotFoundException;
}
