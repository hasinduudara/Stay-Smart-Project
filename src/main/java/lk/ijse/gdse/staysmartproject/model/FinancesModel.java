package lk.ijse.gdse.staysmartproject.model;

import lk.ijse.gdse.staysmartproject.dto.FinancesDTO;
import lk.ijse.gdse.staysmartproject.dto.TenantDTO;
import lk.ijse.gdse.staysmartproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FinancesModel {

    public String getNextFinancesId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Finance_ID from Finances order by Finance_ID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("F%03d", newIdIndex); // Return the new customer ID in format Cnnn
        }
        return "F001"; // Return the default customer ID if no data is found
    }

    public static boolean saveFinances(FinancesDTO finances) throws SQLException, ClassNotFoundException {
        boolean isSaved = CrudUtil.execute(
                "INSERT INTO Finances VALUES(?,?,?,?)",
                finances.getFinance_ID(),
                finances.getIncome(),
                finances.getExpense(),
                finances.getProfit()
        );
        return isSaved;
    }

    public ArrayList<Double> getFullCost() {
        ArrayList<Double> fullCost = new ArrayList<>();
        try {
            ResultSet rst = CrudUtil.execute("SELECT SUM(Amount) FROM maintains");
            if (rst.next()) {
                fullCost.add(rst.getDouble(1));
            }
            ResultSet rs1 = CrudUtil.execute("SELECT SUM(Rent_Amount) FROM rent_payment");
            if(rs1.next()){
                fullCost.add(rs1.getDouble(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return fullCost;
    }
}
