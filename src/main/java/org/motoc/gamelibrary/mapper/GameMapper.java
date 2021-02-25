package org.motoc.gamelibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.motoc.gamelibrary.dto.*;
import org.motoc.gamelibrary.model.Category;
import org.motoc.gamelibrary.model.Creator;
import org.motoc.gamelibrary.model.Game;
import org.motoc.gamelibrary.model.Image;
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

    @Mapping(target = "imageIds", source = "images", qualifiedByName = "imageSetToIds")
    GameDto gameToDto(Game game);


    /* ======================================= OVERVIEW METHODS (AND MORE) ========================================= */
    default Page<GameOverviewDto> pageToOverviewDto(Page<Game> page) {
        return page.map(this::gameToOverviewDto);
    }

    @Mapping(target = "imageIds", source = "images", qualifiedByName = "imageSetToIds")
    GameOverviewDto gameToOverviewDto(Game game);

    GameNameAndIdDto gameToNameAndIdDto(Game game);

    CategoryNameAndIdDto categoryToNameAndIdDto(Category category);

    CreatorWithoutContactDto toCreatorWithoutContactDto(Creator creator);

    @Named("imageSetToIds")
    default Set<Long> imageSetToIdSet(Set<Image> images) {
        if (images == null) {
            return null;
        }

        Set<Long> ids = new HashSet<>();
        for (Image image : images) {
            ids.add(image.getId());
        }
        return ids;
    }
}
