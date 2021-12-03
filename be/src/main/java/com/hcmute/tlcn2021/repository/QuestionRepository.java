package com.hcmute.tlcn2021.repository;

import com.hcmute.tlcn2021.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findAllByIsDeletedFalse(Pageable pageable);

    Page<Question> findAllByIsDeletedFalseAndFaculty_IdEquals(Long facultyId, Pageable pageable);


}
