package org.motoc.gamelibrary.repository.fragment.implementation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.motoc.gamelibrary.domain.model.Account;
import org.motoc.gamelibrary.repository.AbstractContainerBaseTest;
import org.motoc.gamelibrary.repository.jpa.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;

import static org.assertj.core.api.Assertions.assertThat;

class AccountRepositoryTest extends AbstractContainerBaseTest {

    @BeforeAll
    static void startAbstractContainer() {
        postgreSQLContainer.start();

        JdbcDatabaseDelegate containerDelegate = new JdbcDatabaseDelegate(postgreSQLContainer, "");
        ScriptUtils.runInitScript(containerDelegate, "sql/schema.sql");
        ScriptUtils.runInitScript(containerDelegate, "sql/data.sql");
    }

    @Autowired
    private AccountRepository repository;

    @Test
    void find() {
        Account account = this.repository.find(100L);
        abstractLogger.info(account.toString());
        assertThat(account.getContact()).isNotNull();
    }
}