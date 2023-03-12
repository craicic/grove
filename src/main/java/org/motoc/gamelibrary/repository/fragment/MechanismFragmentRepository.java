package org.motoc.gamelibrary.repository.fragment;

import org.motoc.gamelibrary.domain.model.Mechanism;

/**
 * It's the mechanism custom repository, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
public interface MechanismFragmentRepository {

    /**
     * Removes a mechanism, removing it from game before
     */
    void remove(Long id);

    Mechanism saveMechanism(Mechanism mechanism);
}
