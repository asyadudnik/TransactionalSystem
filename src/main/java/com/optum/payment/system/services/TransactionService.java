package com.optum.payment.system.services;

import com.optum.payment.system.entities.Participant;
import com.optum.payment.system.entities.Transaction;
import com.optum.payment.system.entities.enums.TransactionStatus;
import com.optum.payment.system.repositories.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service("transactionService")
@Transactional
public class TransactionService {
    private final TransactionRepository repo;

    @Autowired
    public TransactionService(TransactionRepository repo) {
        this.repo = repo;
    }

    public List<Transaction> listAll() {
        return (List<Transaction>) repo.findAll();
    }

    public void save(Transaction transaction) {
        try {
            repo.save(transaction);
        } catch (Exception ex) {
            log.error("Can't save transaction: {} ", ex.getMessage());
        }

    }

    public Transaction create(
            Integer credit,
            BigDecimal sum,
            String currency,
            Participant payer,
            Participant recipient
    ) {
        Transaction created = new Transaction();
        created.setTransactionCredit(credit);
        created.setTransactionDebit(credit == 0 ? 1 : 0);
        created.setTransactionSum(sum);
        created.setTransactionCurrency(currency);
        created.setTransactionDescription(credit == 0 ? "debit" : "credit");
        created.setParticipantSenderIbanAccount(payer.getIbanAccount());
        created.setParticipantReceiverIbanAccount(recipient.getIbanAccount());
        created.setTransactionStatus(TransactionStatus.OPEN.name());
        save(created);
        return created;
    }

    public Transaction get(long id) {
        return repo.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public void delete(long id) {
        repo.deleteById(id);
    }
}
