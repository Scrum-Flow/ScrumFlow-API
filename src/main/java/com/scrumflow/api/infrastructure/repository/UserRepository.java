package com.scrumflow.api.infrastructure.repository;

import com.scrumflow.api.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository 
        extends JpaRepository<User, Integer> 
{
    Optional<User> findByEmail( String email );
}
