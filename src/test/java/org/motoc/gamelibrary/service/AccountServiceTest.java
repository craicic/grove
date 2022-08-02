package org.motoc.gamelibrary.service;

import org.junit.jupiter.api.Test;
import org.motoc.gamelibrary.AbstractContainerBaseTest;
import org.motoc.gamelibrary.domain.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class AccountServiceTest extends AbstractContainerBaseTest {

    @Autowired
    AccountService service;

    @Test
    void findById() {
        AccountDto account = service.findById(26L);
        assertThat(account).isNotNull();

    }
}