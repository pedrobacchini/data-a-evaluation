package com.github.pedrobacchini.dto;

import lombok.Getter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
public class ElectionDTO {

    @NotEmpty
    private String name;

    @NotNull
    @Future
    private LocalDate startDate;

    @NotNull
    @Future
    private LocalDate finishDate;
}
