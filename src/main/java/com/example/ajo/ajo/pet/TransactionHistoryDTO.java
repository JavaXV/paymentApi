package com.example.ajo.ajo.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistoryDTO {

    private String pageno;
    private Integer amount;
    private String status;

}
