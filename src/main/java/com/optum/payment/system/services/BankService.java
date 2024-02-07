package com.optum.payment.system.services;

import com.optum.payment.system.entities.Bank;
import com.optum.payment.system.repositories.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("bankService")
@Transactional
public class BankService {

    private final BankRepository bankRepository;
    @Autowired
    public BankService(BankRepository repository){
        this.bankRepository = repository;
    }
    public List<Bank> listAll() {
        return (List<Bank>)bankRepository.findAll();
    }

    public void save(Bank bank) {
        bankRepository.save(bank);
    }

    public Bank get(long id) throws ChangeSetPersister.NotFoundException {
        Optional<Bank> value =this.bankRepository.findById(id);
        if(value.isEmpty()){
            throw new ChangeSetPersister.NotFoundException();
        }
        return value.get();
    }

    public void delete(long id) {
        bankRepository.deleteById(id);
    }
}
