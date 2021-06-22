package org.motoc.gamelibrary.business;

import org.motoc.gamelibrary.business.refactor.SimpleCrudMethodsImpl;
import org.motoc.gamelibrary.model.Account;
import org.motoc.gamelibrary.model.GameCopy;
import org.motoc.gamelibrary.model.Loan;
import org.motoc.gamelibrary.repository.jpa.LoanRepository;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class LoanService extends SimpleCrudMethodsImpl<Loan, JpaRepository<Loan, Long>> {


    private static final Logger logger = LoggerFactory.getLogger(LoanService.class);

    private final LoanRepository loanRepository;

    private final AccountService accountService;

    private final GameCopyService gameCopyService;

    public LoanService(JpaRepository<Loan, Long> genericRepository, LoanRepository loanRepository, AccountService accountService, GameCopyService gameCopyService) {
        super(genericRepository, Loan.class);
        this.loanRepository = loanRepository;
        this.accountService = accountService;
        this.gameCopyService = gameCopyService;
    }

    public Loan save(Long accountId, Long gameCopyId) {
        Account account = accountService.findActiveById(accountId);
        GameCopy gameCopy = gameCopyService.findLoanableById(gameCopyId);
        Loan loan;

        if (account != null && gameCopy != null) {
            LocalDateTime present = LocalDateTime.now();
            loan = new Loan(null, present, present.plusWeeks(4L), gameCopy, false, account);
            loanRepository.save(loan);
        } else if (account == null && gameCopy == null) {
            throw new NotFoundException("Account of id:" + accountId + " not found or member can't loan games (membership or loan status)" +
                    "\nGame copy of id:" + gameCopyId + " not found or not loanable");
        } else if (account == null) {
            throw new NotFoundException("Account of id:" + accountId + " not found or member can't loan games (membership or loan status)");
        } else {
            throw new NotFoundException("gameCopy of id:" + gameCopyId + " not found or not loanable");
        }
        return loan;
    }
}
