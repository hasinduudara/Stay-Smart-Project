package lk.ijse.gdse.staysmartproject.bo.custom.impl;

import lk.ijse.gdse.staysmartproject.bo.custom.TenantBO;
import lk.ijse.gdse.staysmartproject.dao.DAOFactory;
import lk.ijse.gdse.staysmartproject.dao.custom.TenantDAO;
import lk.ijse.gdse.staysmartproject.dto.TenantDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class TenantBOImpl implements TenantBO {
    TenantDAO tenantDAO = (TenantDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.TENANT);

    public String getNextTenantId() throws SQLException {
        return tenantDAO.getNextTenantId();
    }

    public boolean saveTenant(TenantDTO tenant) throws SQLException, ClassNotFoundException {
        return tenantDAO.saveTenant(tenant);
    }

    public boolean updateTenant(TenantDTO tenant) throws SQLException, ClassNotFoundException {
        return tenantDAO.updateTenant(tenant);
    }

    public boolean deleteTenant(String id) throws SQLException, ClassNotFoundException {
        return tenantDAO.deleteTenant(id);
    }

    public ArrayList<TenantDTO> getAllTenants() throws SQLException, ClassNotFoundException {
        return tenantDAO.getAllTenants();
    }

    public TenantDTO getTenantById(String tenantId) throws SQLException, ClassNotFoundException {
        return tenantDAO.getTenantById(tenantId);
    }

    public ArrayList<TenantDTO> getTenantsByHouseId(String houseId) throws SQLException, ClassNotFoundException {
        return tenantDAO.getTenantsByHouseId(houseId);
    }
}
