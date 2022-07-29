package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.model.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository takes advantage of Spring data.sql / JPA
 */
public interface LoanStatusRepository extends JpaRepository<LoanStatus, Long> {
}
