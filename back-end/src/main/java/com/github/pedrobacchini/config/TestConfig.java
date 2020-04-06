package com.github.pedrobacchini.config;

import com.github.pedrobacchini.entity.Candidate;
import com.github.pedrobacchini.entity.Election;
import com.github.pedrobacchini.entity.ElectionPosition;
import com.github.pedrobacchini.repository.CandidateRepository;
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
    private final CandidateRepository candidateRepository;

    @Bean
    public boolean instantiateDatabase() {


        ElectionPosition prefeito = new ElectionPosition("Prefeito");
        ElectionPosition vereador = new ElectionPosition("Vereador");

        ElectionPosition programadorLider = new ElectionPosition("Programador Lider");
        ElectionPosition artistaLider = new ElectionPosition("Artista Lider");

        ElectionPosition sindico = new ElectionPosition("Sindico");
        ElectionPosition subsindico = new ElectionPosition("Sub-Sindico");

        Election eleicoes2020 = new Election("Eleições 2020",
                LocalDate.of(2020, 10, 4), LocalDate.of(2020, 10, 25));
        eleicoes2020.addAllElectionPositions(Arrays.asList(prefeito, vereador));

        Election eleicoeslideres = new Election("Eleições Lideres",
                LocalDate.of(2020, 3, 31), LocalDate.of(2020, 4, 20));
        eleicoeslideres.addAllElectionPositions(Arrays.asList(programadorLider, artistaLider));

        Election eleicaoCodomino = new Election("Eleioções Condominio 2019",
                LocalDate.of(2019, 1, 12), LocalDate.of(2019, 3, 1));
        eleicaoCodomino.addAllElectionPositions(Arrays.asList(sindico, subsindico));

        electionRepository.saveAll(Arrays.asList(eleicoes2020, eleicoeslideres, eleicaoCodomino));

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
