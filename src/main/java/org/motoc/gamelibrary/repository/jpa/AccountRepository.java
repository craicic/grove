package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository takes advantage of Spring data / JPA
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
}
