package com.example.ajo.ajo.pet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AccountsRepository extends JpaRepository<Accounts, Long> {

    Accounts findTopByCodesOrderByIdDesc(String codes);

    Accounts findByAccountno(String accountno);

     Accounts findTopByAccountnoOrderByIdDesc(String accountno);

    Accounts findByPageno(String pageno);

    boolean existsByPageno(String pageno);
    

     List<Accounts> findByAccountnoOrderByPagenoAsc(String accountno);

    @Query("SELECT MAX(a.pageno) FROM Accounts a WHERE a.accountno = :accountno")
    String findLastPageNoByAccountNo(@Param("accountno") String accountno);
}