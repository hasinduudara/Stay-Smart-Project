package lk.ijse.gdse.staysmartproject.model;

import lk.ijse.gdse.staysmartproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MaintenanceModel {

    public String getNextMaintenanceId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT MT_ID FROM Maintenance ORDER BY MT_ID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last tenant ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("MT%03d", newIdIndex); // Return the new tenant ID in format Tnnn
        }
        return "MT001";
    }
}
