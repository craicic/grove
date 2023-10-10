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
    @Mapping(target = "lowerCaseTitle", ignore = true)
    @Mapping(target = "gameCopies", ignore = true)
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

    @Mapping(target = "imageIds", ignore = true)
//            source = "images", qualifiedByName = "imageSetToIds")
    @Mapping(target = "gameCopyCount", ignore = true)
    GameOverviewDto gameToOverviewDto(Game game);

    GameTitleAndIdDto gameToTitleAndIdDto(Game game);

    CategoryDto categoryToDto(Category category);

    @Mapping(target = "games", ignore = true)
    @Mapping(target = "lowerCaseTitle", ignore = true)
    Category dtoToCategory(CategoryDto categoryDto);

    @Mapping(target = "games", ignore = true)
    @Mapping(target = "lowerCaseTitle", ignore = true)
    Mechanism dtoToMechanism(MechanismDto mechanismDto);

    CreatorWithoutContactDto toCreatorWithoutContactDto(Creator creator);

    @Mapping(target = "games", ignore = true)
    @Mapping(target = "contact", ignore = true)
    @Mapping(target = "lowerCaseFirstName", ignore = true)
    @Mapping(target = "lowerCaseLastName", ignore = true)
    Creator dtoToCreator(CreatorWithoutContactDto creatorWithoutContactDto);

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
