package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

/**
 * This repository takes advantage of Spring data.sql / JPA
 */
public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Query("SELECT distinct l FROM Loan l " +
            "WHERE ( l.account.id = :accountId OR l.gameCopy.id = :gameCopyId ) " +
            "AND l.isClosed = false")
    Set<Loan> findActiveLoans(@Param("accountId") Long accountId, @Param("gameCopyId") Long gameCopyId);
}
