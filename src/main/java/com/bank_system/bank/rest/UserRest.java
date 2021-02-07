package com.bank_system.bank.rest;

import com.bank_system.bank.model.Transaction;
import com.bank_system.bank.model.User;
import com.bank_system.bank.service.interfaces.ITransactionService;
import com.bank_system.bank.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
public class UserRest {

    @Autowired
    private IUserService userService;

    @Autowired
    private ITransactionService transactionService;

    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public List<User> userList() {
        return userService.findUserList();
    }

    @RequestMapping(value = "/user/primary/transaction", method = RequestMethod.GET)
    public List<Transaction> getPrimaryTransactionList(@RequestParam("username") String username) {
        return transactionService.findAccountTransactionList(username);
    }

    @RequestMapping("/user/{username}/enable")
    public void enableUser(@PathVariable("username") String username) {
        userService.enableUser(username);
    }

    @RequestMapping("/user/{username}/disable")
    public void diableUser(@PathVariable("username") String username) {
        userService.disableUser(username);
    }
}
