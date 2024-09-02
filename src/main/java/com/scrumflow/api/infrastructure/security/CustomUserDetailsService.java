package com.scrumflow.api.infrastructure.security;

import com.scrumflow.api.domain.model.User;
import com.scrumflow.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserDetailsService implements UserDetailsService 
{
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        User user = userRepository.findByEmail( username ).orElseThrow( () -> new UsernameNotFoundException( "User not found: " + username ) );
        
        return new org.springframework.security.core.userdetails.User( user.getEmail(), user.getPassword(), new ArrayList<>() );
    }
}
