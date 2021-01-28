package org.motoc.gamelibrary.repository.criteria;

import org.motoc.gamelibrary.dto.GameNameDto;
import org.motoc.gamelibrary.model.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    Page<Game> getFilteredGameOverview(Pageable pageable, String keyword);
}
