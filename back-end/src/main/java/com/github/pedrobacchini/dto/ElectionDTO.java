package com.github.pedrobacchini.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pedrobacchini.constraint.FromDataBeforeToDate;
import lombok.Getter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@FromDataBeforeToDate(fromDate = "startDate", toDate = "finishDate")
public class ElectionDTO {

    @NotEmpty
    private String name;

    @NotNull
    @Future
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;

    @NotNull
    @Future
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate finishDate;
}
