package com.bank_system.bank.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import com.bank_system.bank.dao.RoleDao;
import com.bank_system.bank.model.Account1;
import com.bank_system.bank.model.Account2;
import com.bank_system.bank.model.User;
import com.bank_system.bank.model.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bank_system.bank.service.interfaces.IUserService;

@Controller
public class MainController {

    @Autowired
    private IUserService IUserService;

    @Autowired
    private RoleDao roleDao;

    @RequestMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        User user = new User();

        model.addAttribute("user", user);

        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPost(@ModelAttribute("user") User user, Model model) {

        if (IUserService.checkUserExists(user.getUsername(), user.getEmail())) {

            if (IUserService.checkEmailExists(user.getEmail())) {
                model.addAttribute("emailExists", true);
            }

            if (IUserService.checkUsernameExists(user.getUsername())) {
                model.addAttribute("usernameExists", true);
            }

            return "signup";
        } else {
            Set<UserRole> userRoles = new HashSet<>();
            userRoles.add(new UserRole(user, roleDao.findByName("ROLE_USER")));
            IUserService.createUser(user, userRoles);

            return "redirect:/";
        }
    }

    @RequestMapping("/userFront")
    public String userFront(Principal principal, Model model) {
        User user = IUserService.findByUsername(principal.getName());
        Account1 account1 = user.getAccount1();
        Account2 account2 = user.getAccount2();

        model.addAttribute("account1", account1);
        model.addAttribute("account2", account2);

        return "userFront";
    }
}
