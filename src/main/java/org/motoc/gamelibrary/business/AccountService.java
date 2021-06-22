package org.motoc.gamelibrary.business;

import org.motoc.gamelibrary.business.refactor.SimpleCrudMethodsImpl;
import org.motoc.gamelibrary.model.Account;
import org.motoc.gamelibrary.repository.criteria.AccountRepositoryCustom;
import org.motoc.gamelibrary.repository.jpa.AccountRepository;
import org.motoc.gamelibrary.repository.jpa.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Transactional
public class AccountService extends SimpleCrudMethodsImpl<Account, JpaRepository<Account, Long>> {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    final private AccountRepository accountRepository;

    final private ContactRepository contactRepository;

    final private AccountRepositoryCustom accountRepositoryCustom;

    @Autowired
    public AccountService(JpaRepository<Account, Long> genericRepository, AccountRepository accountRepository,
                          ContactRepository contactRepository, AccountRepositoryCustom accountRepositoryCustom) {
        super(genericRepository, Account.class);
        this.accountRepository = accountRepository;
        this.contactRepository = contactRepository;
        this.accountRepositoryCustom = accountRepositoryCustom;
    }

    /**
     * Edits a, account by id
     */
    public Account edit(@Valid Account account, Long id) {
        return accountRepository.findById(id)
                .map(accountFromPersistence -> {
                    accountFromPersistence.setMembershipNumber(account.getMembershipNumber());
                    accountFromPersistence.setRenewalDate(account.getRenewalDate());
                    accountFromPersistence.setFirstName(account.getFirstName());
                    accountFromPersistence.setLastName(account.getLastName());
                    accountFromPersistence.setUsername(account.getUsername());
                    accountFromPersistence.setContact(account.getContact());
                    accountFromPersistence.setLoan(account.getLoan());
                    logger.debug("Found account of id={} : {}", id, accountFromPersistence);
                    return accountRepository.save(accountFromPersistence);
                })
                .orElseGet(() -> {
                    account.setId(id);
                    logger.debug("No account of id={} found. Set account : {}", id, account);
                    return accountRepository.save(account);
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
        return accountRepository.save(account);
    }

    /**
     * Find an active member of the given id
     */
    public Account findActiveById(Long id) {
        return accountRepository.findActiveById(id);
    }
}
