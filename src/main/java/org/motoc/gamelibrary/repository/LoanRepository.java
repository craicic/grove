package org.motoc.gamelibrary.repository;

import org.motoc.gamelibrary.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
