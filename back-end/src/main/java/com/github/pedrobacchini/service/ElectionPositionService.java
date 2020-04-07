package com.github.pedrobacchini.service;

import com.github.pedrobacchini.entity.Election;
import com.github.pedrobacchini.entity.ElectionPosition;

import java.util.List;
import java.util.UUID;

public interface ElectionPositionService {

    List<ElectionPosition> getAll();

    List<ElectionPosition> getAllByElection(Election election);

    ElectionPosition getById(UUID uuid);

    ElectionPosition create(ElectionPosition election);

    ElectionPosition update(UUID uuid, ElectionPosition election);

    void delete(UUID uuid);
}
