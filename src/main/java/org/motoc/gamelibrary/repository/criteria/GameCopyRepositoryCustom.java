package org.motoc.gamelibrary.repository.criteria;

import org.motoc.gamelibrary.model.GameCopy;
import org.motoc.gamelibrary.model.Seller;

public interface GameCopyRepositoryCustom {
    GameCopy addSeller(GameCopy copy, Seller seller);

    void removeSeller(GameCopy copy, Seller seller);
}
