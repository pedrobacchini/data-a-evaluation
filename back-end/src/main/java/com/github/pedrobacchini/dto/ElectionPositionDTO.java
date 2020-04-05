package com.github.pedrobacchini.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ElectionPositionDTO {

    @NotNull
    private String name;
}
