package org.motoc.gamelibrary.repository.criteria;

import org.motoc.gamelibrary.model.GameCopy;
import org.motoc.gamelibrary.model.Seller;
import org.springframework.stereotype.Repository;

@Repository
public interface GameCopyRepositoryCustom {
    GameCopy addSeller(GameCopy copy, Seller seller);

    void removeSeller(GameCopy copy, Seller seller);
}
