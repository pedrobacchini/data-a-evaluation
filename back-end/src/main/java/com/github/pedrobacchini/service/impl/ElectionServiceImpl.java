package com.github.pedrobacchini.service.impl;

import com.github.pedrobacchini.config.LocaleMessageSource;
import com.github.pedrobacchini.entity.Election;
import com.github.pedrobacchini.exception.IntegrityViolationException;
import com.github.pedrobacchini.exception.NotFoundException;
import com.github.pedrobacchini.repository.ElectionRepository;
import com.github.pedrobacchini.service.ElectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository electionRepository;
    private final LocaleMessageSource localeMessageSource;

    @Override
    public List<Election> getAll() { return electionRepository.findAll(); }

    @Override
    public Election getById(UUID uuid) {
        return electionRepository.findById(uuid)
                .orElseThrow(() ->
                        new NotFoundException(localeMessageSource
                                .getMessage("not-found", uuid, Election.class.getName())));
    }

    @Override
    public Election create(Election election) { return electionRepository.save(election); }

    @Override
    public Election update(UUID uuid, Election election) {
        try {
            Election savedElection = getById(uuid);
            savedElection.updateData(election);
            return electionRepository.save(savedElection);
        } catch (DataIntegrityViolationException e) {
            throw new IntegrityViolationException(localeMessageSource
                    .getMessage("not-delete-election-position-with-candidate"));
        }
    }

    @Override
    public void delete(UUID uuid) {
        try {
            electionRepository.deleteById(uuid);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(localeMessageSource
                    .getMessage("not-found", uuid, Election.class.getName()));
        } catch (DataIntegrityViolationException e) {
            throw new IntegrityViolationException(localeMessageSource
                    .getMessage("not-delete-election-with-candidate"));
        }
    }
}
