package com.optum.payment.system.controllers;

import com.optum.payment.system.entities.Bank;
import com.optum.payment.system.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/payment/api/banks")
public class BankController {


    private final BankService bankService;
    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping(value = "/list", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String listAll(Model model) {
        List<Bank> banks = bankService.listAll();
        model.addAttribute("banks", banks);
        return "bank/banks";
    }

    @GetMapping(value = "/new", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String showNewBankPage(Model model) {
        Bank bank = new Bank();
        model.addAttribute("bank", bank);
        return "bank/new_bank";
    }

    @PostMapping(value = "/save", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String saveBank(@ModelAttribute("bank") Bank bank) {
        bankService.save(bank);
        return "bank/bank_edit";
    }

    @PostMapping(value = "/edit/{id}",produces = { APPLICATION_JSON_VALUE })//consumes = { APPLICATION_JSON_VALUE },
    public String showEditBankPage(@PathVariable(name = "id") Long id) throws ChangeSetPersister.NotFoundException {
        ModelAndView modelAndView = new ModelAndView("bank/bank_edit");
        Bank bank = bankService.get(id);
        if(bank!=null) {
            modelAndView.addObject("bank", bank);
            return modelAndView.getViewName();
        }else {
            modelAndView.addObject("errMsg", "BANK NOT FOUND");
            return "errors/error";
        }
    }
}
