package com.scrumflow.api.infrastructure.security;

import com.scrumflow.api.domain.model.User;
import com.scrumflow.api.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class SecurityFilter extends OncePerRequestFilter
{
    @Autowired
    TokenService tokenService;
    
    @Autowired
    UserRepository userRepository;
    
    @Override
    protected void doFilterInternal( HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain ) throws ServletException, IOException
    {
        String token = recoverToken( request );
        
        String login = tokenService.validateToken( token );
        
        if ( login != null )
        {
            User user = userRepository.findByEmail( login ).orElseThrow( () -> new RuntimeException( "User not found" ) );
            List<SimpleGrantedAuthority> authorities= Collections.singletonList( new SimpleGrantedAuthority( "ROLE_USER" ) );
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken( user, null, authorities );
            SecurityContextHolder.getContext().setAuthentication( authentication );
        }
        
        filterChain.doFilter( request, response );
    }
    
    private String recoverToken(HttpServletRequest request) 
    {
        String token = request.getHeader( "Authorization" );
        
        if ( token == null )
        {
            return null;
        }
        
        return token.replace( "Bearer ", "" );
    }
}
