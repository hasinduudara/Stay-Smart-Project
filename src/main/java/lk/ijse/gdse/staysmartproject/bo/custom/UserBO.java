package lk.ijse.gdse.staysmartproject.bo.custom;

import lk.ijse.gdse.staysmartproject.bo.SuperBO;
import lk.ijse.gdse.staysmartproject.dto.UserDTO;
import java.sql.SQLException;
import java.util.List;

public interface UserBO extends SuperBO {
    public boolean saveUser(UserDTO user) throws SQLException;

    public String getNextUserId() throws SQLException;

    public List<UserDTO> getAllUsers() throws SQLException;
}
