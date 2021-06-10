package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.model.GameCopy;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository takes advantage of Spring data / JPA
 */
public interface GameCopyRepository extends JpaRepository<GameCopy, Long> {

    GameCopy findByObjectCode(String code);
}
