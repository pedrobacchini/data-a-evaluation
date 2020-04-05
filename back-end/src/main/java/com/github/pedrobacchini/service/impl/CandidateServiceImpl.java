package com.github.pedrobacchini.service.impl;

import com.github.pedrobacchini.config.LocaleMessageSource;
import com.github.pedrobacchini.entity.Candidate;
import com.github.pedrobacchini.exception.IntegrityViolationException;
import com.github.pedrobacchini.exception.NotFoundException;
import com.github.pedrobacchini.repository.CandidateRepository;
import com.github.pedrobacchini.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final LocaleMessageSource localeMessageSource;

    @Override
    public List<Candidate> getAll() { return candidateRepository.findAll(); }

    @Override
    public Candidate getById(UUID uuid) {
        return candidateRepository.findById(uuid)
                .orElseThrow(() ->
                        new NotFoundException(localeMessageSource
                                .getMessage("not-found", uuid, Candidate.class.getName())));
    }

    @Override
    public Candidate create(Candidate Candidate) { return candidateRepository.save(Candidate); }

    @Override
    public Candidate update(UUID uuid, Candidate candidate) {
        Candidate savedCandidate = getById(uuid);
        BeanUtils.copyProperties(candidate, savedCandidate);
        return candidateRepository.save(savedCandidate);
    }

    @Override
    public void delete(UUID uuid) {
        try {
            candidateRepository.deleteById(uuid);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(localeMessageSource
                    .getMessage("not-found", uuid, Candidate.class.getName()));
        } catch (DataIntegrityViolationException e) {
            throw new IntegrityViolationException(e.getMessage());
        }
    }
}
