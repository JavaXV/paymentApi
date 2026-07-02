package com.example.ajo.ajo.pet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Transactions")
@NoArgsConstructor
@AllArgsConstructor
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @Column(name = "branch")
    private String branch;

    @Column(name = "fieldofficer")
    private String fieldofficer;

    @Column(name = "accountno")
    private String accountno;

    @Column(name = "pageno")
    private String pageno;

    @Column(name = "approveddate")
    private String approveddate;

    @Column(name = "receipt")
    private String receipt;

    @Column(name = "withdrawalamount")
    private Integer withdrawalamount;

    @Column(name = "transactiontype")
    private String transactiontype;

    @Column(name = "depositeamount")
    private Integer depositeamount;

   

    // Manual Constructor
    public Transactions(
            String branch,
            String fieldofficer,
            String accountno,
            String pageno,
            String approveddate,
            String receipt,
            Integer withdrawalamount,
            String transactiontype,
            Integer depositeamount
    ) {
        this.branch = branch;
        this.fieldofficer = fieldofficer;
        this.accountno = accountno;
        this.pageno = pageno;
        this.approveddate = approveddate;
        this.receipt = receipt;
        this.withdrawalamount = withdrawalamount;
        this.transactiontype = transactiontype;
        this.depositeamount = depositeamount;
    }



}