package com.hcmute.tlcn2021.repository;

import com.hcmute.tlcn2021.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Optional<Topic> findByName(String name);

    @Modifying
    @Query("UPDATE Topic t SET t.isDeleted = true WHERE t.id = :id AND t.isDeleted = false")
    int softDeleteTopic(@Param("id") Long id);

    List<Topic> findAllByFaculty_IdAndIsDeletedFalse(Long facultyId);

    List<Topic> findAllByIsDeletedFalse();

    Optional<Topic> findByIdAndIsDeletedFalse(Long id);

}
