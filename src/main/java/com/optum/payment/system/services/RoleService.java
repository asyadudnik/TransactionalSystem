package com.optum.payment.system.services;

import com.optum.payment.system.entities.Role;
import com.optum.payment.system.repositories.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service("roleService")
@Transactional
public class RoleService {
    public static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository repo;
    @Autowired
    public RoleService( RoleRepository repo){
        this.repo = repo;
    }

    public List<Role> listAll() {
        return (List<Role>) repo.findAll();
    }

    public Role save(Role role) {
        if (role != null && role.getId() != null && get(role.getId()) != null) {
            if(logger.isDebugEnabled()){
                logger.info("Role {} already exist!", role.getRoleName());
            }
            return role;
        } else {
            if (role == null) throw new AssertionError();
            return repo.save( role);
        }
    }

    public Role get(long id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Not found Role with id = " + id));
    }



    public void delete(long id) {
        repo.deleteById(id);
    }
}
