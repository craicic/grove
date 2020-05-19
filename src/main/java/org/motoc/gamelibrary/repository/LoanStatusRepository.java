package org.motoc.gamelibrary.repository;

import org.motoc.gamelibrary.model.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanStatusRepository extends JpaRepository<LoanStatus, Long> {
}
