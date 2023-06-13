package com.example.Example3.repository;

import com.example.Example3.model.Transaction;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByAccountNameCustomer(String nameCustomer);
    List<Transaction> findAllByCreateDateAndCreateDate(LocalDate createdate,LocalDate endDate);

}
