package com.github.pedrobacchini.repository;

import com.github.pedrobacchini.dto.ElectionSummary;
import com.github.pedrobacchini.entity.Election;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ElectionRepository extends UuidRepository<Election> {

    @Transactional(readOnly = true)
    List<Election> findAllByStartDateIsAfter(LocalDate date);

    @Transactional(readOnly = true)
    @Query("select e.uuid as uuid, e.name as name from Election e where e.startDate > :date")
    List<ElectionSummary> findAllByStartDateIsAfterSummary(LocalDate date);

    @Transactional(readOnly = true)
    List<Election> findAllByStartDateIsBefore(LocalDate date);
}
