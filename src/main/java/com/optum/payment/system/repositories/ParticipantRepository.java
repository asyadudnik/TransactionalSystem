package com.optum.payment.system.repositories;

import com.optum.payment.system.entities.Participant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("participantRepository")
public interface ParticipantRepository extends CrudRepository<Participant, Long> {
}
