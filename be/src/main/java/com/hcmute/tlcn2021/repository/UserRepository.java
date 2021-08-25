package com.hcmute.tlcn2021.repository;

import com.hcmute.tlcn2021.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Page<User> findAllByIsDeletedFalse(Pageable pageable);

    @Modifying
    @Query("UPDATE User u SET u.isDeleted = true WHERE u.id = :id AND u.isDeleted = false")
    int softDeleteUser(@Param("id") Long id);
}
