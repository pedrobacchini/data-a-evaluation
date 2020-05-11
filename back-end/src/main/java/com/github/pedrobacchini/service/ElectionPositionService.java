package com.github.pedrobacchini.service;

import com.github.pedrobacchini.dto.ElectionPositionSelection;
import com.github.pedrobacchini.entity.Election;

import java.util.List;

public interface ElectionPositionService {

    List<ElectionPositionSelection> getAllByElection(Election election);
}
