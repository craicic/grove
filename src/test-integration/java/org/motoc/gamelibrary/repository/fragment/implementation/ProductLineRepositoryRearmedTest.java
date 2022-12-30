package org.motoc.gamelibrary.repository.fragment.implementation;

import org.junit.jupiter.api.*;
import org.motoc.gamelibrary.domain.dto.ProductLineNameDto;
import org.motoc.gamelibrary.domain.model.ProductLine;
import org.motoc.gamelibrary.repository.jpa.ProductLineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductLineRepositoryRearmedTest extends AbstractContainerBaseTest {

    @BeforeAll
    static void startAbstractContainer() {
        postgreSQLContainer.start();
    }

    @BeforeEach
    void reloadSQL() {
        JdbcDatabaseDelegate containerDelegate = new JdbcDatabaseDelegate(postgreSQLContainer, "");
        ScriptUtils.runInitScript(containerDelegate, "sql/schema.sql");
        ScriptUtils.runInitScript(containerDelegate, "sql/data.sql");
    }

    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private ProductLineRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(ProductLineRepositoryRearmedTest.class);

    private static final Long pId = 1L;

    private static final Long wrongId = 1152L;

    @Test
    @Order(1)
    void whenSaveProductLine_ThenReturnExpectedProductLine() {
        final String pName = "Unlock";
        ProductLine p = new ProductLine();
        p.setName(pName);
        p = repository.saveProductLine(p);

        assertThat(p.getName()).isEqualTo(pName);
        assertThat(p.getLowerCaseName()).isEqualTo(pName.toLowerCase());
    }

    @Test
    @Order(2)
    void whenSaveAlreadyExistingProductLine_ThenThrowADataIntegrityViolationException() {
        EntityManager em = emf.createEntityManager();
        ProductLine p = new ProductLine();
        p.setName(em.find(ProductLine.class, 1L).getName());
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> repository.saveProductLine(p));

        assertThat(exception.getClass()).isEqualTo(DataIntegrityViolationException.class);
    }

    @Test
    @Order(3)
    void whenDeletePublisher_ThenPublisherCountDecreaseBy1() {
        final long preDeleteCount = repository.count();
        repository.remove(pId);
        final long postDeleteCount = repository.count();

        assertThat(postDeleteCount).isEqualTo(preDeleteCount - 1L);
    }

    @Test
    @Order(6)
    void whenFindNames_ThenReturnAList() {
        final long expectedCount = repository.count();
        List<ProductLineNameDto> names = repository.findNames();

        assertThat(names.size()).isEqualTo(expectedCount);
    }
}