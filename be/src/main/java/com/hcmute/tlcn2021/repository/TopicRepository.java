package com.hcmute.tlcn2021.repository;

import com.hcmute.tlcn2021.model.Topic;
import com.hcmute.tlcn2021.payload.response.PieChartDataResponse;
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

    @Query(" SELECT new com.hcmute.tlcn2021.payload.response.PieChartDataResponse(t.name,COUNT(u.id)) " +
            " FROM User u RIGHT JOIN u.topics t" +
            " WHERE u.isDeleted = FALSE AND t.isDeleted = FALSE " +
            " AND t.faculty.id = :facultyId " +
            " GROUP BY t.name ")
    List<PieChartDataResponse> getTopicUserPieChartDataResponse(@Param("facultyId") Long facultyId);

    @Query(" SELECT new com.hcmute.tlcn2021.payload.response.PieChartDataResponse(t.name, COUNT(q.id)) " +
            " FROM Topic t LEFT JOIN Question q ON t.id = q.topic.id" +
            " WHERE t.isDeleted = FALSE AND q.isDeleted = FALSE " +
            " AND t.faculty.id = :facultyId " +
            " GROUP BY t.name ")
    List<PieChartDataResponse> getTopicQuestionPieChartDataResponse(@Param("facultyId") Long facultyId);

}
