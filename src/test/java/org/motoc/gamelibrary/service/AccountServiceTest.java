package org.motoc.gamelibrary.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.motoc.gamelibrary.repository.jpa.AccountRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AccountServiceTest {
    @Mock
    AccountRepository accountRepository;
    @InjectMocks
    AccountService service;
}