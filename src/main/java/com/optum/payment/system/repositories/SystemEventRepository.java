package com.optum.payment.system.repositories;

import com.optum.payment.system.entities.SystemEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("systemEventRepository")
public interface SystemEventRepository extends CrudRepository<SystemEvent, Long> {

}
