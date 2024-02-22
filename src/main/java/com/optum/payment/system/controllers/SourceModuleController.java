package com.optum.payment.system.controllers;

import com.optum.payment.system.entities.SourceModule;
import com.optum.payment.system.services.SourceModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payment/api/sourceModules")
public class SourceModuleController {


    private final SourceModuleService sourceModuleService;

    @Autowired
    public SourceModuleController(SourceModuleService sourceModuleService) {
        this.sourceModuleService = sourceModuleService;
    }

    @GetMapping("/list")
    public String listAll(Model model) {
        List<SourceModule> listSourceModules = sourceModuleService.listAll();
        model.addAttribute("sourceModules", listSourceModules);
        return "sourceModule/sourceModules";
    }

    @GetMapping(value = "/new")
    public String showNewSourceModulePage(Model model) {
        SourceModule sourceModule = new SourceModule();
        model.addAttribute("sourceModule", sourceModule);
        return "sourceModule/new_sourceModule";
    }

    @PostMapping(value = "/save", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String saveSystem(@ModelAttribute("sourceModule") SourceModule sourceModule) {
        sourceModuleService.save(sourceModule);
        return "redirect:/";
    }
}
