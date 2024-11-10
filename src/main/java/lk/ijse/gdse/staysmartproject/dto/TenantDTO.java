package lk.ijse.gdse.staysmartproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TenantDTO {
    private String Tenant_ID;
    private String House_ID;
    private String Name;
    private String Email;
    private Date End_Of_Date;
}
