package com.hcmute.tlcn2021.repository;

import com.hcmute.tlcn2021.enumeration.ERole;
import com.hcmute.tlcn2021.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}