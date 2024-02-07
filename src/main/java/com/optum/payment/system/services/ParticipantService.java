package com.optum.payment.system.services;

import com.optum.payment.system.entities.Participant;
import com.optum.payment.system.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("participantService")
@Transactional
public class ParticipantService {
    private final ParticipantRepository repo;
    @Autowired
    public ParticipantService( ParticipantRepository repo){
        this.repo = repo;
    }


    public List<Participant> listAll() {
        return (List<Participant>)repo.findAll();
    }

    public void save(Participant participant) {
        repo.save(participant);
    }

    public Participant get(long id) {
        Optional<Participant> value=repo.findById(id);
        return value.orElseThrow(() -> new ResourceNotFoundException("Not found Participant with id = " + id));
    }

    public void delete(long id) {
        repo.deleteById(id);
    }
}
