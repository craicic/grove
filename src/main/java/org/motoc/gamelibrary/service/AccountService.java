package org.motoc.gamelibrary.service;

import org.motoc.gamelibrary.domain.dto.AccountDto;
import org.motoc.gamelibrary.domain.model.Account;
import org.motoc.gamelibrary.mapper.AccountMapper;
import org.motoc.gamelibrary.repository.jpa.AccountRepository;
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

    final private AccountMapper mapper;

    @Autowired
    public AccountService(AccountRepository repository,
                          AccountMapper mapper) {
        this.repository = repository;
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
                    logger.debug("Found account of id={} : {}", id, accountFromPersistence);
                    return repository.save(accountFromPersistence);
                })
                .orElseGet(() -> {
                    account.setId(id);
                    logger.debug("No account of id={} found. Set account : {}", id, account);
                    return repository.save(account);
                });
    }

    private boolean isMembershipUp(Account account) {
        return account.getRenewalDate().isAfter(LocalDate.now());
    }


    public List<Account> findAccountsWithNoCurrentLoan() {
        return repository.findAllNoLoan();
    }
}
