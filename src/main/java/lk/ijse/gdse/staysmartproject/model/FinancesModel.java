package lk.ijse.gdse.staysmartproject.model;

import lk.ijse.gdse.staysmartproject.db.DBConnection;
import lk.ijse.gdse.staysmartproject.dto.FinancesDTO;
import lk.ijse.gdse.staysmartproject.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FinancesModel {

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

    public String getNextFinanceId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT Finance_ID FROM Finances ORDER BY Finance_ID DESC LIMIT 1";
        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String string = rs.getString(1);
            String subString = string.substring(1);
            int lastIdIndex = Integer.parseInt(subString);
            int nextIdIndex = lastIdIndex + 1;

            return String.format("F%03d", nextIdIndex);
        }
        return "F001";
    }

    public static boolean saveFinance(FinancesDTO finance) throws SQLException, ClassNotFoundException {
        boolean isSaved = CrudUtil.execute(
                "INSERT INTO Finances (Finance_ID, Income, Expenses, Profit) VALUES (?, ?, ?, ?)",
                finance.getFinance_ID(),
                finance.getIncome(),
                finance.getExpense(),
                finance.getProfit());
        return isSaved;
    }
}
