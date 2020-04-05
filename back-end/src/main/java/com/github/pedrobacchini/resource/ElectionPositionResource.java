package com.github.pedrobacchini.resource;

import com.github.pedrobacchini.dto.ElectionPositionDTO;
import com.github.pedrobacchini.entity.Election;
import com.github.pedrobacchini.entity.ElectionPosition;
import com.github.pedrobacchini.event.ResourceCreatedEvent;
import com.github.pedrobacchini.service.ElectionPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/election-positions")
public class ElectionPositionResource {

    private final ElectionPositionService electionPositionService;
    private final ApplicationEventPublisher publisher;

    @GetMapping
    public List<ElectionPosition> getAll() { return electionPositionService.getAll(); }

    @PostMapping
    public ResponseEntity<ElectionPosition> create(@RequestBody @Valid ElectionPositionDTO electionPositionDTO, HttpServletResponse response) {
        ElectionPosition createdElectionPosition = electionPositionService.create(fromDTO(electionPositionDTO));
        publisher.publishEvent(new ResourceCreatedEvent(this, response, createdElectionPosition.getUuid()));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdElectionPosition);
    }

    private ElectionPosition fromDTO(ElectionPositionDTO electionPositionDTO) {
        Election election = new Election(electionPositionDTO.getElectionUuid());
        return new ElectionPosition(electionPositionDTO.getName(), election);
    }
}
