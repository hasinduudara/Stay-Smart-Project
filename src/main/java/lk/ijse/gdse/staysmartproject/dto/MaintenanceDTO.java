package lk.ijse.gdse.staysmartproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MaintenanceDTO {
    private String MT_ID;
    private String House_ID;
    private Double Amount;
    private String Description;
    private Date Date;

}