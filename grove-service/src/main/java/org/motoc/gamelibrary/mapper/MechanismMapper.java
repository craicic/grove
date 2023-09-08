package org.motoc.gamelibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.motoc.gamelibrary.domain.dto.MechanismDto;
import org.motoc.gamelibrary.domain.dto.MechanismNameDto;
import org.motoc.gamelibrary.domain.model.Mechanism;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Maps model to dto and and dto to model
 */
@Mapper(componentModel = "spring")
public interface MechanismMapper {

    MechanismMapper INSTANCE = Mappers.getMapper(MechanismMapper.class);

    default Page<MechanismDto> pageToPageDto(Page<Mechanism> mechanismPage) {
        return mechanismPage.map(this::mechanismToDto);
    }

    List<MechanismDto> mechanismsToDto(List<Mechanism> mechanisms);

    MechanismDto mechanismToDto(Mechanism mechanism);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lowerCaseTitle", ignore = true)
    @Mapping(target = "games", ignore = true)
    Mechanism mechanismNameDtoToMechanism(MechanismNameDto mechanismNameDto);

    @Mapping(target = "games", ignore = true)
    @Mapping(target = "lowerCaseTitle", ignore = true)
    Mechanism dtoToMechanism(MechanismDto mechanism);
}
