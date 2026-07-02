package com.example.ajo.ajo.pet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class PetController {
    private final PetService petService;

    @GetMapping("/test")
    public String test() {
        return "Controller Working";
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return petService.login(request);
    }

    // @PostMapping("/addMoney")
    // public ApiResponse addMoney(@RequestBody AddMoneyRequest request) {
    // return petService.addMoney(
    // request.getAccountno(),
    // request.getAmount()
    // );
    // }
    @PostMapping("/addMoney")
    public ResponseEntity<?> addMoney(

            @RequestParam("accountno") String accountno,
            @RequestParam("amount") Integer amount,
            @RequestParam("receipt") MultipartFile receipt

    ) throws IOException {
        return ResponseEntity.ok(petService.addMoney(accountno, amount, receipt));

    }

    @GetMapping("/transaction-history")
    public ResponseEntity<List<TransactionHistoryDTO>> getTransactionHistory(
            @RequestParam("accountno") String accountno) {

        return ResponseEntity.ok(petService.getTransactionHistory(accountno));
    }

    // @GetMapping("/withdraw-history")
    // public ResponseEntity<List<WithdrawHistoryDTO>> getWithdrawHistory(
    //         @RequestParam String accountno) {

    //     List<WithdrawHistoryDTO> history = petService.getWithdrawHistory(accountno);

    //     return ResponseEntity.ok(history);
    // }

    @GetMapping("/withdraw-history")
    public ResponseEntity<List<WithdrawHistoryDTO>> getWithdrawHistory(
            @RequestParam String accountno) {
        return ResponseEntity.ok(petService.getWithdrawHistory(accountno));
    }

    @PostMapping("/newPageDeposit")
    public ResponseEntity<Map<String, Object>> addInitial(@RequestBody InitialRequest request) {

        Map<String, Object> response = new HashMap<>();

        try {

            String message = petService.addInitial(
                    request.getAccountno(),
                    request.getInitial());

            response.put("status", "SUCCESS");
            response.put("message", message);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            response.put("status", "ERROR");
            response.put("message", e.getMessage());

            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Pet>> getPets() {
        return new ResponseEntity<>(petService.getPets(), HttpStatus.OK);

    }

    @PostMapping("/add")
    public ResponseEntity<Pet> add(@RequestBody Pet pet) {
        return new ResponseEntity<>(petService.add(pet), HttpStatus.CREATED);
    }

    @PostMapping("/account/add")
    public ResponseEntity<Accounts> add(@RequestBody Accounts accounts) {
        return new ResponseEntity<>(petService.add(accounts), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> update(@RequestBody Pet pet) {

        Map<String, Object> response = new HashMap<>();

        try {
            Pet updatedPet = petService.update(pet);

            response.put("success", true);
            response.put("message", "Pet updated successfully");
            response.put("data", updatedPet);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            response.put("success", false);
            response.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/pets/{id}")
    public void delete(@PathVariable("id") Integer id) {
        petService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPet(@PathVariable("id") Integer id) throws Exception {
        return petService.getById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
