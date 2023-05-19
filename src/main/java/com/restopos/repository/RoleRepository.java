package com.restopos.repository;


import java.util.Optional;

import com.restopos.models.ERole;
import com.restopos.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}