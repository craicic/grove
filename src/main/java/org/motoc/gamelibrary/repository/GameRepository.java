package org.motoc.gamelibrary.repository;

import org.motoc.gamelibrary.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
