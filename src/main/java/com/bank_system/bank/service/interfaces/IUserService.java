package com.bank_system.bank.service.interfaces;

import java.util.List;
import java.util.Set;

import com.bank_system.bank.model.User;
import com.bank_system.bank.model.security.UserRole;

public interface IUserService {
	User findByUsername(String username);

    User findByEmail(String email);

    boolean checkUserExists(String username, String email);

    boolean checkUsernameExists(String username);

    boolean checkEmailExists(String email);
    
    void save (User user);
    
    User createUser(User user, Set<UserRole> userRoles);
    
    User saveUser (User user); 
    
    List<User> findUserList();

    void enableUser (String username);

    void disableUser (String username);
}
