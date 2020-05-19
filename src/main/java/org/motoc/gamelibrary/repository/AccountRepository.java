package org.motoc.gamelibrary.repository;

import org.motoc.gamelibrary.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
