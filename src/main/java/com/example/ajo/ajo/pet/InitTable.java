package com.example.ajo.ajo.pet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "Initable")
@NoArgsConstructor
@AllArgsConstructor
public class InitTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Integer id;

    @Column(name = "initial")
    private Integer initial;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "accountno")
    private String accountno;

    @Column(name = "pageno")
    private String pageno;

    @Column(name = "totalvalue")
    private Integer totalvalue;

   

    // Manual Constructor
    public InitTable(
            Integer initial,
            Integer amount,
            String accountno,
            String pageno,
            Integer totalvalue
    ) {
        this.initial = initial;
        this.pageno = pageno;
        this.totalvalue = totalvalue;
        this.accountno = accountno;
        this.amount = amount;
    }

    // set setter get setter
     public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }


    public Integer getTotalvalue() {
        return totalvalue;
    }

    public void setTotalvalue(Integer totalvalue) {
        this.totalvalue = totalvalue;
    }


    public Integer getInitial() {
        return initial;
    }

    public void setInitial(Integer initial) {
        this.initial = initial;
    }

}