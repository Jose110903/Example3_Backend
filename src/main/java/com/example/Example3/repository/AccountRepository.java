package com.example.Example3.repository;

import com.example.Example3.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByNameCustomer(String NameCustomer);

    boolean existsByNameCustomerAndNumberAccount(String NameCustomer,String numberAccount);
    boolean existsAccountByNameCustomer(String NameCustomer);
}
