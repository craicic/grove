package org.motoc.gamelibrary.repository.criteria;

import org.motoc.gamelibrary.dto.GameNameDto;
import org.motoc.gamelibrary.model.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Game custom repository, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 *
 * @author RouzicJ
 */
public interface GameRepositoryCustom {

    /**
     * Get all Games' name in a custom DTO
     */
    List<GameNameDto> findNames();

    Game addExpansions(Game game, @NotNull List<Game> expansions);

    Game addCoreGame(Game game, @NotNull Game coreGame);

    void removeCoreGame(Game game);

    void removeExpansion(Game game, Game expansion);

    Game addCategory(Game game, Category category);

    void removeCategory(Game game, Category category);


    Game addTheme(Game game, Theme theme);

    void removeTheme(Game game, Theme theme);


    Game addGameCopy(Game game, GameCopy gameCopy);

    void removeGameCopy(Game game, GameCopy gameCopy);


    Game addCreator(Game game, Creator creator);

    void removeCreator(Game game, Creator creator);


    Game addPublisher(Game game, Publisher publisher);

    void removePublisher(Game game, Publisher publisher);
}
