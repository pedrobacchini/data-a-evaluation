package com.github.pedrobacchini.service.impl;

import com.github.pedrobacchini.config.LocaleMessageSource;
import com.github.pedrobacchini.dto.ElectionPositionSummary;
import com.github.pedrobacchini.entity.Election;
import com.github.pedrobacchini.entity.ElectionPosition;
import com.github.pedrobacchini.exception.IntegrityViolationException;
import com.github.pedrobacchini.exception.NotFoundException;
import com.github.pedrobacchini.repository.ElectionPositionRepository;
import com.github.pedrobacchini.service.ElectionPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ElectionPositionServiceImpl implements ElectionPositionService {

    private final ElectionPositionRepository electionPositionRepository;
    private final LocaleMessageSource localeMessageSource;

    @Override
    public List<ElectionPosition> getAll() { return electionPositionRepository.findAll(); }

    @Override
    public List<ElectionPositionSummary> getAllByElection(Election election) {
        return electionPositionRepository.findAllByElection(election);
    }

    @Override
    public ElectionPosition getById(UUID uuid) {
        return electionPositionRepository.findById(uuid)
                .orElseThrow(() ->
                        new NotFoundException(localeMessageSource
                                .getMessage("not-found", uuid, ElectionPosition.class.getName())));
    }

    @Override
    public ElectionPosition create(ElectionPosition election) { return electionPositionRepository.save(election); }

    @Override
    public ElectionPosition update(UUID uuid, ElectionPosition electionPosition) {
        ElectionPosition savedElectionPosition = getById(uuid);
        BeanUtils.copyProperties(electionPosition, savedElectionPosition);
        return electionPositionRepository.save(savedElectionPosition);
    }

    @Override
    public void delete(UUID uuid) {
        try {
            electionPositionRepository.deleteById(uuid);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(localeMessageSource
                    .getMessage("not-found", uuid, ElectionPosition.class.getName()));
        } catch (DataIntegrityViolationException e) {
            throw new IntegrityViolationException(e.getMessage());
        }
    }
}
