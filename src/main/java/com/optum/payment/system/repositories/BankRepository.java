package com.optum.payment.system.repositories;

import com.optum.payment.system.entities.Bank;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("bankRepository")
public interface BankRepository extends CrudRepository<Bank, Long> {
}
