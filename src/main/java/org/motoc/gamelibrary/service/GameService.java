package org.motoc.gamelibrary.service;

import org.motoc.gamelibrary.domain.model.*;
import org.motoc.gamelibrary.repository.jpa.*;
import org.motoc.gamelibrary.service.refactor.SimpleCrudMethodsImpl;
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
import javax.validation.Valid;
import java.util.List;

/**
 * Perform service logic on the entity Game
 */
@Service
@Transactional
public class GameService extends SimpleCrudMethodsImpl<Game, JpaRepository<Game, Long>> {

    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

    private final GameRepository gameRepository;

    private final CategoryRepository categoryRepository;
    private final ThemeRepository themeRepository;
    private final GameCopyRepository gameCopyRepository;
    private final CreatorRepository creatorRepository;
    private final ProductLineRepository productLineRepository;
    private final ImageRepository imageRepository;

    private Game gameToReturn;

    @Autowired
    public GameService(JpaRepository<Game, Long> genericRepository,
                       GameRepository gameRepository,
                       CategoryRepository categoryRepository,
                       ThemeRepository themeRepository,
                       GameCopyRepository gameCopyRepository,
                       CreatorRepository creatorRepository,
                       ProductLineRepository productLineRepository,
                       ImageRepository imageRepository) {
        super(genericRepository, Game.class);
        this.gameToReturn = null;
        this.gameRepository = gameRepository;
        this.categoryRepository = categoryRepository;
        this.themeRepository = themeRepository;
        this.gameCopyRepository = gameCopyRepository;
        this.creatorRepository = creatorRepository;
        this.productLineRepository = productLineRepository;
        this.imageRepository = imageRepository;
    }

    public Game gameEdit(@Valid Game newGame, Long id) {
        return gameRepository.findById(id)
                .map(game -> {
                    game.setName(newGame.getName());
                    game.setDescription(newGame.getDescription());
                    game.setPlayTime(newGame.getPlayTime());
                    game.setMinNumberOfPlayer(newGame.getMinNumberOfPlayer());
                    game.setMaxNumberOfPlayer(newGame.getMaxNumberOfPlayer());
                    game.setMinMonth(newGame.getMinMonth());
                    game.setMinAge(newGame.getMinAge());
                    game.setMaxAge(newGame.getMaxAge());
                    game.setStuff(newGame.getStuff());
                    game.setNature(newGame.getNature());
                    game.setSize(newGame.getSize());
                    logger.debug("Found game of id={}, proceeding to its edit", id);
                    return gameRepository.save(game);
                })
                .orElseGet(() -> {
                    newGame.setId(id);
                    logger.debug("No game of id={} found. Set game : {}", id, newGame);
                    return gameRepository.save(newGame);
                });
    }

    public Game ruleEdit(@Valid Game newGame, Long id) {
        return gameRepository.findById(id)
                .map(game -> {
                    game.setPreparation(newGame.getPreparation());
                    game.setGoal(newGame.getGoal());
                    game.setCoreRules(newGame.getCoreRules());
                    game.setVariant(newGame.getVariant());
                    game.setEnding(newGame.getEnding());
                    logger.debug("Found game of id={}, proceeding to its rules edit", id);
                    return gameRepository.save(game);
                })
                .orElseGet(() -> {
                    newGame.setId(id);
                    logger.debug("No game of id={} found. Set game : {}", id, newGame);
                    return gameRepository.save(newGame);
                });

    }

    /**
     * Edits (replace or add) a game by id, cascade set to default.
     */
    public Game edit(@Valid Game newGame, Long id) {
        return gameRepository.findById(id)
                .map(game -> {
                    game.setName(newGame.getName());
                    game.setDescription(newGame.getDescription());
                    game.setPlayTime(newGame.getPlayTime());
                    game.setMinNumberOfPlayer(newGame.getMinNumberOfPlayer());
                    game.setMaxNumberOfPlayer(newGame.getMaxNumberOfPlayer());
                    game.setMinMonth(newGame.getMinMonth());
                    game.setMinAge(newGame.getMinAge());
                    game.setMaxAge(newGame.getMaxAge());
                    game.setStuff(newGame.getStuff());
                    game.setPreparation(newGame.getPreparation());
                    game.setGoal(newGame.getGoal());
                    game.setCoreRules(newGame.getCoreRules());
                    game.setVariant(newGame.getVariant());
                    game.setEnding(newGame.getEnding());
                    game.setNature(newGame.getNature());
                    game.setSize(newGame.getSize());
                    logger.debug("Found game of id={}, proceeding to its edit", id);
                    return gameRepository.save(game);
                })
                .orElseGet(() -> {
                    newGame.setId(id);
                    logger.debug("No game of id={} found. Set game : {}", id, newGame);
                    return gameRepository.save(newGame);
                });
    }

    public List<String> findNames() {
        return gameRepository.findNames();
    }

    public Page<Game> findPagedOverview(Pageable pageable, String keyword) {
        return gameRepository.findGamesByKeyword(keyword, pageable);
    }

    public Page<Game> findPagedOverview(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }

    public Game addExpansions(Long gameId, List<Long> expansionIds) {
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
                            throw new ChildAndParentException("The candidate expansion : " + expansion.getName()
                                    + " is already an expansion of the other game : " + expansion.getCoreGame().getName());
                        /* if a candidate expansion already has an expansion, throw exception */
                        if (!expansion.getExpansions().isEmpty())
                            throw new ChildAndParentException("The candidate expansion : " + expansion.getName()
                                    + " has already at least one expansion : expansion.size()=" + expansion.getExpansions().size());
                        if (game.getExpansions().contains(expansion))
                            throw new ChildAndParentException("Expansion : " + expansion.getName() + " of id=" + expansion.getId() +
                                    " is already linked to the game : " + game.getName() + " of id=" + game.getId());
                    }

                    return gameRepository.addExpansions(game, expansions);
                })
                .orElseThrow(
                        () -> {
                            logger.debug("No game of id={} found.", gameId);
                            throw new NotFoundException("No category of id=" + gameId + " found.");
                        }
                );
    }


    public Game addExpansion(Long gameId, Long expansionId) {
        Game expansion = gameRepository.findById(expansionId).orElseThrow(
                () -> {
                    throw new NotFoundException("No expansion of id=" + expansionId + " found.");
                }
        );

        return gameRepository.findById(gameId)
                .map(game -> {
                    if (game.getCoreGame() != null)
                        throw new ChildAndParentException("The candidate core game "
                                + game.getName() + "  is already set has an expansion of the game : "
                                + game.getCoreGame().getName());

                    /* if the candidate expansion is already an expansion, throw exception */
                    if (expansion.getCoreGame() != null)
                        throw new ChildAndParentException("The candidate expansion : " + expansion.getName()
                                + " is already an expansion of the other game : " + expansion.getCoreGame().getName());
                    /* if a candidate expansion already has an expansion, throw exception */
                    if (!expansion.getExpansions().isEmpty())
                        throw new ChildAndParentException("The candidate expansion : " + expansion.getName()
                                + " has already at least one expansion : expansion.size()=" + expansion.getExpansions().size());
                    if (game.getExpansions().contains(expansion))
                        throw new ChildAndParentException("Expansion : " + expansion.getName() + " of id=" + expansion.getId() +
                                " is already linked to the game : " + game.getName() + " of id=" + game.getId());

                    return gameRepository.addExpansion(game, expansion);
                })
                .orElseThrow(
                        () -> {
                            logger.debug("No game of id={} found.", gameId);
                            throw new NotFoundException("No category of id=" + gameId + " found.");
                        }
                );
    }

    public Game addCoreGame(Long gameId, Long coreGameId) {
        Game coreGame = gameRepository.findById(coreGameId)
                .orElseThrow(() -> {
                    throw new NotFoundException("No core game of id=" + coreGameId + " found.");
                });

        return gameRepository.findById(gameId)
                .map(game -> {
                    if (coreGame.getExpansions().contains(game))
                        throw new ChildAndParentException("Core game : " + coreGame.getName() + " is already the core game of " + game.getName());
                    if (game.getCoreGame() != null)
                        throw new ChildAndParentException("The game of id=" + gameId + " already has a core game");
                    if (!game.getExpansions().isEmpty())
                        throw new ChildAndParentException("Game " + game.getName() + " has at least one expansion : it can't have a core game");
                    return gameRepository.addCoreGame(game, coreGame);
                })
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("No game of id=" + gameId + " found.");
                        });
    }

    public void removeCoreGame(Long id) {
        gameRepository.findById(id).ifPresentOrElse(
                game -> {
                    if (game.getCoreGame() != null)
                        gameRepository.removeCoreGame(game);
                    else
                        logger.info("Game of id {} has no core game", id);
                },
                () -> {
                    throw new NotFoundException("No game of id=" + id + " found.");
                });
    }

    public void removeExpansion(Long gameId, Long expansionId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> {
                    throw new NotFoundException("No game of id=" + gameId + " found.");
                }
        );

        Game expansion = gameRepository.findById(expansionId).orElseThrow(() -> {
            throw new NotFoundException("No expansion of id=" + expansionId + " found.");
        });

        if (game.getExpansions().isEmpty() || !game.getExpansions().contains(expansion))
            throw new IllegalStateException("Game of id=" + game.getId() + " does not contains this expansion of id=" + expansion.getId());
        gameRepository.removeExpansion(game, expansion);
    }

    public Game addCategory(Long gameId, Long categoryId) {
        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> {
                            throw new NotFoundException("No category of id=" + categoryId + " found.");
                        }
                );

        return gameRepository
                .findById(gameId)
                .map(game -> {
                    if (!game.getCategories().isEmpty() && game.getCategories().contains(category))
                        throw new IllegalStateException("Game of id=" + game.getId() +
                                " is already linked to category of id=" + category.getId());
                    return gameRepository.addCategory(game, category);
                })
                .orElseThrow(() -> {
                            throw new NotFoundException("No game of id=" + gameId + " found.");
                        }
                );
    }

    public Game removeCategory(Long gameId, Long categoryId) {
        this.gameToReturn = null;

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> {
                    throw new NotFoundException("No category of id={}" + categoryId + " found.");
                }
        );

        gameRepository
                .findById(gameId)
                .ifPresentOrElse(game -> {
                    if (game.getCategories().isEmpty() || !game.getCategories().contains(category))
                        throw new IllegalStateException("Game of id=" + game.getId() +
                                " is not linked to category of id=" + category.getId());

                    gameToReturn = gameRepository.removeCategory(game, category);
                }, () -> {
                    throw new NotFoundException("No game of id=" + gameId + " found.");
                });

        return gameToReturn;
    }

    public Game addTheme(Long gameId, Long themeId) {
        Theme theme = themeRepository
                .findById(themeId)
                .orElseThrow(() -> {
                            throw new NotFoundException("No Theme of id=" + themeId + " found.");
                        }
                );
        return gameRepository
                .findById(gameId)
                .map(game -> {
                    if (!game.getThemes().isEmpty() && game.getThemes().contains(theme))
                        throw new IllegalStateException("Game of id=" + game.getId() +
                                " is already linked to theme of id=" + theme.getId());
                    return gameRepository.addTheme(game, theme);
                })
                .orElseThrow(() -> {
                            throw new NotFoundException("No game of id=" + gameId + " found.");
                        }
                );
    }

    public Game removeTheme(Long gameId, Long themeId) {
        this.gameToReturn = null;

        Theme theme = themeRepository.findById(themeId).orElseThrow(() -> {
                    throw new NotFoundException("No theme of id={}" + themeId + " found.");
                }
        );

        gameRepository
                .findById(gameId)
                .ifPresentOrElse(game -> {
                    if (game.getThemes().isEmpty() || !game.getThemes().contains(theme))
                        throw new IllegalStateException("Game of id=" + game.getId() +
                                " is not linked to theme of id=" + theme.getId());

                    gameToReturn = gameRepository.removeTheme(game, theme);
                }, () -> {
                    throw new NotFoundException("No game of id=" + gameId + " found.");
                });
        return gameToReturn;
    }

    public Game addGameCopy(Long gameId, Long gameCopyId) {
        GameCopy gameCopy = gameCopyRepository
                .findById(gameCopyId)
                .orElseThrow(() -> {
                            throw new NotFoundException("No GameCopy of id=" + gameCopyId + " found.");
                        }
                );
        return gameRepository
                .findById(gameId)
                .map(game -> {
                    if (!game.getGameCopies().isEmpty() && game.getGameCopies().contains(gameCopy))
                        throw new IllegalStateException("Game of id=" + game.getId() +
                                " is already linked to gameCopy of id=" + gameCopy.getId());
                    return gameRepository.addGameCopy(game, gameCopy);
                })
                .orElseThrow(() -> {
                            throw new NotFoundException("No game of id=" + gameId + " found.");
                        }
                );
    }

    public void removeGameCopy(Long gameId, Long gameCopyId) {

        GameCopy gameCopy = gameCopyRepository.findById(gameCopyId).orElseThrow(() -> {
                    throw new NotFoundException("No gameCopy of id={}" + gameCopyId + " found.");
                }
        );

        gameRepository
                .findById(gameId)
                .ifPresentOrElse(game -> {
                    if (!game.getGameCopies().contains(gameCopy))
                        throw new IllegalStateException("Game of id=" + game.getId() +
                                " is not linked to gameCopy of id=" + gameCopy.getId());
                    gameRepository.removeGameCopy(game, gameCopy);
                }, () -> {
                    throw new NotFoundException("No game of id=" + gameId + " found.");
                });
    }

    public Game addCreator(Long gameId, Long creatorId) {
        Creator creator = creatorRepository
                .findById(creatorId)
                .orElseThrow(() -> {
                            throw new NotFoundException("No Creator of id=" + creatorId + " found.");
                        }
                );
        return gameRepository
                .findById(gameId)
                .map(game -> {
                    if (!game.getCreators().isEmpty() && game.getCreators().contains(creator))
                        logger.warn("Game of id=" + game.getId() +
                                " is already linked to creator of id=" + creator.getId());

                    return gameRepository.addCreator(game, creator);
                })
                .orElseThrow(() -> {
                            throw new NotFoundException("No game of id=" + gameId + " found.");
                        }
                );
    }

    public Game removeCreator(Long gameId, Long creatorId) {
        this.gameToReturn = null;

        Creator creator = creatorRepository.findById(creatorId).orElseThrow(() -> {
                    throw new NotFoundException("No creator of id={}" + creatorId + " found.");
                }
        );

        gameRepository
                .findById(gameId)
                .ifPresentOrElse(game -> {
                    if (game.getCreators().isEmpty() || !game.getCreators().contains(creator))
                        throw new IllegalStateException("Game of id=" + game.getId() +
                                " is not linked to creator of id=" + creator.getId());
                    gameToReturn = gameRepository.removeCreator(game, creator);
                }, () -> {
                    throw new NotFoundException("No game of id=" + gameId + " found.");
                });

        return gameToReturn;
    }

    public void addImage(Long gameId, Long imageId) {
        Image image = imageRepository.findById(imageId).orElseThrow(
                () -> {
                    throw new NotFoundException("No image of id=" + imageId + " found.");
                });

        gameRepository
                .findById(gameId)
                .ifPresentOrElse(game -> {
                            if (game.getImages() != null && game.getImages().contains(image))
                                throw new IllegalStateException("Image of id=" + imageId + " is already attached to the game of id=" + gameId);
                            gameRepository.attachImage(game, image);
                        },
                        () -> {
                            throw new NotFoundException("No game of id=" + gameId + " found.");
                        });
    }


    public Game addProductLine(Long gameId, Long lineId) {
        ProductLine productLine = productLineRepository.findById(lineId).orElseThrow(
                () -> {
                    throw new NotFoundException("No product line of id=" + lineId + " found.");
                });

        return gameRepository.findById(gameId)
                .map(game -> {
                    if (!(game.getProductLine() == null)) {
                        logger.warn("Game of id=" + game.getId() + " already has a product line");
                    }
                    if (game.getProductLine() == productLine) {
                        logger.warn("Game of id=" + game.getId() +
                                " is already linked to product line of id=" + productLine.getId());
                    }

                    return gameRepository.addProductLine(game, productLine);
                })
                .orElseThrow(() -> {
                            throw new NotFoundException("No game of id=" + gameId + " found.");
                        }
                );
    }

    public Game removeProductLine(Long gameId, Long lineId) {
        this.gameToReturn = null;

        ProductLine productLine = productLineRepository.findById(lineId).orElseThrow(() -> {
                    throw new NotFoundException("No productLine of id={}" + lineId + " found.");
                }
        );

        gameRepository
                .findById(gameId)
                .ifPresentOrElse(game -> {
                    if (game.getProductLine() == null) {
                        logger.warn("Game of id=" + game.getId() + " has no product line");
                    }
                    if (game.getProductLine() != productLine) {
                        logger.warn("Game of id=" + game.getId() +
                                " is not linked to product line of id=" + lineId);
                    }

                    gameToReturn = gameRepository.removeProductLine(game, productLine);
                }, () -> {
                    throw new NotFoundException("No game of id=" + gameId + " found.");
                });

        return gameToReturn;
    }
}