package org.motoc.gamelibrary.repository.fragment.implementation;

import org.junit.jupiter.api.Test;
import org.motoc.gamelibrary.AbstractContainerBaseTest;
import org.motoc.gamelibrary.domain.model.Account;
import org.motoc.gamelibrary.repository.jpa.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class AccountFragmentRepositoryImplTest extends AbstractContainerBaseTest {

    @Autowired
    private AccountRepository repository;

    @Test
    void find() {
        Account account = this.repository.find(26L);
        assertThat(account.getContact()).isNotNull();
    }
}