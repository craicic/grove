package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * This repository takes advantage of Spring data / JPA
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT a.id, a.username, a.first_name, a.last_name, a.renewal_date, a.membership_number FROM account a " +
            "WHERE (SELECT (COUNT(l) = 0) " +
            "FROM loan l WHERE (l.fk_account = a.id AND l.is_closed = false )) " +
            "ORDER BY a.username --/n #pageable/n ",
            nativeQuery = true)
    Page<Account> findAllNoLoan(Pageable pageable);
}

