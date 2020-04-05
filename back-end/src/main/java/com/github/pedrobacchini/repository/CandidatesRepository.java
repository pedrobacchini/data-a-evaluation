package com.github.pedrobacchini.repository;

import com.github.pedrobacchini.entity.Candidate;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatesRepository extends UuidRepository<Candidate> {
}
