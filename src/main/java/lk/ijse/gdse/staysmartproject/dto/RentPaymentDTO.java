package lk.ijse.gdse.staysmartproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RentPaymentDTO {
    private String Rent_Payment_ID;
    private Double Rent_Amount;
    private Date Payment_Date;
    private String Tenant_ID;
    private String House_ID;
}


