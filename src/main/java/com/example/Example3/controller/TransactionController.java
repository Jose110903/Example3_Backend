package com.example.Example3.controller;


import com.example.Example3.exception.ResourceNotFoundException;
import com.example.Example3.exception.ValidationException;
import com.example.Example3.model.Account;
import com.example.Example3.model.Transaction;
import com.example.Example3.repository.AccountRepository;
import com.example.Example3.repository.TransactionRepository;
import com.example.Example3.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bank/v1")
public class TransactionController {
    @Autowired
    private AccountRepository accountRepository;
    private final TransactionRepository TransactionRepository;
    public TransactionController (AccountRepository accountRepository, TransactionRepository TransactionRepository){
        this.accountRepository = accountRepository;
        this.TransactionRepository = TransactionRepository;
    }


    //Endpoint:http://localhost:8083/api/bank/v1/transactions
    //Method: GET
    @Transactional(readOnly = true)
    @RequestMapping ("/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        return new ResponseEntity<List<Transaction>>(TransactionRepository.findAll(), HttpStatus.OK);
    }
    //EndPoint: http://localhost:8080/api/library/v1/loans/filterByCodeStudent
    //Method: GET
    @Transactional(readOnly = true)
    @RequestMapping("/transactions/filterByNameCustomer")
    public ResponseEntity<List<Transaction>> getTransactionsbyNameCustomer(@RequestParam(name="nameCustomer") String nameCustomer) {
        return new ResponseEntity<List<Transaction>>(TransactionRepository.findAllByAccountNameCustomer(nameCustomer), HttpStatus.OK);
    }
    //EndPoint: http://localhost:8080/api/library/v1/loans/filterByCodeStudent
    //Method: GET
    @Transactional(readOnly = true)
    @RequestMapping("/transactions/filterByCreateDateRange")
    public ResponseEntity<List<Transaction>> GellAllCreateDate(@RequestParam(name="startDate") LocalDate startDate, @RequestParam(name="endDate") LocalDate endDate) {
        return new ResponseEntity<List<Transaction>>(TransactionRepository.findAllByCreateDateAndCreateDate(startDate,endDate), HttpStatus.OK);
    }









    //EndPoint: http://localhost:8083/api/bank/v1/accounts/1/transactions
    //Method: Post
    @Transactional
    @RequestMapping("/accounts/{id}/transactions")
    public ResponseEntity<Transaction> createLoan(@PathVariable(value = "id") long accountId,
                                                  @RequestBody Transaction transaction) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found on :: " + accountId));
        ValidateTransaction(transaction);
        transaction.setAccount(TransactionRepository.findById(accountId).get().getAccount());
        transaction.setCreateDate(LocalDate.now());
        return new ResponseEntity<Transaction>(TransactionRepository.save(transaction), HttpStatus.CREATED);
    }

    private void ValidateTransaction(Transaction transaction) {
        if(transaction.getType()==null || transaction.getType().isEmpty()){
            throw new RuntimeException("El tipo de transacción bancaria debe ser obligatorio");
        }
        if(!transaction.getType().equals("Retiro") && !transaction.getType().equals("Deposito")){
            throw new ValidationException("El tipo de transacción bancaria debe ser Retiro o Deposito");
        }
        if(transaction.getAmount()<=0.0){
            throw new ValidationException("El monto en una transacción bancaria debe ser mayor de S/.0.0");
        }
        if(transaction.getType().equals("Retiro") && (transaction.getAmount()>transaction.getBalance())){
            throw new ValidationException("En una transacción bancaria tipo retiro el monto no puede ser mayor al saldo");
        }
        if(transaction.getType().equals("Deposito") && (transaction.getAmount()>transaction.getBalance())){
            throw new ValidationException("En una transacción bancaria tipo retiro el monto no puede ser mayor al saldo");
        }
    }


}
