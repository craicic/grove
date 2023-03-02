package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.domain.model.Game;
import org.motoc.gamelibrary.repository.fragment.GameFragmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * This repository takes advantage of Spring data.sql / JPA
 */
public interface GameRepository extends JpaRepository<Game, Long>, GameFragmentRepository {

    Page<Game> findAllByLowerCaseNameContaining(@Param("keyword") String keyword, Pageable pageable);

}
