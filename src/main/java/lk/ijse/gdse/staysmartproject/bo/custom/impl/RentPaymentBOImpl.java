package lk.ijse.gdse.staysmartproject.bo.custom.impl;

import lk.ijse.gdse.staysmartproject.bo.custom.RentPaymentBO;
import lk.ijse.gdse.staysmartproject.dao.DAOFactory;
import lk.ijse.gdse.staysmartproject.dao.custom.HouseDAO;
import lk.ijse.gdse.staysmartproject.dao.custom.RentPaymentDAO;
import lk.ijse.gdse.staysmartproject.dao.custom.TenantDAO;
import lk.ijse.gdse.staysmartproject.dto.RentPaymentDTO;
import lk.ijse.gdse.staysmartproject.dto.TenantDTO;
import lk.ijse.gdse.staysmartproject.dto.tm.HouseTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class RentPaymentBOImpl implements RentPaymentBO {
    RentPaymentDAO rentPaymentDAO = (RentPaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.RENT_PAYMENT);
    HouseDAO houseDAO = (HouseDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.HOUSE);
    TenantDAO tenantDAO = (TenantDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.TENANT);

    public String getNextRentPaymentId() throws SQLException {
        return rentPaymentDAO.getNextRentPaymentId();
    }

    public ArrayList<RentPaymentDTO> getAllRentPayments() throws SQLException {
        return rentPaymentDAO.getAllRentPayments();
    }

    public Optional<String> getHouseIdByDate(java.sql.Date date) throws SQLException, ClassNotFoundException {
        return rentPaymentDAO.getHouseIdByDate(date);
    }

    public Optional<RentPaymentDTO> getRentPaymentByDate(java.sql.Date date) throws SQLException, ClassNotFoundException {
        return rentPaymentDAO.getRentPaymentByDate(date);
    }

    public HouseTM getHouseById(String houseId) throws SQLException, ClassNotFoundException {
        return houseDAO.findHouseById(houseId);
    }

    public Optional<TenantDTO> getTenantById(String tenantId) throws SQLException {
        return tenantDAO.findTenantById(tenantId);
    }

    public boolean saveRentPayment(RentPaymentDTO rentPayment) throws SQLException, ClassNotFoundException {
        return rentPaymentDAO.saveRentPayment(rentPayment);
    }
}
