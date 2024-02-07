package com.optum.payment.system.controllers;

import com.optum.payment.system.entities.Participant;
import com.optum.payment.system.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/payment/api/participants")
public class ParticipantController {


    private final ParticipantService participantService;
    @Autowired
    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping("/list")
    public String listAll(Model model) {
        List<Participant> listParticipants = participantService.listAll();
        model.addAttribute("listParticipants", listParticipants);
        return "/participant/participants";
    }

    @GetMapping(value = "/new")
    public String showNewParticipantPage(Model model) {
        Participant participant = new Participant();
        model.addAttribute("participant", participant);
        return "/participant/new_participant";
    }

    @PostMapping(value = "/save", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String saveParticipant(@ModelAttribute("participant") Participant participant) {
        participantService.save(participant);
        return "redirect:/";
    }
}
