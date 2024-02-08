package com.optum.payment.system.services;

import com.optum.payment.system.entities.System;
import com.optum.payment.system.entities.SystemEvent;
import com.optum.payment.system.entities.enums.ActiveStatus;
import com.optum.payment.system.entities.enums.SystemName;
import com.optum.payment.system.repositories.SystemEventRepository;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static com.optum.payment.system.utils.EnumUtils.getEnumFromString;

@Service("systemEventService")
@Transactional
public class SystemEventService {
    public static final Logger logger = LoggerFactory.getLogger(SystemEventService.class);
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
            logger.error("Null argument for save system event!");
            return;
        }
        createSystem(systemEvent.getSystemEventName());
        if (Strings.isBlank(systemEvent.getSystemEventStatus())) {
            systemEvent.setSystemEventStatus(ActiveStatus.ACTIVE.name());
        }
        repo.save(systemEvent);
    }

    private com.optum.payment.system.entities.System createSystem(String systemName) {
        if (!Strings.isBlank(systemName)) {
            if (systemService.findBySystemName(systemName).isPresent()) {
                return systemService.findBySystemName(systemName).orElseThrow(NoSuchElementException::new);
            } else {
                System systemCreated = new System();
                systemCreated.setSystemName(getEnumFromString(SystemName.class, systemName).name());
                systemCreated.setSystemDescription(systemCreated.getSystemName());
                System createdSystem = systemService.save(systemCreated);
                if (createdSystem == null) {
                    logger.error("Can't create system with name {}.", systemName);
                    return null;
                }
                return createdSystem;
            }
        } else {
            logger.error("Can't create system with empty name.");
            return null;
        }
    }

    public SystemEvent get(long id) {
        if (repo.findById(id).isPresent()) {
            return repo.findById(id).orElseThrow(NoSuchElementException::new);
        } else {
            if(logger.isDebugEnabled()) {
                logger.error("Can't find system with id {}", id);
            }
            return null;
        }
    }

    public void delete(long id) {
        repo.deleteById(id);
    }
}
