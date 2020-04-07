package com.github.pedrobacchini.repository;

import com.github.pedrobacchini.entity.Election;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ElectionRepository extends UuidRepository<Election> {

    @Transactional(readOnly = true)
    List<Election> findAllByStartDateIsAfter(LocalDate date);

    @Transactional(readOnly = true)
    List<Election> findAllByStartDateIsBefore(LocalDate date);
}
