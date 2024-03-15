package com.optum.payment.system.services;

import com.optum.payment.system.entities.System;
import com.optum.payment.system.entities.SystemEvent;
import com.optum.payment.system.entities.enums.ActiveStatus;
import com.optum.payment.system.entities.enums.SystemName;
import com.optum.payment.system.repositories.SystemEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static com.optum.payment.system.utils.EnumUtils.getEnumFromString;


@Slf4j
@Service("systemEventService")
@Transactional
public class SystemEventService {
    private final SystemEventRepository repo;
    private final SystemService systemService;
    @Autowired
    public SystemEventService( SystemEventRepository repo, SystemService systemService){
        this.repo = repo;
        this.systemService =systemService;
    }


    public List<SystemEvent> listAll() {
        return (List<SystemEvent>) repo.findAll();
    }

    public void save(SystemEvent systemEvent) {
        if (systemEvent == null) {
            log.error("Null argument for save system event!");
            return;
        }
        createSystem(systemEvent.getSystemEventName());
        if (Strings.isBlank(systemEvent.getSystemEventStatus())) {
            systemEvent.setSystemEventStatus(ActiveStatus.ACTIVE.name());
        }
        repo.save(systemEvent);
    }

    public System createSystem(String systemName) {
        if (!Strings.isBlank(systemName)) {
            if (systemService.findBySystemName(systemName).isPresent()) {
                return systemService.findBySystemName(systemName).orElseThrow(NoSuchElementException::new);
            } else {
                System systemCreated = new System();
                systemCreated.setSystemName(getEnumFromString(SystemName.class, systemName).name());
                systemCreated.setSystemDescription(systemCreated.getSystemName());
                return systemService.save(systemCreated);
            }
        } else {
            log.error("Can't create system with empty name.");
        }
        return null;
    }

    public SystemEvent get(long id) {
        if (repo.findById(id).isPresent()) {
            return repo.findById(id).orElseThrow(NoSuchElementException::new);
        } else {
            if(log.isDebugEnabled()) {
                log.error("Can't find system with id {}", id);
            }
            return null;
        }
    }

    public void delete(long id) {
        repo.deleteById(id);
    }
}
