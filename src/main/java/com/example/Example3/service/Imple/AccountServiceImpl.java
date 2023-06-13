package com.example.Example3.service.Imple;


import com.example.Example3.model.Account;
import com.example.Example3.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Example3.repository.AccountRepository;


@Service
public class AccountServiceImpl implements AccountService {
@Autowired
private AccountRepository accountRepository;

@Override
public Account createaccount(Account account) {
    return accountRepository.save(account);
}
}
