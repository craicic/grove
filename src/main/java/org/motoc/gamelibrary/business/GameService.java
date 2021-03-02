package org.motoc.gamelibrary.business;

import org.motoc.gamelibrary.business.refactor.SimpleCrudMethodsImpl;
import org.motoc.gamelibrary.dto.GameNameDto;
import org.motoc.gamelibrary.model.Game;
import org.motoc.gamelibrary.repository.criteria.GameRepositoryCustom;
import org.motoc.gamelibrary.repository.jpa.GameRepository;
import org.motoc.gamelibrary.technical.exception.ChildAndParentException;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Perform business logic on the entity Game
 *
 * @author RouzicJ
 */
@Service
@Transactional
public class GameService extends SimpleCrudMethodsImpl<Game, JpaRepository<Game, Long>> {

    private static final Logger logger = LoggerFactory.getLogger(GameService.class);


    private final GameRepository gameRepository;

    private final GameRepositoryCustom gameRepositoryCustom;

    @Autowired
    public GameService(JpaRepository<Game, Long> genericRepository,
                       GameRepository gameRepository,
                       GameRepositoryCustom gameRepositoryCustom) {
        super(genericRepository, Game.class);
        this.gameRepository = gameRepository;
        this.gameRepositoryCustom = gameRepositoryCustom;
    }

    public List<GameNameDto> findNames() {
        return gameRepositoryCustom.findNames();
    }

    public Page<Game> findPagedOverview(Pageable pageable, String keyword) {
        return gameRepository.findAllByLowerCaseNameContaining(keyword, pageable);
    }

    public Page<Game> findPagedOverview(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }

    public Game addExpansion(Long gameId, List<Long> expansionIds) {
        return gameRepository.findById(gameId)
                .map(game -> {
                    if (game.getCoreGame() != null)
                        throw new ChildAndParentException("The candidate core game "
                                + game.getName() + "  is already set has an expansion of the game : "
                                + game.getCoreGame().getName());

                    List<Game> expansions = gameRepository.findAllById(expansionIds);

                    for (Game expansion : expansions) {
                        /* if a candidate expansion is already an expansion, throw exception */
                        if (expansion.getCoreGame() != null)
                            throw new ChildAndParentException("The candidate expansion : " + expansion
                                    + " is already an expansion of the other game : " + expansion.getCoreGame().getName());
                        /* if a candidate expansion already has an expansion, throw exception */
                        if (!expansion.getExpansions().isEmpty())
                            throw new ChildAndParentException("The candidate expansion : " + expansion.getName()
                                    + " has already at least one expansion : expansion.size()=" + expansion.getExpansions().size());
                    }

                    return gameRepositoryCustom.addExpansions(game, expansions);
                })
                .orElseThrow(
                        () -> {
                            logger.debug("No game of id={} found.", gameId);
                            throw new NotFoundException(gameId);
                        }
                );
    }

    public Game addCoreGame(Long gameId, Long coreGameId) {
        Game coreGame = gameRepository.findById(coreGameId)
                .orElseThrow(() -> {
                    logger.debug("No core game of id={} found.", coreGameId);
                    throw new NotFoundException(coreGameId);
                });

        return gameRepository.findById(gameId)
                .map(game -> {
                    if (coreGame.getExpansions().contains(game))
                        throw new ChildAndParentException("Core game : " + coreGame.getName() + " is already the core game of " + game.getName());
                    if (game.getCoreGame() != null)
                        throw new ChildAndParentException("The game of id=" + gameId + " already has a core game");
                    if (!game.getExpansions().isEmpty())
                        throw new ChildAndParentException("Game " + game.getName() + " has at least one expansion : it can't have a core game");
                    return gameRepositoryCustom.addCoreGame(game, coreGame);
                })
                .orElseThrow(
                        () -> {
                            logger.debug("No game of id={} found.", gameId);
                            throw new NotFoundException(gameId);
                        });
    }

    public void removeCoreGame(Long id) {
        gameRepository.findById(id).ifPresentOrElse(
                game -> {
                    if (game.getCoreGame() != null)
                        gameRepositoryCustom.removeCoreGame(game);
                    else
                        logger.info("Game of id {} has no core game", id);
                },
                () -> {
                    logger.debug("No game of id={} found.", id);
                    throw new NotFoundException(id);
                });
    }

    public void removeExpansion(Long gameId, Long expansionId) {
        Game game = new Game();
        game.setId(gameId);

        Game expansion = new Game();
        expansion.setId(expansionId);

        gameRepositoryCustom.removeExpansion(game, expansion);
    }

}
