package lk.ijse.gdse.staysmartproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class HouseDTO {
    private String House_ID;
    private double Rent_Price;
    private String Status;
}