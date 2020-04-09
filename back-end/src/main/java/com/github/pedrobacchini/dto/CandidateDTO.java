package com.github.pedrobacchini.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
public class CandidateDTO {

    @NotNull
    private String name;

    @NotNull
    private ElectionPosition electionPosition;

    @Getter
    public static class ElectionPosition {

        @NotNull
        private UUID uuid;
    }
}
