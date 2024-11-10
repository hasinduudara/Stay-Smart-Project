package lk.ijse.gdse.staysmartproject.dto.tm;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TenantTM {
    private String Tenant_ID;
    private String House_ID;
    private String Name;
    private String Email;
    private Date End_Of_Date;
}
