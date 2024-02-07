package com.optum.payment.system.repositories;

import com.optum.payment.system.entities.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("transactionRepository")
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}
