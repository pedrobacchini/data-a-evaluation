package com.github.pedrobacchini.resource;

import com.github.pedrobacchini.dto.*;
import com.github.pedrobacchini.entity.Election;
import com.github.pedrobacchini.event.ResourceCreatedEvent;
import com.github.pedrobacchini.mapper.ElectionMapper;
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
    private final ElectionMapper electionMapper;

    @GetMapping(params = "started")
    public List<ElectionStarted> getAllStarted() {
        List<Election> allStarted = electionService.getAllStarted();
        return electionMapper.fromEntityList(allStarted);
    }

    @GetMapping(params = "available")
    public List<ElectionRetrieve> getAllAvailable() {
        List<Election> allAvailable = electionService.getAllAvailable();
        return allAvailable.stream().map(electionMapper::fromEntity).collect(Collectors.toList());
    }

    @GetMapping(params = {"available", "selection"})
    public List<ElectionSelection> getAllAvailableSelection() { return electionService.getAllAvailableSelection(); }

    @GetMapping(params = "selection", value = "/{uuid}/election-positions")
    public List<ElectionPositionSelection> getAllElectionPositionsSelection(@PathVariable("uuid") String uuid) {
        Election election = electionService.getById(UUID.fromString(uuid));
        return electionPositionService.getAllByElection(election);
    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<ElectionRetrieve> getById(@PathVariable("uuid") String uuid) {
        Election election = electionService.getById(UUID.fromString(uuid));
        ElectionRetrieve electionRetrieve = electionMapper.fromEntity(election);
        return ResponseEntity.ok(electionRetrieve);
    }

    @PostMapping
    public ResponseEntity<ElectionRetrieve> create(@RequestBody @Valid ElectionDTO electionDTO, HttpServletResponse response) {
        Election election = electionMapper.fromDTO(electionDTO);
        election = electionService.create(election);
        ElectionRetrieve electionRetrieve = electionMapper.fromEntity(election);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, election.getUuid()));
        return ResponseEntity.status(HttpStatus.CREATED).body(electionRetrieve);
    }

    @PutMapping(value = "/{uuid}")
    public ResponseEntity<ElectionRetrieve> update(@PathVariable("uuid") String uuid, @RequestBody @Valid ElectionDTO electionDTO) {
        Election election = electionMapper.fromDTO(electionDTO);
        election = electionService.update(UUID.fromString(uuid), election);
        ElectionRetrieve electionRetrieve = electionMapper.fromEntity(election);
        return ResponseEntity.ok(electionRetrieve);
    }

    @DeleteMapping(value = "/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable("uuid") String uuid) {
        electionService.delete(UUID.fromString(uuid));
        return ResponseEntity.noContent().build();
    }
}
