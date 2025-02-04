package lk.ijse.gdse.staysmartproject.dao.custom;

import lk.ijse.gdse.staysmartproject.dao.CrudDAO;
import lk.ijse.gdse.staysmartproject.dto.MaintenanceDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MaintenanceDAO extends CrudDAO {
    public String getNextMaintenanceId() throws SQLException;
    public boolean saveMaintenance(MaintenanceDTO maintenance) throws SQLException, ClassNotFoundException;
    public ArrayList<MaintenanceDTO> getAllMaintenances() throws SQLException;

}
