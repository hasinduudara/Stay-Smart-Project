package lk.ijse.gdse.staysmartproject.model;

import lk.ijse.gdse.staysmartproject.util.CrudUtil;

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
}
