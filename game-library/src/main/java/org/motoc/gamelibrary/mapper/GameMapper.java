package org.motoc.gamelibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.motoc.gamelibrary.domain.dto.*;
import org.motoc.gamelibrary.domain.model.*;
import org.springframework.data.domain.Page;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface GameMapper {

    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    default Page<GameDto> pageToPageDto(Page<Game> page) {
        return page.map(this::gameToDto);
    }

    /* ======================================= GAME DTO METHODS (AND MORE) ========================================= */
    @Mapping(target = "images", ignore = true)
    Game dtoToGame(GameDto dto);

    @Mappings(
            {
                    @Mapping(target = "imageIds", source = "images", qualifiedByName = "imageSetToIds"),
                    @Mapping(target = "copies", source = "gameCopies")
            })
    GameDto gameToDto(Game game);

    GameCopyIdAndCodeDto copyToIdAndCodeDto(GameCopy gameCopy);

    /* ======================================= OVERVIEW METHODS (AND MORE) ========================================= */
    default Page<GameOverviewDto> pageToOverviewDto(Page<Game> page) {
        return page.map(this::gameToOverviewDto);
    }

    @Mapping(target = "imageIds", source = "images", qualifiedByName = "imageSetToIds")
    GameOverviewDto gameToOverviewDto(Game game);

    GameTitleAndIdDto gameToTitleAndIdDto(Game game);


    CategoryDto categoryToDto(Category category);

    CreatorWithoutContactDto toCreatorWithoutContactDto(Creator creator);

    @Named("imageSetToIds")
    default Set<Long> imageSetToIdSet(Set<Image> images) {
        if (images.isEmpty()) {
            return null;
        }

        Set<Long> ids = new HashSet<>();
        for (Image image : images) {
            ids.add(image.getId());
        }
        return ids;
    }
}
