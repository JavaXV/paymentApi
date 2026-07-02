package com.example.ajo.ajo.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawHistoryDTO {

    private String pageno;
    private Double totalDeposited;
    private String status;
    private int remainingBalance;

}