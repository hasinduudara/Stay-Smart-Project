package lk.ijse.gdse.staysmartproject.bo.custom;

import lk.ijse.gdse.staysmartproject.bo.SuperBO;
import lk.ijse.gdse.staysmartproject.dto.MaintenanceDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MaintenanceBO extends SuperBO {
    public String getNextMaintenanceId() throws SQLException;

    public boolean saveMaintenance(MaintenanceDTO maintenance) throws SQLException, ClassNotFoundException;

    public ArrayList<MaintenanceDTO> getAllMaintenances() throws SQLException;
}
