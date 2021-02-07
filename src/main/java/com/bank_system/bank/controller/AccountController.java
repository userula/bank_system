package com.bank_system.bank.controller;

import java.security.Principal;
import java.util.List;

import com.bank_system.bank.model.*;
import com.bank_system.bank.service.interfaces.IAccountService;
import com.bank_system.bank.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bank_system.bank.model.Account1;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
    private com.bank_system.bank.service.interfaces.IUserService IUserService;
	
	@Autowired
	private IAccountService IAccountService;
	
	@Autowired
	private ITransactionService ITransactionService;
	
	@RequestMapping("/account1")
	public String account1(Model model, Principal principal) {
		List<Account1Transaction> account1TransactionList = ITransactionService.findAccount1TransactionList(principal.getName());
		
		User user = IUserService.findByUsername(principal.getName());
        Account1 account1 = user.getAccount1();

        model.addAttribute("account1", account1);
        model.addAttribute("account1TransactionList", account1TransactionList);
		
		return "account-1";
	}

	@RequestMapping("/account2")
    public String account2(Model model, Principal principal) {
		List<Account2Transaction> account2TransactionList = ITransactionService.findAccount2TransactionList(principal.getName());
        User user = IUserService.findByUsername(principal.getName());
        Account2 account2 = user.getAccount2();

        model.addAttribute("account2", account2);
        model.addAttribute("account2TransactionList", account2TransactionList);

        return "account-2";
    }
	
	@RequestMapping(value = "/deposit", method = RequestMethod.GET)
    public String deposit(Model model) {
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "deposit";
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public String depositPOST(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
        IAccountService.deposit(accountType, Double.parseDouble(amount), principal);

        return "redirect:/userFront";
    }
    
    @RequestMapping(value = "/withdraw", method = RequestMethod.GET)
    public String withdraw(Model model) {
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "withdraw";
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public String withdrawPOST(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
        IAccountService.withdraw(accountType, Double.parseDouble(amount), principal);

        return "redirect:/userFront";
    }
}
