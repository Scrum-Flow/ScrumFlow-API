package com.scrumflow.api.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.scrumflow.api.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService 
{
    @Value( "${api.security.token_secret}" )
    private String secret;
    
    public String generateToken( User user )
    {
        try 
        {
            return JWT.create()
                      .withIssuer( "main-api" )
                      .withSubject( user.getEmail() )
                      .withExpiresAt( generateExpirationDate() )
                      .sign( Algorithm.HMAC256( secret ) );
        }
        
        catch ( JWTCreationException e )
        {
            throw new RuntimeException( "Unable to create JWT", e );
        }
    }
    
    public String validateToken( String token )
    {
        try 
        {
            return JWT.require( Algorithm.HMAC256( secret ) )
                      .withIssuer( "main-api" )
                      .build()
                      .verify( token )
                      .getSubject();
        }
        
        catch ( JWTVerificationException e )
        {
            return null;
        }
    }
    
    private Instant generateExpirationDate()
    {
        return LocalDateTime.now().plusHours( 2 ).toInstant(ZoneOffset.of( "-03:00" ) );
    }
    
}
