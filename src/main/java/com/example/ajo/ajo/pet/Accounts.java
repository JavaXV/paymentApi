package com.example.ajo.ajo.pet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Accounts")
@NoArgsConstructor
@AllArgsConstructor
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Integer id;

    @Column(name = "branch")
    private String branch;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "accountno")
    private String accountno;

    @Column(name = "pageno")
    private String pageno;

    @Column(name = "initialdeposite")
    private Integer initialdeposite;

    @Column(name = "initialdeposite1")
    private Integer initialdeposite1;

    @Column(name = "codes")
    private String codes;

    @Column(name = "date")
    private String date;
   

    // Manual Constructor
    public Accounts(
            String branch,
            String fullname,
            String accountno,
            Integer initialdeposite,
            Integer initialdeposite1,
            String pageno,
            String codes,
            String date
    ) {
        this.branch = branch;
        this.pageno = pageno;
        this.fullname = fullname;
        this.initialdeposite = initialdeposite;
        this.initialdeposite1 = initialdeposite1;
        this.accountno = accountno;
        this.codes = codes;
        this.date = date;
    }

     public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public Integer getInitialdeposite() {
       return initialdeposite;
    }
    public void setInitialdeposite(Integer initial, Integer initialdeposite) {
        this.initialdeposite = initialdeposite;
    }


}
