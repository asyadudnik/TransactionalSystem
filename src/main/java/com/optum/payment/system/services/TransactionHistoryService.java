package com.optum.payment.system.services;

import com.optum.payment.system.entities.TransactionHistory;
import com.optum.payment.system.repositories.TransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("transactionHistoryService")
@Transactional
public class TransactionHistoryService {

    private final TransactionHistoryRepository repo;

    @Autowired
    public TransactionHistoryService(TransactionHistoryRepository repo) {
        this.repo = repo;
    }

    public List<TransactionHistory> listAll() {
        return (List<TransactionHistory>) repo.findAll();
    }

    public void save(TransactionHistory transactionHistory) {
        repo.save(transactionHistory);
    }

    public TransactionHistory get(long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Transaction with id = " + id));
    }

    public void delete(long id) {
        repo.deleteById(id);
    }
}
