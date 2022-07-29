package org.motoc.gamelibrary.business;

import org.motoc.gamelibrary.dto.AccountDto;
import org.motoc.gamelibrary.mapper.AccountMapper;
import org.motoc.gamelibrary.model.Account;
import org.motoc.gamelibrary.repository.jpa.AccountRepository;
import org.motoc.gamelibrary.repository.jpa.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    final private AccountRepository repository;

    final private ContactRepository contactRepository;

    final private AccountMapper mapper;

    @Autowired
    public AccountService(AccountRepository repository,
                          ContactRepository contactRepository,
                          AccountMapper mapper) {
        this.repository = repository;
        this.contactRepository = contactRepository;
        this.mapper = mapper;
    }

    public AccountDto findById(Long accountId) {
        return mapper.accountToDto(this.repository.find(accountId));
    }

    public List<Account> findAll() {
        return this.repository.findAll();
    }


    /**
     * Edits an account by id
     */
    public Account edit(@Valid Account account, Long id) {
        return repository.findById(id)
                .map(accountFromPersistence -> {
                    accountFromPersistence.setMembershipNumber(account.getMembershipNumber());
                    accountFromPersistence.setRenewalDate(account.getRenewalDate());
                    accountFromPersistence.setFirstName(account.getFirstName());
                    accountFromPersistence.setLastName(account.getLastName());
                    accountFromPersistence.setUsername(account.getUsername());
                    accountFromPersistence.setContact(account.getContact());
                    accountFromPersistence.setLoan(account.getLoan());
                    logger.debug("Found account of id={} : {}", id, accountFromPersistence);
                    return repository.save(accountFromPersistence);
                })
                .orElseGet(() -> {
                    account.setId(id);
                    logger.debug("No account of id={} found. Set account : {}", id, account);
                    return repository.save(account);
                });
    }

    /**
     * Persist a new account by id (if a contact is associated, this one must be new)
     */
    public Account save(@Valid Account account, boolean hasContact) {
        if (hasContact) {
            long contactId = contactRepository.save(account.getContact()).getId();
            account.getContact().setId(contactId);
        }
        return repository.save(account);
    }

    private boolean isMembershipUp(Account account) {
        return account.getRenewalDate().isAfter(LocalDate.now());
    }

//    public void checkMembership(Long accountId) {
//        String errorMessage = "";
//        // find active member by id
//        // if no result throw exception
//        Account account = this.findById(accountId);
//
//        if (account == null) {
//            // todo useless??
//            errorMessage = "No account of id=" + accountId + " found in database.";
//            logger.warn(errorMessage);
//            throw new NotFoundException(errorMessage);
//        }
//
//        // has member got an active membership
//        // if not throw exception
//        boolean isUp = this.isMembershipUp(account);
//        if (isUp) {
//            errorMessage = "Member of accountId=" + accountId + " has an expired membership";
//            logger.warn(errorMessage);
//            throw new IllegalStateException(errorMessage);
//        }
//    }


    public List<Account> findAccountsWithNoCurrentLoan() {
        return repository.findAllNoLoan();
    }
}
