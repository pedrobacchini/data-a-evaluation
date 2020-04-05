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

        Election eleicoes2020 = new Election("Eleições 2020",
                LocalDate.of(2020, 10, 4), LocalDate.of(2020, 10, 25));

        Election eleicoeslideres = new Election("Eleições Lideres",
                LocalDate.of(2020, 3, 31), LocalDate.of(2020, 4, 20));

        electionRepository.saveAll(Arrays.asList(eleicoes2020, eleicoeslideres));

        ElectionPosition prefeito = new ElectionPosition("Prefeito", eleicoes2020);
        ElectionPosition vereador = new ElectionPosition("Vereador", eleicoes2020);

        ElectionPosition programadorLider = new ElectionPosition("Programador Lider", eleicoeslideres);
        ElectionPosition artistaLider = new ElectionPosition("Artista Lider", eleicoeslideres);

        electionPositionRepository.saveAll(Arrays.asList(prefeito, vereador, programadorLider, artistaLider));

        Candidate luiz = new Candidate("Luiz de souza", prefeito);
        Candidate joao = new Candidate("João Ferreira", prefeito);

        Candidate fernando = new Candidate("Fernado Paulo", vereador);
        Candidate diego = new Candidate("Diego Paulo", vereador);
        Candidate maria = new Candidate("Maria Dores", vereador);

        Candidate pedro = new Candidate("Pedro Bacchini", programadorLider);
        Candidate henrique = new Candidate("Henrique Jose", programadorLider);

        Candidate paulo = new Candidate("Paulo Cezar", artistaLider);
        Candidate leonardo = new Candidate("Leonardo Medeiros", artistaLider);

        candidateRepository.saveAll(Arrays.asList(luiz, joao, fernando, diego, maria, pedro, henrique, paulo, leonardo));

        return true;
    }
}
