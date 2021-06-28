package org.motoc.gamelibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.motoc.gamelibrary.dto.GameCopyDto;
import org.motoc.gamelibrary.model.GameCopy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GameCopyMapper {

    GameCopyMapper INSTANCE = Mappers.getMapper(GameCopyMapper.class);

    default Page<GameCopyDto> pageToPageDto(Page<GameCopy> page) {
        return page.map(this::copyToDto);
    }


    GameCopy dtoToCopy(GameCopyDto dto);

    @Mapping(source = "game.id", target = "gameId")
    @Mapping(source = "game.name", target = "gameName")
    GameCopyDto copyToDto(GameCopy copy);

    List<GameCopyDto> copiesToDto(List<GameCopy> copy);
}
