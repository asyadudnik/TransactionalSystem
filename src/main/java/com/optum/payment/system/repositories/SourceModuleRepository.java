package com.optum.payment.system.repositories;

import com.optum.payment.system.entities.SourceModule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("sourceModuleRepository")
public interface SourceModuleRepository extends CrudRepository<SourceModule, Long> {
}
