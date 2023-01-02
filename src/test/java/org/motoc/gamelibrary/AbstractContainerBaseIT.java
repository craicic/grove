package org.motoc.gamelibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractContainerBaseIT extends AbstractIntegrationBaseIT {

    public static final Logger abstractLogger = LoggerFactory.getLogger(AbstractContainerBaseIT.class);
    public static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.1-alpine")
            .withDatabaseName("game-library-test-db")
            .withUsername("postgres")
            .withPassword("postgres")
            .withReuse(true);

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);

    }
}
