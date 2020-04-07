package com.github.pedrobacchini.resource;

import com.github.pedrobacchini.dto.ElectionDTO;
import com.github.pedrobacchini.dto.ElectionPositionResume;
import com.github.pedrobacchini.dto.ElectionResume;
import com.github.pedrobacchini.entity.Election;
import com.github.pedrobacchini.entity.ElectionPosition;
import com.github.pedrobacchini.event.ResourceCreatedEvent;
import com.github.pedrobacchini.service.ElectionPositionService;
import com.github.pedrobacchini.service.ElectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/elections")
public class ElectionResource {

    private final ElectionService electionService;
    private final ApplicationEventPublisher publisher;
    private final ElectionPositionService electionPositionService;

    @GetMapping(params = "started")
    public List<Election> getAllStarted() { return electionService.getAllStarted(); }

    @GetMapping(params = "available")
    public List<Election> getAllAvailable() { return electionService.getAllAvailable(); }

    @GetMapping(params = {"available", "resume"})
    public List<ElectionResume> getAllAvailableResume() {
        return electionService.getAllAvailable().stream().map(ElectionResume::new).collect(Collectors.toList());
    }

    @GetMapping(params = "resume", value = "/{uuid}/election-positions")
    public List<ElectionPositionResume> getAllElectionPositionsResume(@PathVariable("uuid") String uuid) {
        Election election = electionService.getById(UUID.fromString(uuid));
        return electionPositionService.getAllByElection(election).stream()
                .map(ElectionPositionResume::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<Election> getById(@PathVariable("uuid") String uuid) {
        Election election = electionService.getById(UUID.fromString(uuid));
        return ResponseEntity.ok(election);
    }

    @PostMapping
    public ResponseEntity<Election> create(@RequestBody @Valid ElectionDTO electionDTO, HttpServletResponse response) {
        Election createdElection = electionService.create(fromDTO(electionDTO));
        publisher.publishEvent(new ResourceCreatedEvent(this, response, createdElection.getUuid()));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdElection);
    }

    @PutMapping(value = "/{uuid}")
    public ResponseEntity<Election> update(@PathVariable("uuid") String uuid, @RequestBody @Valid ElectionDTO electionDTO) {
        Election election = electionService.update(UUID.fromString(uuid), fromDTO(electionDTO));
        return ResponseEntity.ok(election);
    }

    @DeleteMapping(value = "/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable("uuid") String uuid) {
        electionService.delete(UUID.fromString(uuid));
        return ResponseEntity.noContent().build();
    }

    private Election fromDTO(ElectionDTO electionDTO) {
        Election election = new Election(electionDTO.getName(), electionDTO.getStartDate(), electionDTO.getFinishDate());
        List<ElectionPosition> electionPositions = electionDTO.getElectionPositions().stream()
                .map(electionPositionDTO -> new ElectionPosition(electionPositionDTO.getUuid(), electionPositionDTO.getName()))
                .collect(Collectors.toList());
        election.addAllElectionPositions(electionPositions);
        return election;
    }
}
