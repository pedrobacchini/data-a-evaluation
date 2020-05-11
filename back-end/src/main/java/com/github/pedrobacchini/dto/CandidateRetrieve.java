package com.github.pedrobacchini.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CandidateRetrieve {

    UUID uuid;

    String name;

    CandidateRetrieveElectionPosition electionPosition;

    String picture;

    @Data
    public static class CandidateRetrieveElectionPosition {

        UUID uuid;

        String name;

        CandidateRetrieveElection election;

        @Data
        public static class CandidateRetrieveElection {

            UUID uuid;

            String name;

            LocalDate startDate;

            LocalDate finishDate;
        }
    }
}
