package com.github.pedrobacchini.service;

import com.github.pedrobacchini.entity.Candidate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface CandidateService {

    List<Candidate> getAll();

    Candidate getById(UUID uuid);

    Candidate create(Candidate Candidate);

    Candidate update(UUID uuid, Candidate Candidate);

    void delete(UUID uuid);

    String uploadProfilePicture(Candidate candidate, MultipartFile multipartFile) throws IOException;
}
