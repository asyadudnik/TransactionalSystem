package com.optum.payment.system.controllers;

import com.optum.payment.system.entities.Participant;
import com.optum.payment.system.entities.System;
import com.optum.payment.system.entities.SystemEvent;
import com.optum.payment.system.entities.Transaction;
import com.optum.payment.system.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/payment/api/transactions")
public class TransactionController {


    private final TransactionService transactionService;
    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/")
    public String listAll(Model model) {
        List<Transaction> listTransactions =  transactionService.listAll();
        model.addAttribute("listTransactions", listTransactions);
        return "transactions/transactions";
    }

    @GetMapping(value = "/new")
    public String createNewTransaction(Transaction transaction) {
        transactionService.save(transaction);
        ModelAndView modelAndView = new ModelAndView("transactions/new_transaction");
        modelAndView.addObject("transaction", transaction);
        return modelAndView.getViewName();
    }

    @PostMapping(value = "/save", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String saveTransaction(@ModelAttribute("transaction") Transaction transaction) {
        transactionService.save(transaction);
        return "redirect:/";
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createNewTransactionByData(Integer credit,
                                             BigDecimal sum,
                                             String currency,
                                             System system,
                                             SystemEvent systemEvent,
                                             Participant payer,
                                             Participant recipient
    ) {
        Transaction created = transactionService.create(credit, sum, currency, payer, recipient);
        ModelAndView modelAndView = new ModelAndView("transactions/new_transaction");
        modelAndView.addObject("transaction", created);
        return modelAndView.getViewName();
    }


}
