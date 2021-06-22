package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * This repository takes advantage of Spring data / JPA
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a " +
            "LEFT JOIN Loan l ON (a.id = l.account.id  AND l.isClosed = true)" +
            "WHERE (a.renewalDate >= current_date AND a.id = :id) ")
    Account findActiveById(@Param("id") Long id);
}
