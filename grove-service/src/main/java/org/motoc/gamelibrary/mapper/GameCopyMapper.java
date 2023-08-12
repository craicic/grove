package org.motoc.gamelibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.motoc.gamelibrary.domain.dto.GameCopyDto;
import org.motoc.gamelibrary.domain.dto.PublisherDto;
import org.motoc.gamelibrary.domain.model.GameCopy;
import org.motoc.gamelibrary.domain.model.Publisher;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GameCopyMapper {

    GameCopyMapper INSTANCE = Mappers.getMapper(GameCopyMapper.class);

    default Page<GameCopyDto> pageToPageDto(Page<GameCopy> page) {
        return page.map(this::copyToDto);
    }

    @Mapping(target = "game", ignore = true)
    @Mapping(target = "preReservations", ignore = true)
    @Mapping(target = "gameCopyReservations", ignore = true)
    GameCopy dtoToCopy(GameCopyDto dto);

    @Mapping(source = "game.id", target = "gameId")
    @Mapping(source = "game.title", target = "gameTitle")
    GameCopyDto copyToDto(GameCopy copy);

    List<GameCopyDto> copiesToDto(List<GameCopy> copy);

    @Mapping(target = "lowerCaseName", ignore = true)
    @Mapping(target = "copies", ignore = true)
    Publisher dtoToPublisher(PublisherDto publisher);
}
