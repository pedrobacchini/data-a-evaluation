package com.github.pedrobacchini.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class ElectionRetrieve {

    UUID uuid;

    String name;

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate startDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate finishDate;

    List<ElectionRetrieveElectionPosition> electionPositions;

    @Data
    public static class ElectionRetrieveElectionPosition {

        UUID uuid;

        String name;

        List<ElectionRetrieveCandidate> candidates;

        @Data
        public static class ElectionRetrieveCandidate {

            UUID uuid;

            String name;

            String picture;
        }
    }
}
