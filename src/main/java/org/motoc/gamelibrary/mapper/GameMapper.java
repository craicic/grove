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

    default GameDto gameToDtoOnlyName(Game game) {
        if (game == null) {
            return null;
        }

        GameDto gameDto = new GameDto();

        gameDto.setId(game.getId());
        gameDto.setName(game.getName());

        return gameDto;
    }

    default Game dtoToGameOnlyName(GameDto gameDto) {
        if (gameDto == null) {
            return null;
        }

        Game game = new Game();

        game.setId(gameDto.getId());
        game.setName(gameDto.getName());

        return game;
    }

    default GameDto gameToDto(Game game) {
        if (game == null) {
            return null;
        }

        GameDto gameDto = new GameDto();

        gameDto.setId(game.getId());
        gameDto.setName(game.getName());
        gameDto.setDescription(game.getDescription());
        gameDto.setPlayTime(game.getPlayTime());
        gameDto.setMinNumberOfPlayer(game.getMinNumberOfPlayer());
        gameDto.setMaxNumberOfPlayer(game.getMaxNumberOfPlayer());
        gameDto.setMinAge(game.getMinAge());
        gameDto.setMaxAge(game.getMaxAge());
        gameDto.setMinMonth(game.getMinMonth());
        gameDto.setNature(game.getNature());
        gameDto.setSize(game.getSize());
        gameDto.setEditionNumber(game.getEditionNumber());

        gameDto.setCoreGame(gameToDtoOnlyName(game.getCoreGame()));
        for (Game expansion : game.getExpansions()) {
            gameDto.getExpansions().add(gameToDtoOnlyName(expansion));
        }

        return gameDto;
    }

    default Game dtoToGame(GameDto gameDto) {
        if (gameDto == null) {
            return null;
        }

        Game game = new Game();

        game.setId(gameDto.getId());
        game.setName(gameDto.getName());
        game.setDescription(gameDto.getDescription());
        game.setPlayTime(gameDto.getPlayTime());
        game.setMinNumberOfPlayer(gameDto.getMinNumberOfPlayer());
        game.setMaxNumberOfPlayer(gameDto.getMaxNumberOfPlayer());
        game.setMinAge(gameDto.getMinAge());
        game.setMaxAge(gameDto.getMaxAge());
        game.setMinMonth(gameDto.getMinMonth());
        game.setNature(gameDto.getNature());
        game.setSize(gameDto.getSize());
        game.setEditionNumber(gameDto.getEditionNumber());

        game.setCoreGame(dtoToGameOnlyName(gameDto.getCoreGame()));
        for (GameDto expansion : gameDto.getExpansions()) {
            game.getExpansions().add(dtoToGameOnlyName(expansion));
        }

        return game;
    }

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
