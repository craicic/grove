package org.motoc.gamelibrary.repository.criteria;

import org.motoc.gamelibrary.dto.GameNameDto;
import org.motoc.gamelibrary.model.Game;

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
}
