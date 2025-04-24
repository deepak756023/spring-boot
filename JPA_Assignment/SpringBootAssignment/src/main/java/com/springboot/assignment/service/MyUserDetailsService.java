//2
package com.springboot.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.assignment.entity.User;
import com.springboot.assignment.entity.UserPrincipal;
import com.springboot.assignment.repository.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

    
    @Autowired
    private UserRepo repo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = repo.findByUsername(username);
        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(user);
    }
    
    
    

}
