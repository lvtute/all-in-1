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

    @Query(value = " SELECT id " +
            " FROM (SELECT u.id, count(q.id) as my_count " +
            " FROM user u LEFT JOIN question q ON u.id = q.adviser_id JOIN user_topic ut ON u.id = ut.user_id " +
            " WHERE ut.topic_id = :topicId " +
            " AND u.is_deleted = false " +
            " GROUP BY u.id) AS T ORDER BY my_count ASC LIMIT 1 ", nativeQuery = true)
    Optional<Long> findAdviserIdForAssigningQuestionByTopicId(@Param("topicId") Long topicId);

    @Query(value = " SELECT id " +
            " FROM (SELECT u.id, count(q.id) as my_count " +
            " FROM user u LEFT JOIN question q ON u.id = q.adviser_id JOIN role r ON u.role_id = r.id " +
            " WHERE u.is_deleted = false " +
            " AND u.faculty_id = :facultyId " +
            " AND r.name = 'ROLE_ADVISER' " +
            " GROUP BY u.id) AS T ORDER BY my_count ASC LIMIT 1 ", nativeQuery = true)
    Optional<Long> findAdviserIdForAssigningQuestionByFaculty(@Param("facultyId") Long facultyId);
}
