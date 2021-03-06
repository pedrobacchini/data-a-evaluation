package com.github.pedrobacchini.config;

import com.github.pedrobacchini.entity.Candidate;
import com.github.pedrobacchini.entity.Election;
import com.github.pedrobacchini.entity.ElectionPosition;
import com.github.pedrobacchini.repository.CandidateRepository;
import com.github.pedrobacchini.repository.ElectionRepository;
import com.github.pedrobacchini.service.CandidateService;
import com.github.pedrobacchini.service.ImageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig {

    @Bean
    CommandLineRunner instantiateDatabase(final ImageService imageService,
                                          final ElectionRepository electionRepository,
                                          final CandidateRepository candidateRepository,
                                          final CandidateService candidateService) {

        return args -> {
            ElectionPosition prefeito = new ElectionPosition("Prefeito");
            ElectionPosition vereador = new ElectionPosition("Vereador");

            ElectionPosition programadorLider = new ElectionPosition("Programador Lider");
            ElectionPosition artistaLider = new ElectionPosition("Artista Lider");

            ElectionPosition sindico = new ElectionPosition("Sindico");
            ElectionPosition subsindico = new ElectionPosition("Sub-Sindico");

            Election eleicoes2020 = Election.builder()
                    .name("Eleições 2020")
                    .startDate(LocalDate.of(2020, 10, 4))
                    .finishDate(LocalDate.of(2020, 10, 25))
                    .electionPosition(prefeito)
                    .electionPosition(vereador)
                    .build();

            Election eleicoeslideres = Election.builder()
                    .name("Eleições Lideres")
                    .startDate(LocalDate.of(2020, 3, 31))
                    .finishDate(LocalDate.of(2020, 4, 20))
                    .electionPosition(programadorLider)
                    .electionPosition(artistaLider)
                    .build();

            Election eleicaoCodomino = Election.builder()
                    .name("Eleições Condominio 2019")
                    .startDate(LocalDate.of(2019, 1, 12))
                    .finishDate(LocalDate.of(2019, 3, 1))
                    .electionPosition(sindico)
                    .electionPosition(subsindico)
                    .build();

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

            MultipartFile luizPicture = imageService.getMultipartFile("back-end/src/main/resources/static/01.jpg");
            candidateService.uploadProfilePicture(luiz, luizPicture);

            MultipartFile joaoPicture = imageService.getMultipartFile("back-end/src/main/resources/static/02.jpg");
            candidateService.uploadProfilePicture(joao, joaoPicture);
        };
    }
}
