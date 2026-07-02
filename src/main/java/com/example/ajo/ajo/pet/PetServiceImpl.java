package com.example.ajo.ajo.pet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;
    private final AccountsRepository accountsRepository;
    private final TransactionsRepository transactionsRepository;
    private final InitTableRepository initTableRepository;

    @Override
    public ApiResponse addMoney(String accountno, Integer amount, MultipartFile receipt) throws IOException {

        // Accounts account = accountsRepository.findByAccountno(accountno);
        Accounts account = accountsRepository.findTopByAccountnoOrderByIdDesc(accountno);
        String approvedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if (account == null) {
            return new ApiResponse(
                    "ACCOUNTNOTFOUND",
                    "Account Not Found");
        }

        // Sum all previous deposits for this account
        // String pageno = account.getPageno();

        String pageno = accountsRepository.findLastPageNoByAccountNo(accountno);

        String totalDeposits = transactionsRepository.sumDepositAmountByPageNo(pageno);
        String withdrawCount = transactionsRepository.countWithdrawByPageNo(pageno);

        if (totalDeposits == null) {
            totalDeposits = "0";
        }

        // Get limit from initable
        InitTable initable = initTableRepository.findByPageno(pageno);

        if (initable == null) {

            if (Long.parseLong(withdrawCount) > 0) {
                return new ApiResponse(
                        "WITHDRAWN",
                        "This Page Number Has Already Been Withdrawn");
            } else {
                // Get initial deposit
                Integer initialAmount1 = account.getInitialdeposite();
                // Multiply by 31
                Integer totalvalue1 = initialAmount1 * 31;

                InitTable initabless = new InitTable();
                initabless.setPageno(pageno);
                initabless.setInitial(initialAmount1);
                initabless.setTotalvalue(totalvalue1);
                initabless.setAccountno(accountno);
                initabless.setAmount(initialAmount1);

                initTableRepository.save(initabless);

            }

            // return new ApiResponse(
            // "PAGEINITABLE",
            // "Configuration PageNo Not Found"
            // );
        }

        if (Long.parseLong(withdrawCount) > 0) {
            return new ApiResponse(
                    "WITHDRAWN",
                    "This Page Number Has Already Been Withdrawn");
        }
        // Get limit from initable
        InitTable initablee = initTableRepository.findByPageno(pageno);
        Double totalDepositsValue = Double.parseDouble(totalDeposits);
        Integer totalValue = initablee.getTotalvalue();
        Integer totalmoney = (int) Math.round(totalDepositsValue + amount);
        System.out.println(totalValue);

        Integer totalDepositsLong = totalmoney;
        // Long initialAmount = account.getInitialdeposite();

        System.out.println(totalDepositsLong);

        // Long inter = (initialAmount > 5000)
        // ? initialAmount / 2
        // : initialAmount;

        Integer remainingAmount = totalValue - (int) Math.round(totalDepositsValue);
        Integer remain = remainingAmount;
        System.out.println(remain);

        // Check limit if totaldeposite = totalvalue new endorsement
        if (Long.parseLong(totalDeposits) == totalValue) {
            return new ApiResponse(
                    "ENDORSEMENT",
                    "This passbook page is completed. New Initial Deposit is required for another Page");
        }

        // Check limit
        if (totalDepositsLong > totalValue) {

            remain = (int) Math.max(remain, 0L);

            return new ApiResponse(
                    "TRANSACTIONLIMIT",
                    "The money you are trying to add will exceed the page limit. You are supposed to pay exactly: ₦"
                            + String.format("%,d", remain));
        }

        Transactions transaction = new Transactions();
        transaction.setAccountno(accountno);
        transaction.setDepositeamount(amount);
        transaction.setPageno(pageno);
        transaction.setTransactiontype("Deposite");
        transaction.setApproveddate(approvedDate);

        // ============================
        // Upload Receipt Image
        // ============================

        if (receipt != null && !receipt.isEmpty()) {

            String fileName = System.currentTimeMillis()
                    + "_" + receipt.getOriginalFilename();

            Path uploadPath = Paths.get("uploads/receipts");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(
                    receipt.getInputStream(),
                    uploadPath.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);

            // Save filename in Transactions table
            transaction.setReceipt(fileName);

        }

        // ============================
        // Save Transaction
        // ============================

        Transactions savedTransaction = transactionsRepository.save(transaction);

        if (savedTransaction != null) {
            return new ApiResponse(
                    "SUCCESS",
                    "Transaction Successful");
        }

        return new ApiResponse(
                "ERROR",
                "Transaction Failed");
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        // Accounts account = accountsRepository.findByCodes(request.getCodes());
        Accounts account = accountsRepository.findTopByCodesOrderByIdDesc(request.getCodes());

        if (account == null) {

            return new LoginResponse(
                    "ERROR",
                    "Invalid Access Code",
                    null);
        }
        return new LoginResponse(
                "SUCCESS",
                "Login Successful",
                account);
    }

    @Override
    public Pet add(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public Accounts add(Accounts accounts) {
        return accountsRepository.save(accounts);
    }

    @Override
    public void delete(Integer id) {
        petRepository.deleteById(id);
    }

    @Override
    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    @Override
    public Pet update(Pet pet) {

        Pet updatedPet = petRepository.save(pet);
        System.out.println("Updated Pet ID: " + updatedPet.getId());
        System.out.println("Updated Pet Name: " + updatedPet.getName());
        final int petId = updatedPet.getId();
        System.out.println(petId);
        if (petId == 2) {

        }
        return updatedPet;
    }

    @Override
    public Optional<Pet> getById(Integer id) throws Exception {

        return Optional.of(petRepository.findById(id)
                .orElseThrow(() -> new Exception("pet not found !")));

    }

    @Override
    public Transactions add(Transactions transactions) {
        return transactionsRepository.save(transactions);
    }

    @Override
    public List<TransactionHistoryDTO> getTransactionHistory(String accountno) {

        List<Accounts> accounts = accountsRepository.findByAccountnoOrderByPagenoAsc(accountno);

        List<TransactionHistoryDTO> history = new ArrayList<>();

        for (Accounts account : accounts) {

            Transactions transaction = transactionsRepository.findByAccountnoAndPagenoAndTransactiontype(
                    accountno,
                    account.getPageno(),
                    "Withdraw");

            String status = (transaction != null) ? "WITHDRAWN" : "ACTIVE";

            Integer totalAmount = transactionsRepository.getTotalDepositByAccountnoAndPageno(
                    accountno,
                    account.getPageno());

            history.add(new TransactionHistoryDTO(
                    account.getPageno(),
                    totalAmount,
                    status));
        }

        return history;
    }

    @Override
    public List<WithdrawHistoryDTO> getWithdrawHistory(String accountno) {

        List<Accounts> accounts =
                accountsRepository.findByAccountnoOrderByPagenoAsc(accountno);

        List<WithdrawHistoryDTO> list = new ArrayList<>();

        for (Accounts account : accounts) {

            Double totalDeposited = transactionsRepository.getTotalDeposited(
                    accountno,
                    account.getPageno());

            Integer totalWithdrawn = transactionsRepository.getTotalWithdrawn(
                    accountno,
                    account.getPageno());

            String status = totalWithdrawn > 0
                    ? "WITHDRAWN"
                    : "ACTIVE";

            int remainingBalance = (int) (totalDeposited - totalWithdrawn);

            if (remainingBalance < 0) {
                remainingBalance = 0;
            }

            list.add(new WithdrawHistoryDTO(
                    account.getPageno(),
                    totalDeposited,
                    status,
                    remainingBalance));
        }

        return list;
    }

    @Override
    public String addInitial(String accountno, Integer initial) {

        Accounts account = accountsRepository.findTopByAccountnoOrderByIdDesc(accountno);
        String approvedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        int vad = initial * 31;

        int inter = (initial > 5000)
                ? initial / 2
                : initial;

        String pageno;
        do {
            pageno = "PG" + String.format("%06d", ThreadLocalRandom.current().nextInt(0, 1_000_000));
        } while (accountsRepository.existsByPageno(pageno));

        InitTable initable = new InitTable();

        initable.setAccountno(accountno);
        initable.setInitial(initial);
        initable.setPageno(pageno);
        initable.setAmount(initial);
        initable.setTotalvalue(vad);
        initTableRepository.save(initable);

        Accounts iniaccount = new Accounts();
        iniaccount.setAccountno(accountno);
        iniaccount.setInitialdeposite(initial);
        iniaccount.setPageno(pageno);
        iniaccount.setCodes(account.getCodes());
        iniaccount.setDate(approvedDate);
        iniaccount.setInitialdeposite1(inter);
        accountsRepository.save(iniaccount);

        return "Initial Deposit Saved Successfully";
    }

}
