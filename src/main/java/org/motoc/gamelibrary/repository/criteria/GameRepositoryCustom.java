package org.motoc.gamelibrary.repository.criteria;

import org.motoc.gamelibrary.dto.GameNameDto;

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
}
