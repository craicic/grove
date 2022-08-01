package org.motoc.gamelibrary.repository.fragment.implementation;

import org.junit.jupiter.api.Test;
import org.motoc.gamelibrary.domain.model.Account;
import org.motoc.gamelibrary.repository.jpa.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@Commit
class AccountFragmentRepositoryImplTest {

    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14")
            .withDatabaseName("game-library-test-db")
            .withPassword("postgres")
            .withUsername("postgres");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @Autowired
    private AccountRepository repository;

    @Test
    void find() {
        Account account = this.repository.find(26L);
        assertThat(account.getContact()).isNotNull();
    }
}