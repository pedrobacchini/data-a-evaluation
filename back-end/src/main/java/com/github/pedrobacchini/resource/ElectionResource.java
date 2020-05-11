package com.github.pedrobacchini.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.pedrobacchini.dto.ElectionDTO;
import com.github.pedrobacchini.dto.ElectionPositionSelection;
import com.github.pedrobacchini.dto.ElectionStarted;
import com.github.pedrobacchini.dto.ElectionSelection;
import com.github.pedrobacchini.entity.Election;
import com.github.pedrobacchini.event.ResourceCreatedEvent;
import com.github.pedrobacchini.json.View;
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
    @JsonView(View.Election.class)
    public List<Election> getAllAvailable() { return electionService.getAllAvailable(); }

    @GetMapping(params = {"available", "selection"})
    public List<ElectionSelection> getAllAvailableSelection() { return electionService.getAllAvailableSelection(); }

    @GetMapping(params = "selection", value = "/{uuid}/election-positions")
    public List<ElectionPositionSelection> getAllElectionPositionsSelection(@PathVariable("uuid") String uuid) {
        Election election = electionService.getById(UUID.fromString(uuid));
        return electionPositionService.getAllByElection(election);
    }

    @GetMapping(path = "/{uuid}")
    @JsonView(View.Election.class)
    public ResponseEntity<Election> getById(@PathVariable("uuid") String uuid) {
        Election election = electionService.getById(UUID.fromString(uuid));
        return ResponseEntity.ok(election);
    }

    @PostMapping
    @JsonView(View.Election.class)
    public ResponseEntity<Election> create(@RequestBody @Valid ElectionDTO electionDTO, HttpServletResponse response) {
        Election election = electionMapper.fromDTO(electionDTO);
        election = electionService.create(election);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, election.getUuid()));
        return ResponseEntity.status(HttpStatus.CREATED).body(election);
    }

    @PutMapping(value = "/{uuid}")
    @JsonView(View.Election.class)
    public ResponseEntity<Election> update(@PathVariable("uuid") String uuid, @RequestBody @Valid ElectionDTO electionDTO) {
        Election election = electionMapper.fromDTO(electionDTO);
        election = electionService.update(UUID.fromString(uuid), election);
        return ResponseEntity.ok(election);
    }

    @DeleteMapping(value = "/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable("uuid") String uuid) {
        electionService.delete(UUID.fromString(uuid));
        return ResponseEntity.noContent().build();
    }
}
