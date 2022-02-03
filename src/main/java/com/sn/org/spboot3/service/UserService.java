package com.sn.org.spboot3.service;

import com.sn.org.spboot3.repo.PersonRepo;
import jdk.jfr.StackTrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    PersonRepo personRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = personRepo.getByLogin(username);
       if(userDetails==null)  throw new UsernameNotFoundException("You are not registration!");

        return userDetails;
    }
}
