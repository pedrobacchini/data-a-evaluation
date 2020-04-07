package com.github.pedrobacchini.dto;

import com.github.pedrobacchini.entity.Election;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ElectionResume {

    private UUID uuid;

    private String name;

    public ElectionResume(Election election) {
        this.uuid = election.getUuid();
        this.name = election.getName();
    }
}
