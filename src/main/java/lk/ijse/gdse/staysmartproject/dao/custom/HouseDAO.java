package lk.ijse.gdse.staysmartproject.dao.custom;

import lk.ijse.gdse.staysmartproject.dao.SuperDAO;
import lk.ijse.gdse.staysmartproject.dto.tm.HouseTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface HouseDAO extends SuperDAO {
    public ArrayList<HouseTM> getAllHouses() throws SQLException, ClassNotFoundException;
    public HouseTM findHouseById(String houseId) throws SQLException, ClassNotFoundException;
    public boolean updateRentPrice(String houseId, double newRentPrice) throws SQLException, ClassNotFoundException;
    public boolean updateHouseStatus(String houseId, String status) throws SQLException, ClassNotFoundException;
    public boolean resetHouseStatus(String houseId) throws SQLException, ClassNotFoundException;
    public double findRentPriceById(String houseId) throws SQLException, ClassNotFoundException;
}
