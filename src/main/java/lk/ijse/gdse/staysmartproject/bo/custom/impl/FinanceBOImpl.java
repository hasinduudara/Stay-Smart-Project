package lk.ijse.gdse.staysmartproject.bo.custom.impl;

import lk.ijse.gdse.staysmartproject.bo.custom.FinanceBO;
import lk.ijse.gdse.staysmartproject.dao.DAOFactory;
import lk.ijse.gdse.staysmartproject.dao.custom.FinanceDAO;
import lk.ijse.gdse.staysmartproject.dto.FinancesDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class FinanceBOImpl implements FinanceBO {
    FinanceDAO financeDAO = (FinanceDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.FINANCE);

    public ArrayList<Double> getFullCost() {
        return financeDAO.getFullCost();
    }

    public String getNextFinanceId() throws SQLException {
        return financeDAO.getNextFinanceId();
    }

    public boolean saveFinance(FinancesDTO finance) throws SQLException, ClassNotFoundException {
        return financeDAO.saveFinance(finance);
    }
}
