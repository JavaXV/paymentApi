package com.example.ajo.ajo.pet;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

    @Query("SELECT COALESCE(SUM(t.depositeamount), 0) " +
           "FROM Transactions t " +
           "WHERE t.pageno = :pageno")

    String sumDepositAmountByPageNo(@Param("pageno") String pageno);

    @Query("SELECT COUNT(t) FROM Transactions t " +
       "WHERE t.pageno = :pageno " +
       "AND t.transactiontype = 'Withdraw'")

    String countWithdrawByPageNo(@Param("pageno") String pageno);

    Transactions findByAccountnoAndPagenoAndTransactiontype(String accountno, String pageno, String transactiontype);

       @Query("""
       SELECT COALESCE(SUM(t.depositeamount), 0)
       FROM Transactions t
       WHERE t.accountno = :accountno
       AND t.pageno = :pageno
       """)
       Integer getTotalDepositByAccountnoAndPageno(@Param("accountno") String accountno,@Param("pageno") String pageno);
       
       @Query("""
       SELECT COALESCE(SUM(t.depositeamount),0)
       FROM Transactions t
       WHERE t.accountno = :accountno
       AND t.pageno = :pageno
       """)
       Double getTotalDeposited( @Param("accountno") String accountno,@Param("pageno") String pageno);

       Integer findByAccountnoAndPageno(String accountno, String pageno);

      @Query("""
         SELECT COALESCE(SUM(t.withdrawalamount), 0)
         FROM Transactions t
         WHERE t.accountno = :accountno
         AND t.pageno = :pageno
         """)
      Integer getTotalWithdrawn(@Param("accountno") String accountno, @Param("pageno") String pageno);


}