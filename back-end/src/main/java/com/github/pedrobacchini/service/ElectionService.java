package com.github.pedrobacchini.service;

import com.github.pedrobacchini.entity.Election;

import java.util.List;
import java.util.UUID;

public interface ElectionService {

    List<Election> getAll();

    List<Election> getAllAvailable();

    Election getById(UUID uuid);

    Election create(Election election);

    Election update(UUID uuid, Election election);

    void delete(UUID uuid);
}
