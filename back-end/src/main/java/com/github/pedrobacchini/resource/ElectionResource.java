package com.github.pedrobacchini.resource;

import com.github.pedrobacchini.dto.ElectionDTO;
import com.github.pedrobacchini.entity.Election;
import com.github.pedrobacchini.event.ResourceCreatedEvent;
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

    @GetMapping
    public List<Election> getAll() { return electionService.getAll(); }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<Election> getById(@PathVariable("uuid") String uuid) {
        Election client = electionService.getById(UUID.fromString(uuid));
        return ResponseEntity.ok(client);
    }

    @PostMapping
    public ResponseEntity<Election> create(@RequestBody @Valid ElectionDTO electionDTO, HttpServletResponse response) {
        Election createdClient = electionService.create(fromDTO(electionDTO));
        publisher.publishEvent(new ResourceCreatedEvent(this, response, createdClient.getUuid()));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
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
        return new Election(electionDTO.getName(), electionDTO.getStartDate(), electionDTO.getFinishDate());
    }
}
