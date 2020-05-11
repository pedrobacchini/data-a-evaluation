package com.github.pedrobacchini.repository;

import com.github.pedrobacchini.dto.ElectionPositionSelection;
import com.github.pedrobacchini.entity.Election;
import com.github.pedrobacchini.entity.ElectionPosition;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ElectionPositionRepository extends UuidRepository<ElectionPosition> {

    @Transactional(readOnly = true)
    List<ElectionPositionSelection> findAllByElection(Election election);
}
