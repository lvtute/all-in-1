package com.hcmute.tlcn2021.repository;

import com.hcmute.tlcn2021.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    String BASIC_QUESTION_SEARCH_QUERY = " SELECT q " +
            " FROM Question q " +
            " WHERE ( UPPER(q.title) LIKE %:searchString% OR UPPER(q.content) LIKE %:searchString% OR UPPER(q.answer) LIKE %:searchString% ) ";
    String CHECK_PRIVATE_CONDITION = " AND q.isPrivate = FALSE ";
    String CHECK_FACULTY_ID_CONDITION = " AND q.faculty.id = :facultyId ";

    Page<Question> findAllByIsDeletedFalseAndFaculty_IdEquals(Long facultyId, Pageable pageable);

    Page<Question> findAllByIsDeletedFalseAndFaculty_IdEqualsAndAnswerNull(Long facultyId, Pageable pageable);

    Page<Question> findAllByIsDeletedFalseAndAdviser_IdEquals(Long adviserId, Pageable pageable);

    Page<Question> findAllByIsDeletedFalseAndAdviser_IdEqualsAndAnswerNull(Long adviserId, Pageable pageable);

    Optional<Question> findByIdAndIsDeletedFalse(Long id);

    @Modifying
    @Query("UPDATE Question q SET q.isDeleted = true WHERE q.id = :id AND q.isDeleted = false")
    int softDelete(@Param("id") Long id);

    @Query(value = BASIC_QUESTION_SEARCH_QUERY +
            CHECK_PRIVATE_CONDITION
    )
    Page<Question> findByIsPrivateFalseAndSearchString(@Param("searchString") String searchString, Pageable pageable);

    @Query(value = BASIC_QUESTION_SEARCH_QUERY +
            CHECK_PRIVATE_CONDITION +
            CHECK_FACULTY_ID_CONDITION
    )
    Page<Question> findByFaculty_IdEqualsAndIsPrivateFalseAndSearchString(@Param("searchString") String searchString, @Param("facultyId") Long facultyId, Pageable pageable);

}
