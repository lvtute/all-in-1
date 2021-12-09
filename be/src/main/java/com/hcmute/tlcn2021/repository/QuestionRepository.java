package com.hcmute.tlcn2021.repository;

import com.hcmute.tlcn2021.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findAllByIsDeletedFalse(Pageable pageable);

    Page<Question> findAllByIsDeletedFalseAndFaculty_IdEquals(Long facultyId, Pageable pageable);

    Page<Question> findAllByIsDeletedFalseAndAdviser_IdEquals(Long adviserId, Pageable pageable);

    Page<Question> findAllByIsDeletedFalseAndAdviser_IdEqualsAndAnswerNull(Long adviserId, Pageable pageable);

    @Modifying
    @Query("UPDATE Question q SET q.isDeleted = true WHERE q.id = :id AND q.isDeleted = false")
    int softDelete(@Param("id") Long id);

}
