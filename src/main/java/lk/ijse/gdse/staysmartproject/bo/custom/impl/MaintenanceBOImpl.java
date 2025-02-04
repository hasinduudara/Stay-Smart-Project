package lk.ijse.gdse.staysmartproject.bo.custom.impl;

import lk.ijse.gdse.staysmartproject.bo.custom.MaintenanceBO;
import lk.ijse.gdse.staysmartproject.dao.DAOFactory;
import lk.ijse.gdse.staysmartproject.dao.custom.MaintenanceDAO;
import lk.ijse.gdse.staysmartproject.dto.MaintenanceDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class MaintenanceBOImpl implements MaintenanceBO {
    MaintenanceDAO maintenanceDAO = (MaintenanceDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.MAINTENANCE);

    public String getNextMaintenanceId() throws SQLException {
        return maintenanceDAO.getNextMaintenanceId();
    }

    public boolean saveMaintenance(MaintenanceDTO maintenance) throws SQLException, ClassNotFoundException {
        return maintenanceDAO.saveMaintenance(maintenance);
    }

    public ArrayList<MaintenanceDTO> getAllMaintenances() throws SQLException {
        return maintenanceDAO.getAllMaintenances();
    }
}
