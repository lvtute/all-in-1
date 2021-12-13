package com.hcmute.tlcn2021.repository;

import com.hcmute.tlcn2021.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Optional<Faculty> findByName(String name);

    List<Faculty> findAllByIsDeletedFalse();

    Optional<Faculty> findByIdAndIsDeletedFalse(Long id);

    @Modifying
    @Query("UPDATE Faculty u SET u.isDeleted = true WHERE u.id = :id AND u.isDeleted = false")
    int softDeleteFaculty(@Param("id") Long id);
}
