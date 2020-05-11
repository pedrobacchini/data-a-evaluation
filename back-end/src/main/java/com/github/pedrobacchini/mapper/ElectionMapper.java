package com.github.pedrobacchini.mapper;

import com.github.pedrobacchini.dto.ElectionDTO;
import com.github.pedrobacchini.dto.ElectionRetrieve;
import com.github.pedrobacchini.dto.ElectionStarted;
import com.github.pedrobacchini.entity.Election;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ElectionMapper {

    Election fromDTO(final ElectionDTO electionDTO);

    ElectionRetrieve fromEntity(Election election);

    List<ElectionStarted> fromEntityList(List<Election> elections);

    @AfterMapping
    default void after(@MappingTarget final Election.ElectionBuilder electionBuilder) {
        System.out.println("AfterMapping");
    }
}
