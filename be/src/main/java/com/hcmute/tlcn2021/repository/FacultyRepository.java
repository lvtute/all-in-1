package com.hcmute.tlcn2021.repository;

import com.hcmute.tlcn2021.model.Faculty;
import com.hcmute.tlcn2021.payload.response.PieChartDataResponse;
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

    @Query(" SELECT new com.hcmute.tlcn2021.payload.response.PieChartDataResponse(f.name, COUNT(u.id)) " +
            " FROM Faculty f JOIN User u ON f.id = u.faculty.id " +
            " WHERE u.isDeleted = FALSE AND f.isDeleted = FALSE " +
            " GROUP BY f.name ")
    List<PieChartDataResponse> getFacultyUserPieChartDataResponse();
}
