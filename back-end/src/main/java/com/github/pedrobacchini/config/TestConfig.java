package com.github.pedrobacchini.config;

import com.github.pedrobacchini.entity.Candidate;
import com.github.pedrobacchini.entity.Election;
import com.github.pedrobacchini.entity.ElectionPosition;
import com.github.pedrobacchini.repository.CandidateRepository;
import com.github.pedrobacchini.repository.ElectionPositionRepository;
import com.github.pedrobacchini.repository.ElectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
@Profile("test")
@RequiredArgsConstructor
public class TestConfig {

    private final ElectionRepository electionRepository;
    private final ElectionPositionRepository electionPositionRepository;
    private final CandidateRepository candidateRepository;

    @Bean
    public boolean instantiateDatabase() {

        Election election2020 = new Election("Elections 2020",
                LocalDate.of(2020, 10, 4), LocalDate.of(2020, 10, 25));

        Election electionLeadProgrammer = new Election("Elections Lead Programmer",
                LocalDate.of(2020, 3, 31), LocalDate.of(2020, 4, 20));

        electionRepository.saveAll(Arrays.asList(election2020, electionLeadProgrammer));

        ElectionPosition president = new ElectionPosition("President");

        ElectionPosition leader = new ElectionPosition("Leader");

        electionPositionRepository.saveAll(Arrays.asList(president, leader));

        Candidate luiz = new Candidate("Luiz de souza", president);

        Candidate pedro = new Candidate("Pedro Bacchini", leader);

        candidateRepository.saveAll(Arrays.asList(luiz, pedro));

        return true;
    }
}
