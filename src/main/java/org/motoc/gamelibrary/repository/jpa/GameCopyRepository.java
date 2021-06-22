package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.model.GameCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * This repository takes advantage of Spring data / JPA
 */
public interface GameCopyRepository extends JpaRepository<GameCopy, Long> {

    GameCopy findByObjectCode(String code);

    @Query("SELECT gc FROM GameCopy gc " +
            "LEFT JOIN Loan l ON ( gc.id = l.gameCopy.id AND l.isClosed = true ) " +
            "WHERE ( gc.isLoanable = true AND gc.id = :id)")
    GameCopy findLoanableById(@Param("id") Long id);
}
