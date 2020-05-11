package com.github.pedrobacchini.mapper;

import com.github.pedrobacchini.dto.CandidateDTO;
import com.github.pedrobacchini.dto.CandidateRetrieve;
import com.github.pedrobacchini.entity.Candidate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CandidateMapper {

    Candidate fromDTO(CandidateDTO candidateDTO);

    CandidateRetrieve fromEntity(Candidate candidate);
}
