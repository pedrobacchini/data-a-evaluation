package com.github.pedrobacchini.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
public class ElectionPositionDTO {

    private UUID uuid;

    @NotNull
    private String name;
}
