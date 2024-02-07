package com.optum.payment.system.repositories;

import com.optum.payment.system.entities.TransactionHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("transactionHistoryRepository")
public interface TransactionHistoryRepository extends CrudRepository<TransactionHistory, Long> {
}
