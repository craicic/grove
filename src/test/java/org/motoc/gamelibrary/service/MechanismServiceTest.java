package org.motoc.gamelibrary.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.motoc.gamelibrary.domain.model.Mechanism;
import org.motoc.gamelibrary.repository.jpa.MechanismRepository;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
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
    void save() {
        final String mechanismTitle = "Aventure";
        Mechanism toPersist = new Mechanism();
        toPersist.setTitle(mechanismTitle);

        Mechanism toReturn = new Mechanism();
        toReturn.setId(5L);
        toReturn.setTitle(mechanismTitle);

        when(mechanismRepository.saveMechanism(toPersist)).thenReturn(toReturn);

        assertThat(mechanismService.save(toPersist)).isSameAs(toReturn);
    }

    @Test
    void findById() {

        final long id = 4L;

        Mechanism mechanism = new Mechanism();
        mechanism.setId(id);
        mechanism.setTitle("Aventure");

        when(mechanismRepository.findById(id)).thenReturn(Optional.of(mechanism));

        assertThat(mechanismService.findById(id)).isSameAs(mechanism);
    }

    @Test
    void findById_NotFoundException() {
        final long id = 4L;

        when(mechanismRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            mechanismService.findById(id);
        }).hasMessageContaining("No mechanism of id=" + id + " found.");
    }

    @Test
    void findPage() {
        final Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Order.asc("title")));

        Mechanism mechanismA = new Mechanism();
        mechanismA.setId(5L);
        mechanismA.setTitle("Aventure");

        Mechanism mechanismB = new Mechanism();
        mechanismB.setId(7L);
        mechanismB.setTitle("Médiéval");

        List<Mechanism> mechanisms = List.of(mechanismA, mechanismB);
        Page<Mechanism> pageToReturn = new PageImpl<>(mechanisms, pageable, mechanisms.size());

        when(mechanismRepository.findAll(pageable)).thenReturn(pageToReturn);

        assertThat(mechanismService.findPage(pageable)).isSameAs(pageToReturn);
    }

    @Test
    void edit_ShouldReplace() {
        final long id = 1L;
        final String name = "Antiquité";
        Mechanism mechanism = new Mechanism();
        mechanism.setId(id);
        mechanism.setTitle(name);

        when(mechanismRepository.findById(id)).thenReturn(Optional.of(mechanism));
        when(mechanismRepository.saveMechanism(mechanism)).thenReturn(mechanism);

        assertThat(mechanismService.edit(mechanism, id)).isEqualTo(mechanism);
    }

    @Test
    void edit_ShouldCreate() {
        final long id = 1L;
        final String name = "Antiquité";
        Mechanism mechanism = new Mechanism();
        mechanism.setTitle(name);

        when(mechanismRepository.findById(id)).thenReturn(Optional.empty());
        when(mechanismRepository.saveMechanism(mechanism)).thenReturn(mechanism);

        assertThat(mechanismService.edit(mechanism, id)).isEqualTo(mechanism);
    }
}