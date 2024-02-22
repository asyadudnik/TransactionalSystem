package com.optum.payment.system.services;

import com.optum.payment.system.entities.System;
import com.optum.payment.system.repositories.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service("systemService")
@Transactional
public class SystemService {
    private final SystemRepository repo;

    @Autowired
    public SystemService(SystemRepository repo) {
        this.repo = repo;
    }

    public List<System> listAll() {
        return (List<System>)repo.findAll();
    }

    public Optional<System> findBySystemName(@Param("name") String name)
    {
        return repo.findBySystemName(name);
    }

    public System save(System system) {
        return repo.save(system);
    }

    public System get(long id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Not found System with id = " + id));
    }

    public void delete(long id) {
        repo.deleteById(id);
    }
}
