package com.github.pedrobacchini.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
public class ElectionPositionDTO {

    @NotNull
    private String name;

    @NotNull
    private UUID electionUuid;
}
