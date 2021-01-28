package org.motoc.gamelibrary;

import org.junit.jupiter.api.Test;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class GameLibraryApplicationTests {

    @Test
    void contextLoads() {
    }

    @SpyBean
    CommandLineRunner runner;

    @Test
    void whenContextLoads_thenRunnerRun() throws Exception {
        verify(runner, times(1)).run();
    }
}
