package lk.ijse.gdse.staysmartproject.bo.custom.impl;

import lk.ijse.gdse.staysmartproject.bo.custom.UserBO;
import lk.ijse.gdse.staysmartproject.dao.DAOFactory;
import lk.ijse.gdse.staysmartproject.dao.custom.UserDAO;
import lk.ijse.gdse.staysmartproject.dto.UserDTO;

import java.sql.SQLException;
import java.util.List;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.USER);

    public boolean saveUser(UserDTO user) throws SQLException {
        return userDAO.saveUser(user);
    }

    public String getNextUserId() throws SQLException {
        return userDAO.getNextUserId();
    }

    public List<UserDTO> getAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }

}
