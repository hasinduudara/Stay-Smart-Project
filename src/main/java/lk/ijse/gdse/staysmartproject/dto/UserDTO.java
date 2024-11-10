package lk.ijse.gdse.staysmartproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String User_ID;
    private String Name;
    private String Email;
    private String User_Name;
    private String Password;
}
