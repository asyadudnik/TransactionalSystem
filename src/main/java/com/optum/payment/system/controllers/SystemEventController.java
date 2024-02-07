package com.optum.payment.system.controllers;

import com.optum.payment.system.entities.SystemEvent;
import com.optum.payment.system.services.SystemEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/payment/api/systemEvents")
public class SystemEventController {


    private final SystemEventService systemEventService;
    @Autowired
    public SystemEventController(SystemEventService systemEventService) {
        this.systemEventService = systemEventService;
    }

    @GetMapping("/list")
    public String listAll(Model model) {
        List<SystemEvent> listSystemEvents = systemEventService.listAll();
        model.addAttribute("listSystemEvents", listSystemEvents);
        return "/systemEvent/systemEvents";
    }

    @GetMapping(value = "/new")
    public String showNewSystemEventPage(Model model) {
        SystemEvent systemEvent = new SystemEvent();
        model.addAttribute("systemEvent", systemEvent);
        return "/systemEvent/new_systemEvent";
    }

    @PostMapping(value = "/save")
    public String saveSystemEvent(@ModelAttribute("systemEvent") SystemEvent systemEvent) {
        systemEventService.save(systemEvent);
        return "redirect:/";
    }
}
