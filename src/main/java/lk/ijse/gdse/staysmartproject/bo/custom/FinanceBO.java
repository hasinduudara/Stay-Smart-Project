package lk.ijse.gdse.staysmartproject.bo.custom;

import lk.ijse.gdse.staysmartproject.bo.SuperBO;
import lk.ijse.gdse.staysmartproject.dto.FinancesDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FinanceBO extends SuperBO {
    public ArrayList<Double> getFullCost();

    public String getNextFinanceId() throws SQLException;

    public boolean saveFinance(FinancesDTO finance) throws SQLException, ClassNotFoundException;
}
