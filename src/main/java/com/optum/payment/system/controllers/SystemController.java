package com.optum.payment.system.controllers;

import com.optum.payment.system.entities.System;
import com.optum.payment.system.services.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/payment/api/systems")
public class SystemController {


    private final SystemService systemService;
    @Autowired
    public SystemController(SystemService systemService) {
        this.systemService = systemService;
    }

    @GetMapping("/")
    public String listAll(Model model) {
        List<System> systems =  systemService.listAll();
        model.addAttribute("systems", systems);
        return "/systems/systems";
    }

    @GetMapping(value = "/new")
    public String showNewSystemPage(Model model) {
        System system = new System();
        model.addAttribute("system", system);
        return "/systems/new_system";
    }

    @PostMapping(value = "/save", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String saveSystem(@ModelAttribute("system") System system) {
        systemService.save(system);
        return "redirect:/";
    }
}
