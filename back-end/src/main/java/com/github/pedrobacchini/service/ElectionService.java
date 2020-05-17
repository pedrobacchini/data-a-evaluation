package com.github.pedrobacchini.service;

import com.github.pedrobacchini.dto.ElectionSelection;
import com.github.pedrobacchini.entity.Election;

import java.util.List;
import java.util.UUID;

public interface ElectionService {

    List<Election> getAllAvailable();

    List<ElectionSelection> getAllAvailableSelection();

    List<Election> getAllStarted();

    Election getById(UUID uuid);

    Election create(Election election);

    void update(UUID uuid, Election election);

    void delete(UUID uuid);
}
