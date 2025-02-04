package lk.ijse.gdse.staysmartproject.bo.custom;

import lk.ijse.gdse.staysmartproject.bo.SuperBO;
import lk.ijse.gdse.staysmartproject.db.DBConnection;
import lk.ijse.gdse.staysmartproject.dto.tm.HouseTM;
import lk.ijse.gdse.staysmartproject.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;

public interface HouseBO extends SuperBO {
    public ArrayList<HouseTM> getAllHouses() throws SQLException, ClassNotFoundException;

    public HouseTM findHouseById(String houseId) throws SQLException, ClassNotFoundException;

    public boolean updateRentPrice(String houseId, double newRentPrice) throws SQLException, ClassNotFoundException;

    public boolean updateHouseStatus(String houseId, String status) throws SQLException, ClassNotFoundException;

    public boolean resetHouseStatus(String houseId) throws SQLException, ClassNotFoundException;

    public double findRentPriceById(String houseId) throws SQLException, ClassNotFoundException;
}
