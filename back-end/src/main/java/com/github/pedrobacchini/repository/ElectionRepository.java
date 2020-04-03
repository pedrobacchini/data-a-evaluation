package com.github.pedrobacchini.repository;

import com.github.pedrobacchini.entity.Election;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionRepository extends UuidRepository<Election> {
}
