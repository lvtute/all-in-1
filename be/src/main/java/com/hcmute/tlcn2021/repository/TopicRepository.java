package com.hcmute.tlcn2021.repository;

import com.hcmute.tlcn2021.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findAllByFaculty_Id(Long facultyId);
}
