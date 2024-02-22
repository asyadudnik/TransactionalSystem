package com.optum.payment.system.services;

import com.optum.payment.system.entities.SourceModule;
import com.optum.payment.system.repositories.SourceModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service("sourceModuleService")
@Transactional
public class SourceModuleService {

    private final SourceModuleRepository repo;

    @Autowired
    public SourceModuleService( SourceModuleRepository repo){
        this.repo = repo;
    }


    public List<SourceModule> listAll() {
        return (List<SourceModule>)repo.findAll();
    }

    public void save(SourceModule sourceModule) {
        repo.save(sourceModule);
    }

    public SourceModule get(long id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Not found SourceModule with id = " + id));
    }

    public void delete(long id) {
        repo.deleteById(id);
    }
}
