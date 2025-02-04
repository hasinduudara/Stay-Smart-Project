package lk.ijse.gdse.staysmartproject.dao.custom;

import lk.ijse.gdse.staysmartproject.dao.CrudDAO;
import lk.ijse.gdse.staysmartproject.dto.FinancesDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FinanceDAO extends CrudDAO {
    public ArrayList<Double> getFullCost();
    public String getNextFinanceId() throws SQLException;
    public boolean saveFinance(FinancesDTO finance) throws SQLException, ClassNotFoundException;

    }
