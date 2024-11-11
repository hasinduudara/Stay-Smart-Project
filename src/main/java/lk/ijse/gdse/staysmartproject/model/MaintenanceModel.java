package lk.ijse.gdse.staysmartproject.model;

import lk.ijse.gdse.staysmartproject.dto.MaintenanceDTO;
import lk.ijse.gdse.staysmartproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MaintenanceModel {

    public String getNextMaintenanceId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT MT_ID FROM maintains ORDER BY MT_ID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            int newIdIndex = Integer.parseInt(lastId.substring(2)) + 1;
            return String.format("MT%03d", newIdIndex); // Return the new tenant ID in format Tnnn
        }
        return "MT001";
    }

    public static boolean saveMaintenance(MaintenanceDTO maintenance) throws SQLException, ClassNotFoundException {
        boolean isSaved = CrudUtil.execute(
                "INSERT INTO maintains VALUES(?,?,?,?,?)",
                maintenance.getMT_ID(),
                maintenance.getHouse_ID(),
                maintenance.getAmount(),
                maintenance.getDescription(),
                maintenance.getDate());
        return isSaved;
    }

    public ArrayList<MaintenanceDTO> getAllMaintenances() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM maintains");
        ArrayList<MaintenanceDTO> allMaintenances = new ArrayList<>();
        while (rst.next()) {
            allMaintenances.add(new MaintenanceDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getString(4),
                    rst.getDate(5)
            ));
        }
        return allMaintenances;
    }
}
