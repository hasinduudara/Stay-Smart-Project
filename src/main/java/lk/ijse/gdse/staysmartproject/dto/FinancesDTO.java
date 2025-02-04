package lk.ijse.gdse.staysmartproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FinancesDTO {
    private String Finance_ID;
    private Double Income;
    private Double Expense;
    private Double Profit;
}