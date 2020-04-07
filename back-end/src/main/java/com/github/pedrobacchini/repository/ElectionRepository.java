package com.github.pedrobacchini.repository;

import com.github.pedrobacchini.entity.Election;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ElectionRepository extends UuidRepository<Election> {

    List<Election> findAllByStartDateIsAfter(LocalDate date);
}
