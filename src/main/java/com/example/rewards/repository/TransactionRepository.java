package com.example.rewards.repository;

import com.example.rewards.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionDetails, Long>, CustomTransactionRepository {
}
