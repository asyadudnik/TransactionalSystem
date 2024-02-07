package com.optum.payment.system.repositories;

import com.optum.payment.system.entities.System;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository("systemRepository")
public interface SystemRepository extends CrudRepository<System, Long> {

    @Query("SELECT s FROM System s WHERE s.systemName=:name")
    Optional<System> findBySystemName(@Param("name") String name);

}
