package lk.ijse.gdse.staysmartproject.dao.custom;

import lk.ijse.gdse.staysmartproject.dao.CrudDAO;
import lk.ijse.gdse.staysmartproject.dto.UserDTO;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO extends CrudDAO {
    public boolean saveUser(UserDTO user) throws SQLException;
    public String getNextUserId() throws SQLException;
    public List<UserDTO> getAllUsers() throws SQLException;
}
