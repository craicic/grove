package org.motoc.gamelibrary.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.motoc.gamelibrary.repository.jpa.MechanismRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MechanismServiceTest {

    @Mock
    MechanismRepository mechanismRepository;

    @InjectMocks
    MechanismService mechanismService;

    @Test
    void count() {
        when(mechanismRepository.count()).thenReturn(5L);

        assertThat(mechanismService.count()).isEqualTo(5L);
    }

    @Test
    void findById_NotFoundException() {
        final long id = 4L;

        when(mechanismRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            mechanismService.findById(id);
        }).hasMessageContaining("No mechanism of id=" + id + " found.");
    }
}