package org.motoc.gamelibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.motoc.gamelibrary.dto.GameCopyDto;
import org.motoc.gamelibrary.model.GameCopy;

@Mapper(componentModel = "spring")
public interface GameCopyMapper {

    GameCopyMapper INSTANCE = Mappers.getMapper(GameCopyMapper.class);

    GameCopy dtoToCopy(GameCopyDto dto);

    GameCopyDto copyToDto(GameCopy copy);
}
