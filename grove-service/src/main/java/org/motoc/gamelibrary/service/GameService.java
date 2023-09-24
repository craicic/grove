package org.motoc.gamelibrary.service;

import org.motoc.gamelibrary.domain.dto.GameDto;
import org.motoc.gamelibrary.domain.dto.GameOverviewDto;
import org.motoc.gamelibrary.domain.dto.ImageDto;
import org.motoc.gamelibrary.domain.model.*;
import org.motoc.gamelibrary.mapper.GameMapper;
import org.motoc.gamelibrary.repository.jpa.*;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Perform service logic on the entity Game
 */
@Service
@Transactional
public class GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

    private final GameMapper mapper;
    private final GameRepository gameRepository;
    private final CategoryRepository categoryRepository;
    private final MechanismRepository mechanismRepository;
    private final GameCopyRepository gameCopyRepository;
    private final CreatorRepository creatorRepository;
    private final ImageRepository imageRepository;
    private Game gameToReturn;

    @Autowired
    public GameService(GameRepository gameRepository,
                       CategoryRepository categoryRepository,
                       MechanismRepository mechanismRepository,
                       GameCopyRepository gameCopyRepository,
                       CreatorRepository creatorRepository,
                       ImageRepository imageRepository) {
        this.mapper = GameMapper.INSTANCE;
        this.gameToReturn = null;
        this.gameRepository = gameRepository;
        this.categoryRepository = categoryRepository;
        this.mechanismRepository = mechanismRepository;
        this.gameCopyRepository = gameCopyRepository;
        this.creatorRepository = creatorRepository;
        this.imageRepository = imageRepository;
    }

    public GameDto save(@Valid GameDto t) {
        return mapper.gameToDto(gameRepository.save(mapper.dtoToGame(t)));
    }

    public long count() {
        return gameRepository.count();
    }

    public GameDto findById(long id) {
        return mapper.gameToDto(gameRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException("No Game of id=" + id + " found.");
                }));
    }

    public Page<GameDto> findPage(Pageable pageable) {
        return mapper.pageToPageDto(gameRepository.findAll(pageable));
    }


    public void deleteById(long id) {
        gameRepository.bulkDeleteById(id);

    }


    public GameDto findGameById(Long id) {
        return mapper.gameToDto(gameRepository.findGameById(id));
    }


    /**
     * Edits (replace or add) a game by id, cascade set to default.
     */
    public GameDto edit(@Valid GameDto newGameDto, Long id) {
        Game newGame = mapper.dtoToGame(newGameDto);
        return mapper.gameToDto(gameRepository.findById(id)
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
                    logger.debug("No game of id={} found. Set game : {}", id, newGame);
                    throw new NotFoundException("No game of id = " + id + "found.");
                }));
    }

    public List<String> findTitles() {
        return gameRepository.findTitles();
    }

    public Page<GameOverviewDto> findPagedOverview(Pageable pageable, String keyword) {
        return mapper.pageToOverviewDto(gameRepository.findGamesByKeyword(keyword, pageable));
    }


    public GameDto addCategory(Long gameId, Long categoryId) {
        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> {
                            throw new NotFoundException("No category of id=" + categoryId + " found.");
                        }
                );

        return mapper.gameToDto(gameRepository
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
                ));
    }

    public GameDto removeCategory(Long gameId, Long categoryId) {
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

        return mapper.gameToDto(gameToReturn);
    }

    public GameDto addMechanism(Long gameId, Long mechanismId) {
        Mechanism mechanism = mechanismRepository
                .findById(mechanismId)
                .orElseThrow(() -> {
                            throw new NotFoundException("No Mechanism of id=" + mechanismId + " found.");
                        }
                );
        return mapper.gameToDto(gameRepository
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
                ));
    }

    public GameDto removeMechanism(Long gameId, Long mechanismId) {
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
        return mapper.gameToDto(gameToReturn);
    }

    public GameDto addGameCopy(Long gameId, Long gameCopyId) {
        GameCopy gameCopy = gameCopyRepository
                .findById(gameCopyId)
                .orElseThrow(() -> {
                            throw new NotFoundException("No GameCopy of id=" + gameCopyId + " found.");
                        }
                );
        return mapper.gameToDto(gameRepository
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
                ));
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

    public GameDto addCreator(Long gameId, Long creatorId) {
        Creator creator = creatorRepository
                .findById(creatorId)
                .orElseThrow(() -> {
                            throw new NotFoundException("No Creator of id=" + creatorId + " found.");
                        }
                );
        return mapper.gameToDto(gameRepository
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
                ));
    }

    public GameDto removeCreator(Long gameId, Long creatorId) {
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

        return mapper.gameToDto(gameToReturn);
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

    public List<ImageDto> findImagesById(Long id) {
        Game g;
        Optional<Game> opt = gameRepository.findById(id);
        if (opt.isPresent()) {
            g = opt.get();
        } else {
            throw new NotFoundException("No game of id=" + id + " found.");
        }
        List<ImageDto> images = new ArrayList<>();
        for (Image i :
                g.getImages()) {
            images.add(new ImageDto(i.getId(), imageRepository.findBytes(i.getId())));
        }
        return images;
    }

}

