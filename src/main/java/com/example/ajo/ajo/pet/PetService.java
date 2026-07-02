package com.example.ajo.ajo.pet;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.jspecify.annotations.Nullable;
import org.springframework.web.multipart.MultipartFile;

public interface PetService {

    Pet add(Pet pet);
    List<Pet> getPets();
    Pet update(Pet pet);
    void delete(Integer id);
    Optional<Pet> getById(Integer id) throws Exception;
    Accounts add(Accounts accounts);
    Transactions add(Transactions transactions);
    LoginResponse login(LoginRequest request);
    String addInitial(String accountno, Integer initial);
    @Nullable
    ApiResponse addMoney(String accountno, Integer amount, MultipartFile receipt) throws IOException;
    List<TransactionHistoryDTO> getTransactionHistory(String accountno);
	List<WithdrawHistoryDTO> getWithdrawHistory(String accountno);
    
    

}
