package lk.ijse.gdse.staysmartproject.bo.custom;

import lk.ijse.gdse.staysmartproject.bo.SuperBO;
import lk.ijse.gdse.staysmartproject.dto.TenantDTO;
import java.sql.SQLException;
import java.util.ArrayList;

public interface TenantBO extends SuperBO {
    public String getNextTenantId() throws SQLException;

    public boolean saveTenant(TenantDTO tenant) throws SQLException, ClassNotFoundException;

    public boolean updateTenant(TenantDTO tenant) throws SQLException, ClassNotFoundException;

    public boolean deleteTenant(String id) throws SQLException, ClassNotFoundException;

    public ArrayList<TenantDTO> getAllTenants() throws SQLException, ClassNotFoundException;

    public TenantDTO getTenantById(String tenantId) throws SQLException, ClassNotFoundException;

    public ArrayList<TenantDTO> getTenantsByHouseId(String houseId) throws SQLException, ClassNotFoundException;
}
