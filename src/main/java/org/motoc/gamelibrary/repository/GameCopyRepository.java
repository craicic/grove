package org.motoc.gamelibrary.repository;

import org.motoc.gamelibrary.model.GameCopy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameCopyRepository extends JpaRepository<GameCopy, Long> {
}
