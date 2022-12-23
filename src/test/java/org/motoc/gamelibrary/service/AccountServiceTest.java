package org.motoc.gamelibrary.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.motoc.gamelibrary.domain.dto.AccountDto;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class AccountServiceTest {

    @InjectMocks
    AccountService service;

    @Test
    void findById() {
        AccountDto account = service.findById(26L);
        assertThat(account).isNotNull();

    }
}