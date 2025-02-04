package lk.ijse.gdse.staysmartproject.bo.custom.impl;

import lk.ijse.gdse.staysmartproject.bo.custom.HouseBO;
import lk.ijse.gdse.staysmartproject.dao.DAOFactory;
import lk.ijse.gdse.staysmartproject.dao.custom.HouseDAO;
import lk.ijse.gdse.staysmartproject.dto.tm.HouseTM;

import java.sql.*;
import java.util.ArrayList;

public class HouseBOImpl implements HouseBO {
    HouseDAO houseDAO = (HouseDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.HOUSE);

    public ArrayList<HouseTM> getAllHouses() throws SQLException, ClassNotFoundException {
        return houseDAO.getAllHouses();
    }

    public HouseTM findHouseById(String houseId) throws SQLException, ClassNotFoundException {
        return houseDAO.findHouseById(houseId);
    }

    public boolean updateRentPrice(String houseId, double newRentPrice) throws SQLException, ClassNotFoundException {
        return houseDAO.updateRentPrice(houseId, newRentPrice);
    }

    public boolean updateHouseStatus(String houseId, String status) throws SQLException, ClassNotFoundException {
        return houseDAO.updateHouseStatus(houseId, status);
    }

    public boolean resetHouseStatus(String houseId) throws SQLException, ClassNotFoundException {
        return houseDAO.resetHouseStatus(houseId);
    }

    public double findRentPriceById(String houseId) throws SQLException, ClassNotFoundException {
        return houseDAO.findRentPriceById(houseId);
    }
}
