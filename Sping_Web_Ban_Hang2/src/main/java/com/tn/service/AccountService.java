package com.tn.service;

import com.tn.entity.Account;
import com.tn.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Account> opAccount = accountRepo.findByUserName(username);

        if (opAccount.isEmpty()) {
            throw new UsernameNotFoundException("Account not found with username and password");
        }

        return  new User(username, opAccount.get().getPassWord(), Collections.emptyList());
    }
}
