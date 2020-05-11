package com.github.pedrobacchini.repository;

import com.github.pedrobacchini.entity.Election;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ElectionRepository extends UuidRepository<Election> {

    @Transactional(readOnly = true)
    <T> List<T> findAllByStartDateIsAfter(LocalDate date, Class<T> type);

    @Transactional(readOnly = true)
    List<Election> findAllByStartDateIsBefore(LocalDate date);
}
