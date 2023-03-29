package org.motoc.gamelibrary.repository.fragment;

import org.motoc.gamelibrary.domain.model.GameCopy;
import org.motoc.gamelibrary.domain.model.Publisher;

public interface GameCopyFragmentRepository {

    GameCopy addPublisher(GameCopy copy, Publisher publisher);

    GameCopy removePublisher(GameCopy copy, Publisher publisher);
}
