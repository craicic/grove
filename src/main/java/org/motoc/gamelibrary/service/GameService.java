package org.motoc.gamelibrary.service;

import org.motoc.gamelibrary.domain.model.*;
import org.motoc.gamelibrary.repository.jpa.*;
import org.motoc.gamelibrary.service.refactor.SimpleCrudMethodsImpl;
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
    private final MechanismRepository mechanismRepository;
    private final GameCopyRepository gameCopyRepository;
    private final CreatorRepository creatorRepository;
    private final ImageRepository imageRepository;

    private Game gameToReturn;

    @Autowired
    public GameService(JpaRepository<Game, Long> genericRepository,
                       GameRepository gameRepository,
                       CategoryRepository categoryRepository,
                       MechanismRepository mechanismRepository,
                       GameCopyRepository gameCopyRepository,
                       CreatorRepository creatorRepository,
                       ImageRepository imageRepository) {
        super(genericRepository, Game.class);
        this.gameToReturn = null;
        this.gameRepository = gameRepository;
        this.categoryRepository = categoryRepository;
        this.mechanismRepository = mechanismRepository;
        this.gameCopyRepository = gameCopyRepository;
        this.creatorRepository = creatorRepository;
        this.imageRepository = imageRepository;
    }

    public Game gameEdit(@Valid Game newGame, Long id) {
        return gameRepository.findById(id)
                .map(game -> {
                    game.setTitle(newGame.getTitle());
                    game.setDescription(newGame.getDescription());
                    game.setPlayTime(newGame.getPlayTime());
                    game.setMinNumberOfPlayer(newGame.getMinNumberOfPlayer());
                    game.setMaxNumberOfPlayer(newGame.getMaxNumberOfPlayer());
                    game.setMinMonth(newGame.getMinMonth());
                    game.setMinAge(newGame.getMinAge());
                    game.setMaxAge(newGame.getMaxAge());
                    game.setMaterial(newGame.getMaterial());
                    game.setNature(newGame.getNature());
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
                    game.setRules(newGame.getRules());
                    game.setVariant(newGame.getVariant());
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
                    game.setTitle(newGame.getTitle());
                    game.setDescription(newGame.getDescription());
                    game.setPlayTime(newGame.getPlayTime());
                    game.setMinNumberOfPlayer(newGame.getMinNumberOfPlayer());
                    game.setMaxNumberOfPlayer(newGame.getMaxNumberOfPlayer());
                    game.setMinMonth(newGame.getMinMonth());
                    game.setMinAge(newGame.getMinAge());
                    game.setMaxAge(newGame.getMaxAge());
                    game.setMaterial(newGame.getMaterial());
                    game.setRules(newGame.getRules());
                    game.setVariant(newGame.getVariant());
                    game.setNature(newGame.getNature());
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

    public Game addMechanism(Long gameId, Long mechanismId) {
        Mechanism mechanism = mechanismRepository
                .findById(mechanismId)
                .orElseThrow(() -> {
                            throw new NotFoundException("No Mechanism of id=" + mechanismId + " found.");
                        }
                );
        return gameRepository
                .findById(gameId)
                .map(game -> {
                    if (!game.getMechanisms().isEmpty() && game.getMechanisms().contains(mechanism))
                        throw new IllegalStateException("Game of id=" + game.getId() +
                                                        " is already linked to mechanism of id=" + mechanism.getId());
                    return gameRepository.addMechanism(game, mechanism);
                })
                .orElseThrow(() -> {
                            throw new NotFoundException("No game of id=" + gameId + " found.");
                        }
                );
    }

    public Game removeMechanism(Long gameId, Long mechanismId) {
        this.gameToReturn = null;

        Mechanism mechanism = mechanismRepository.findById(mechanismId).orElseThrow(() -> {
                    throw new NotFoundException("No mechanism of id={}" + mechanismId + " found.");
                }
        );

        gameRepository
                .findById(gameId)
                .ifPresentOrElse(game -> {
                    if (game.getMechanisms().isEmpty() || !game.getMechanisms().contains(mechanism))
                        throw new IllegalStateException("Game of id=" + game.getId() +
                                                        " is not linked to mechanism of id=" + mechanism.getId());

                    gameToReturn = gameRepository.removeMechanism(game, mechanism);
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
}
