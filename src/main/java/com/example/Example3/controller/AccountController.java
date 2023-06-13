package com.example.Example3.controller;

import com.example.Example3.exception.ValidationException;
import com.example.Example3.model.Account;
import com.example.Example3.repository.AccountRepository;
import com.example.Example3.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bank/v1")
public class AccountController {
    @Autowired
    private AccountService accountService;
    private final AccountRepository accountRepository;
    public AccountController (AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }



    //Endpoint:http://localhost:8083/api/bank/v1/accounts
    //Method: GET
    @Transactional(readOnly = true)
    @RequestMapping ("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts(){
        return new ResponseEntity<List<Account>>(accountRepository.findAll(), HttpStatus.OK);
    }








    //Endpoint:http://localhost:8083/api/bank/v1/accounts
    //Method: POST
    @Transactional
    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@RequestBody Account account){
        existsBynameCustomerAndnumberAccouunt(account);
        validateAccount(account);
        return new ResponseEntity<>(accountService.createaccount(account), HttpStatus.CREATED);
    }

    public void validateAccount(Account account) {
        if(account.getNameCustomer() == null || account.getNameCustomer().trim().isEmpty()) {
            throw new RuntimeException("El nameCustomer debe ser obligatorio.");
        }
        if(account.getNameCustomer().length() > 30) {
            throw new ValidationException("El nameCustomer no debe exceder los 30 caracteres.");
        }
        if(account.getNumberAccount() == null || account.getNumberAccount().trim().isEmpty()) {
            throw new ValidationException("El numberAccount debe ser obligatorio.");
        }
        if(account.getNumberAccount().length() > 13 || account.getNumberAccount().length() < 13) {
            throw new ValidationException("El numberAccount debe tener una longitud de 13 caracteres");
        }
    }

    private void existsBynameCustomerAndnumberAccouunt(Account account) {
        if(accountRepository.existsByNameCustomerAndNumberAccount(account.getNameCustomer(), account.getNumberAccount())) {
            throw new ValidationException("No se puede registrar la cuenta porque ya existe el nameCustomer y el numberAccount");
        }
        if(accountRepository.existsAccountByNameCustomer(account.getNameCustomer())){
            throw new ValidationException("No se puede registrar la cuenta porque ya existe el nameCustomer");
        }

    }




}
