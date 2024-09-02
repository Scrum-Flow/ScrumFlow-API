package com.scrumflow.api.controllers;

import com.scrumflow.api.domain.model.User;
import com.scrumflow.api.dto.JWTResponseDTO;
import com.scrumflow.api.dto.LoginRequestDTO;
import com.scrumflow.api.dto.RegisterRequestDTO;
import com.scrumflow.api.infrastructure.security.TokenService;
import com.scrumflow.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping( "/auth" )
public class AuthController 
{
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private TokenService tokenService;
    
    @PostMapping( "/login" )
    public ResponseEntity login( @RequestBody LoginRequestDTO body )
    {
        User user = userRepository.findByEmail( body.email() ).orElseThrow( () -> new RuntimeException( "User not found" ) );
        
        if ( passwordEncoder.matches( body.password(), user.getPassword() ) )
        {
            return ResponseEntity.ok( new JWTResponseDTO( user.getName(), tokenService.generateToken( user ) ) );
        }
        
        return ResponseEntity.badRequest().build();
    }
    
     @PostMapping( "/register" )
    public ResponseEntity register( @RequestBody RegisterRequestDTO body )
    {
        Optional<User> user = userRepository.findByEmail( body.email() );
        
        if ( user.isEmpty() ) 
        {
            User newUser = new User();
            newUser.setName( body.name() );
            newUser.setEmail( body.email() );
            newUser.setPassword( passwordEncoder.encode( body.password() ) );
            
            userRepository.save( newUser );
            
            return ResponseEntity.ok( new JWTResponseDTO( newUser.getName(), tokenService.generateToken( newUser ) ) );
        }
        
        return ResponseEntity.badRequest().build();
    }
}
