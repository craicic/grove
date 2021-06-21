package org.motoc.gamelibrary.business;

import org.motoc.gamelibrary.business.refactor.SimpleCrudMethodsImpl;
import org.motoc.gamelibrary.model.Loan;
import org.motoc.gamelibrary.repository.jpa.LoanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LoanService extends SimpleCrudMethodsImpl<Loan, JpaRepository<Loan, Long>> {


    private static final Logger logger = LoggerFactory.getLogger(Loan.class);

    private final LoanRepository loanRepository;

    public LoanService(JpaRepository<Loan, Long> genericRepository, LoanRepository loanRepository) {
        super(genericRepository, Loan.class);
        this.loanRepository = loanRepository;
    }
}
