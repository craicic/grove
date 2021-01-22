package org.motoc.gamelibrary.business;

import org.motoc.gamelibrary.business.refactor.SimpleCrudMethodsImpl;
import org.motoc.gamelibrary.dto.GameNameDto;
import org.motoc.gamelibrary.model.Game;
import org.motoc.gamelibrary.repository.criteria.GameRepositoryCustom;
import org.motoc.gamelibrary.repository.jpa.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public GameService(JpaRepository<Game, Long> genericRepository, GameRepository gameRepository, GameRepositoryCustom gameRepositoryCustom) {
        super(genericRepository, Game.class);
        this.gameRepository = gameRepository;
        this.gameRepositoryCustom = gameRepositoryCustom;
    }

    public List<GameNameDto> findNames() {
        return gameRepositoryCustom.findNames();
    }
}
