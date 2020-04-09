package com.github.pedrobacchini.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.pedrobacchini.dto.CandidateDTO;
import com.github.pedrobacchini.entity.Candidate;
import com.github.pedrobacchini.entity.ElectionPosition;
import com.github.pedrobacchini.event.ResourceCreatedEvent;
import com.github.pedrobacchini.json.View;
import com.github.pedrobacchini.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/candidates")
public class CandidateResource {

    private final CandidateService candidateService;
    private final ApplicationEventPublisher publisher;

    @GetMapping
    @JsonView(View.Candidate.class)
    public List<Candidate> getAll() { return candidateService.getAll(); }

    @GetMapping(path = "/{uuid}")
    @JsonView(View.Candidate.class)
    public ResponseEntity<Candidate> getById(@PathVariable("uuid") String uuid) {
        Candidate candidate = candidateService.getById(UUID.fromString(uuid));
        return ResponseEntity.ok(candidate);
    }

    @PostMapping
    @JsonView(View.Candidate.class)
    public ResponseEntity<Candidate> create(@RequestBody @Valid CandidateDTO candidateDTO, HttpServletResponse response) {
        Candidate createdCandidate = candidateService.create(fromDTO(candidateDTO));
        publisher.publishEvent(new ResourceCreatedEvent(this, response, createdCandidate.getUuid()));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCandidate);
    }

    @PutMapping(value = "/{uuid}")
    @JsonView(View.Candidate.class)
    public ResponseEntity<Candidate> update(@PathVariable("uuid") String uuid, @RequestBody @Valid CandidateDTO candidateDTO) {
        Candidate candidate = candidateService.update(UUID.fromString(uuid), fromDTO(candidateDTO));
        return ResponseEntity.ok(candidate);
    }

    @DeleteMapping(value = "/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable("uuid") String uuid) {
        candidateService.delete(UUID.fromString(uuid));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{uuid}/picture")
    public ResponseEntity<Void> uploadProfilePicture(@PathVariable("uuid") String uuid,
                                                     @RequestParam(name = "file") MultipartFile file) throws IOException, URISyntaxException {
        Candidate candidate = candidateService.getById(UUID.fromString(uuid));
        String path = candidateService.uploadProfilePicture(candidate, file);
        return ResponseEntity.created(new URI(path)).build();
    }

    private Candidate fromDTO(CandidateDTO candidateDTO) {
        ElectionPosition electionPosition = new ElectionPosition(candidateDTO.getElectionPosition().getUuid());
        return new Candidate(candidateDTO.getName(), electionPosition);
    }
}
