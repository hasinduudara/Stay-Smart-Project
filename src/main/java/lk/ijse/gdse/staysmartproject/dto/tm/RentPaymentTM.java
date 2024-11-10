package lk.ijse.gdse.staysmartproject.dto.tm;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RentPaymentTM {
    private String Rent_Payment_ID;
    private Double Rent_Amount;
    private Date Payment_Date;
    private String Tenant_ID;
    private String House_ID;
    private String name;
}
