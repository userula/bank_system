package com.bank_system.bank.controller;

import java.security.Principal;
import java.util.List;

import com.bank_system.bank.model.Account1;
import com.bank_system.bank.model.Account2;
import com.bank_system.bank.model.Recipient;
import com.bank_system.bank.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bank_system.bank.service.interfaces.ITransactionService;
import com.bank_system.bank.service.interfaces.IUserService;

@Controller
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private ITransactionService ITransactionService;

    @Autowired
    private IUserService IUserService;

    @RequestMapping(value = "/betweenAccounts", method = RequestMethod.GET)
    public String betweenAccounts(Model model) {
        model.addAttribute("transferFrom", "");
        model.addAttribute("transferTo", "");
        model.addAttribute("amount", "");

        return "betweenAccounts";
    }

    @RequestMapping(value = "/betweenAccounts", method = RequestMethod.POST)
    public String betweenAccountsPost(
            @ModelAttribute("transferFrom") String transferFrom,
            @ModelAttribute("transferTo") String transferTo,
            @ModelAttribute("amount") String amount,
            Principal principal
    ) throws Exception {
        User user = IUserService.findByUsername(principal.getName());
        Account1 account1 = user.getAccount1();
        Account2 account2 = user.getAccount2();
        ITransactionService.betweenAccountsTransfer(transferFrom, transferTo, amount, account1, account2);

        return "redirect:/userFront";
    }
    
    @RequestMapping(value = "/recipient", method = RequestMethod.GET)
    public String recipient(Model model, Principal principal) {
        List<Recipient> recipientList = ITransactionService.findRecipientList(principal);

        Recipient recipient = new Recipient();

        model.addAttribute("recipientList", recipientList);
        model.addAttribute("recipient", recipient);

        return "recipient";
    }

    @RequestMapping(value = "/recipient/save", method = RequestMethod.POST)
    public String recipientPost(@ModelAttribute("recipient") Recipient recipient, Principal principal) {

        User user = IUserService.findByUsername(principal.getName());
        recipient.setUser(user);
        ITransactionService.saveRecipient(recipient);

        return "redirect:/transfer/recipient";
    }

    @RequestMapping(value = "/recipient/edit", method = RequestMethod.GET)
    public String recipientEdit(@RequestParam(value = "recipientName") String recipientName, Model model, Principal principal){

        Recipient recipient = ITransactionService.findRecipientByName(recipientName);
        List<Recipient> recipientList = ITransactionService.findRecipientList(principal);

        model.addAttribute("recipientList", recipientList);
        model.addAttribute("recipient", recipient);

        return "recipient";
    }

    @RequestMapping(value = "/recipient/delete", method = RequestMethod.GET)
    @Transactional
    public String recipientDelete(@RequestParam(value = "recipientName") String recipientName, Model model, Principal principal){

        ITransactionService.deleteRecipientByName(recipientName);

        List<Recipient> recipientList = ITransactionService.findRecipientList(principal);

        Recipient recipient = new Recipient();
        model.addAttribute("recipient", recipient);
        model.addAttribute("recipientList", recipientList);


        return "recipient";
    }

    @RequestMapping(value = "/toSomeoneElse",method = RequestMethod.GET)
    public String toSomeoneElse(Model model, Principal principal) {
        List<Recipient> recipientList = ITransactionService.findRecipientList(principal);

        model.addAttribute("recipientList", recipientList);
        model.addAttribute("accountType", "");

        return "toSomeoneElse";
    }

    @RequestMapping(value = "/toSomeoneElse",method = RequestMethod.POST)
    public String toSomeoneElsePost(@ModelAttribute("recipientName") String recipientName, @ModelAttribute("accountType") String accountType, @ModelAttribute("amount") String amount, Principal principal) {
        User user = IUserService.findByUsername(principal.getName());
        Recipient recipient = ITransactionService.findRecipientByName(recipientName);
        ITransactionService.toSomeoneElseTransfer(recipient, accountType, amount, user.getAccount1(), user.getAccount2());

        return "redirect:/userFront";
    }
}
