package com.scrumflow.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scrumflow.domain.enums.RoleType;
import com.scrumflow.domain.model.Role;
import com.scrumflow.domain.model.User;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType roleType);

    @Query("SELECT u from User u join u.roles r where r.name = :roleName")
    List<User> findUsersByName(@Param("roleName") String roleName);
}
