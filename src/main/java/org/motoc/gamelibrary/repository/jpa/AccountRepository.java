package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.model.Account;
import org.motoc.gamelibrary.repository.fragment.AccountFragmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * This repository takes advantage of Spring data.sql / JPA
 */
public interface AccountRepository extends JpaRepository<Account, Long>, AccountFragmentRepository {

    @Query(value = "SELECT a.id, a.username, a.first_name, a.last_name, a.renewal_date, a.membership_number, a.fk_contact FROM account a " +
            "WHERE (SELECT (COUNT(l) = 0) " +
            "FROM loan l WHERE (l.fk_account = a.id AND l.is_closed = false )) " +
            "ORDER BY a.username ",
            nativeQuery = true)
    List<Account> findAllNoLoan();
}

