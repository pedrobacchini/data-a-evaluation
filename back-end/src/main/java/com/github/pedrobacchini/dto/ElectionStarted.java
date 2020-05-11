package com.github.pedrobacchini.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class ElectionStarted {

    UUID uuid;

    String name;

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate startDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate finishDate;
}
