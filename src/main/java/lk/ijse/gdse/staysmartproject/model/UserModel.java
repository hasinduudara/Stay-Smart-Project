// userModel.java
package lk.ijse.gdse.staysmartproject.model;

import lk.ijse.gdse.staysmartproject.db.DBConnection;
import lk.ijse.gdse.staysmartproject.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {

    public boolean saveUser(UserDTO user) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO user (User_ID, Name, Email, User_Name, Password) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setString(1, user.getUser_ID());
        pst.setString(2, user.getName());
        pst.setString(3, user.getEmail());
        pst.setString(4, user.getUser_Name());
        pst.setString(5, user.getPassword());

        return pst.executeUpdate() > 0;
    }

    public String getNextUserId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT User_ID FROM user ORDER BY User_ID DESC LIMIT 1";
        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String string = rs.getString(1);
            String subString = string.substring(1);
            int lastIdIndex = Integer.parseInt(subString);
            int nextIdIndex = lastIdIndex + 1;

            return String.format("U%03d", nextIdIndex);
        }
        return "U001";
    }

    public List<UserDTO> getAllUsers() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select * from user";

        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        List<UserDTO> userList = new ArrayList<>();

        while (rs.next()) {
            UserDTO user = new UserDTO(
                    rs.getString("User_ID"),
                    rs.getString("Name"),
                    rs.getString("Email"),
                    rs.getString("User_Name"),
                    rs.getString("Password")
            );
            userList.add(user);
        }

        return userList;
    }



}