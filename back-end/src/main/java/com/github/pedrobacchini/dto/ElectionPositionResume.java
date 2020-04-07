package com.github.pedrobacchini.dto;

import com.github.pedrobacchini.entity.ElectionPosition;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ElectionPositionResume {

    private UUID uuid;

    private String name;

    public ElectionPositionResume(ElectionPosition electionPosition) {
        this.uuid = electionPosition.getUuid();
        this.name = electionPosition.getName();
    }
}
