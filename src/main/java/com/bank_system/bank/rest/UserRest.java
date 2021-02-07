package com.bank_system.bank.rest;

import java.util.List;

import com.bank_system.bank.model.Account1Transaction;
import com.bank_system.bank.model.Account2Transaction;
import com.bank_system.bank.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank_system.bank.service.interfaces.ITransactionService;
import com.bank_system.bank.service.interfaces.IUserService;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
public class UserRest {

    @Autowired
    private IUserService IUserService;

    @Autowired
    private ITransactionService ITransactionService;

    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public List<User> userList() {
        return IUserService.findUserList();
    }

    @RequestMapping(value = "/user/primary/transaction", method = RequestMethod.GET)
    public List<Account1Transaction> getPrimaryTransactionList(@RequestParam("username") String username) {
        return ITransactionService.findAccount1TransactionList(username);
    }

    @RequestMapping(value = "/user/savings/transaction", method = RequestMethod.GET)
    public List<Account2Transaction> getSavingsTransactionList(@RequestParam("username") String username) {
        return ITransactionService.findAccount2TransactionList(username);
    }

    @RequestMapping("/user/{username}/enable")
    public void enableUser(@PathVariable("username") String username) {
        IUserService.enableUser(username);
    }

    @RequestMapping("/user/{username}/disable")
    public void diableUser(@PathVariable("username") String username) {
        IUserService.disableUser(username);
    }
}
