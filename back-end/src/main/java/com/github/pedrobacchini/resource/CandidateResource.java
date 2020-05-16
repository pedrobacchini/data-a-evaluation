package com.github.pedrobacchini.resource;

import com.github.pedrobacchini.dto.CandidateDTO;
import com.github.pedrobacchini.dto.CandidateRetrieve;
import com.github.pedrobacchini.entity.Candidate;
import com.github.pedrobacchini.event.ResourceCreatedEvent;
import com.github.pedrobacchini.mapper.CandidateMapper;
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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/candidates")
public class CandidateResource {

    private final CandidateService candidateService;
    private final ApplicationEventPublisher publisher;
    private final CandidateMapper candidateMapper;

    @GetMapping
    public List<CandidateRetrieve> getAll() {
        List<Candidate> candidates = candidateService.getAll();
        return candidates.stream().map(candidateMapper::fromEntity).collect(Collectors.toList());
    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<CandidateRetrieve> getById(@PathVariable("uuid") String uuid) {
        Candidate candidate = candidateService.getById(UUID.fromString(uuid));
        CandidateRetrieve candidateRetrieve = candidateMapper.fromEntity(candidate);
        return ResponseEntity.ok(candidateRetrieve);
    }

    @PostMapping
    public ResponseEntity<CandidateRetrieve> create(@RequestBody @Valid CandidateDTO candidateDTO,
                                                    HttpServletResponse response) {
        Candidate candidate = candidateMapper.fromDTO(candidateDTO);
        candidate = candidateService.create(candidate);
        CandidateRetrieve candidateRetrieve = candidateMapper.fromEntity(candidate);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, candidate.getUuid()));
        return ResponseEntity.status(HttpStatus.CREATED).body(candidateRetrieve);
    }

    @PutMapping(value = "/{uuid}")
    public ResponseEntity<CandidateRetrieve> update(@PathVariable("uuid") String uuid,
                                                    @RequestBody @Valid CandidateDTO candidateDTO) {
        Candidate candidate = candidateMapper.fromDTO(candidateDTO);
        candidate = candidateService.update(UUID.fromString(uuid), candidate);
        CandidateRetrieve candidateRetrieve = candidateMapper.fromEntity(candidate);
        return ResponseEntity.ok(candidateRetrieve);
    }

    @DeleteMapping(value = "/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable("uuid") String uuid) {
        candidateService.delete(UUID.fromString(uuid));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{uuid}/picture")
    public ResponseEntity<Void> uploadProfilePicture(@PathVariable("uuid") String uuid,
                                                     @RequestParam(name = "file") MultipartFile file)
            throws IOException, URISyntaxException {
        Candidate candidate = candidateService.getById(UUID.fromString(uuid));
        String path = candidateService.uploadProfilePicture(candidate, file);
        return ResponseEntity.created(new URI(path)).build();
    }
}
