package org.motoc.gamelibrary.repository.fragment;

import org.motoc.gamelibrary.domain.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Game custom repository, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
public interface GameFragmentRepository {

    /**
     * Finds an overview of {@link Game} entities based on a search keyword.
     * The overview includes the game's categories, images, game copies and creators.
     *
     * @param keyword  The search keyword to use for filtering the games
     * @param pageable The pagination information for the result set
     * @return A {@link Page} of {@link Game} entities that match the search keyword
     */
    Page<Game> findGamesByKeyword(String keyword, Pageable pageable);

    /**
     * Get all Games' name in a custom DTO
     */
    List<String> findNames();

    Game addExpansions(Game game, @NotNull List<Game> expansions);

    Game addCoreGame(Game game, @NotNull Game coreGame);

    void removeCoreGame(Game game);

    void removeExpansion(Game game, Game expansion);

    Game addCategory(Game game, Category category);

    Game removeCategory(Game game, Category category);


    Game addTheme(Game game, Theme theme);

    Game removeTheme(Game game, Theme theme);


    Game addGameCopy(Game game, GameCopy gameCopy);

    void removeGameCopy(Game game, GameCopy gameCopy);


    Game addCreator(Game game, Creator creator);

    Game removeCreator(Game game, Creator creator);

    void attachImage(Game game, Image image);

    Game addExpansion(Game game, Game expansion);

    Game addProductLine(Game game, ProductLine productLine);

    Game removeProductLine(Game game, ProductLine productLine);
}
