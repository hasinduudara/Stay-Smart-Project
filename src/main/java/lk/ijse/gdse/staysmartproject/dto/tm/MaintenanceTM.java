package lk.ijse.gdse.staysmartproject.dto.tm;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class MaintenanceTM {
    private String MT_ID;
    private String House_ID;
    private Double Amount;
    private String Description;
    private Date Date;
}
