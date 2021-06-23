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
import java.util.Set;

@Service
@Transactional
public class LoanService extends SimpleCrudMethodsImpl<Loan, JpaRepository<Loan, Long>> {


    private static final Logger logger = LoggerFactory.getLogger(LoanService.class);

    private final LoanRepository loanRepository;

    private final AccountService accountService;

    private final GameCopyService gameCopyService;

    public LoanService(JpaRepository<Loan, Long> genericRepository,
                       LoanRepository loanRepository,
                       AccountService accountService,
                       GameCopyService gameCopyService) {
        super(genericRepository, Loan.class);
        this.loanRepository = loanRepository;
        this.accountService = accountService;
        this.gameCopyService = gameCopyService;
    }

    public Loan close(Long loanId) {
        Loan loanFormDB = this.loanRepository.findById(loanId).map(result -> {
            logger.debug("Loan found :" + result);
            return result;
        }).orElseThrow(() -> {
            String errorMessage = "No loan of id=" + loanId + "found ! Can't close it";
            logger.warn(errorMessage);
            throw new NotFoundException(errorMessage);
        });

        loanFormDB.setClosed(true);
        return this.loanRepository.save(loanFormDB);
    }

    public Loan checkAndSave(Long accountId, Long gameCopyId) {
        /* The three following methods throws exception if check result in fail */

        // This one checks if member exists, then checks if the membership is active.
        accountService.checkMembership(accountId);
        // Checks if game exists, then checks its loanable status.
        gameCopyService.checkLoanability(gameCopyId);
        // check if an active loan collide with the requested one
        this.checkActiveLoans(accountId, gameCopyId);


        /* Verification are done, we can now create our entities to persist */
        Loan loan;

        Account account = new Account();
        account.setId(accountId);

        GameCopy gameCopy = new GameCopy();
        gameCopy.setId(gameCopyId);

        LocalDateTime present = LocalDateTime.now();
        loan = new Loan(null, present, present.plusWeeks(4L), gameCopy, false, account);
        return loanRepository.save(loan);
    }

    private void checkActiveLoans(Long accountId, Long gameCopyId) {
        Set<Loan> collidingLoans = loanRepository.findActiveLoans(accountId, gameCopyId);

        if (!collidingLoans.isEmpty()) {
            String errorMessage = "Found at least one active loan that collide with the member:" + accountId + " and game copy:" + gameCopyId;
            logger.warn(errorMessage);
            throw new NotFoundException(errorMessage);
        }
    }
}
