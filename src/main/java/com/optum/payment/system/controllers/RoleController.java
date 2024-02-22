package com.optum.payment.system.controllers;

import com.optum.payment.system.entities.Role;

import com.optum.payment.system.services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payment/api/roles")
public class RoleController {
    public static final Logger logger = LoggerFactory.getLogger(RoleController.class);


    private final RoleService roleService;
    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String listAll(Model model) {
        List<Role> roleList = roleService.listAll();
        model.addAttribute("listRoles", roleList);
        return "role/roles";
    }

    @GetMapping("/new")
    public String showNewRolePage(Model model) {
        Role role = new Role();
        model.addAttribute("role", role);
        return "role/new_role";
    }

    @PostMapping(value = "/save")
    public String saveRole(@ModelAttribute("role") Role role) {
        roleService.save(role);
        return "redirect:/api/role/roles";
    }
}
