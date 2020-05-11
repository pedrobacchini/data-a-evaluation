package com.github.pedrobacchini.service.impl;

import com.github.pedrobacchini.dto.ElectionPositionSelection;
import com.github.pedrobacchini.entity.Election;
import com.github.pedrobacchini.repository.ElectionPositionRepository;
import com.github.pedrobacchini.service.ElectionPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ElectionPositionServiceImpl implements ElectionPositionService {

    private final ElectionPositionRepository electionPositionRepository;

    @Override
    public List<ElectionPositionSelection> getAllByElection(Election election) {
        return electionPositionRepository.findAllByElection(election);
    }
}
