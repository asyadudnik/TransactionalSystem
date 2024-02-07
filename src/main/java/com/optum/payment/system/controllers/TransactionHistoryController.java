package com.optum.payment.system.controllers;

import com.optum.payment.system.entities.TransactionHistory;
import com.optum.payment.system.services.TransactionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment/api/transactionsHistory")
public class TransactionHistoryController {

    private final TransactionHistoryService transactionHistoryService;
    @Autowired
    public TransactionHistoryController(TransactionHistoryService transactionHistoryService) {
        this.transactionHistoryService = transactionHistoryService;
    }


    @GetMapping("/")
    public String listAll(Model model) {
        List<TransactionHistory> listTransactionsHistory =transactionHistoryService.listAll();
        model.addAttribute("listTransactionsHistory", listTransactionsHistory);
        return "transactionsHistory";
    }
    @GetMapping(value = "/new")
    public String showNewTransactionHistoryPage(Model model) {
        TransactionHistory transactionHistory = new TransactionHistory();
        model.addAttribute("transactionHistory", transactionHistory);
        return "new_transactionHistory";
    }

    @PostMapping(value = "/save", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String saveTransactionHistory(@ModelAttribute("transactionHistory") TransactionHistory transactionHistory) {
        transactionHistoryService.save(transactionHistory);
        return "redirect:/";
    }


}
