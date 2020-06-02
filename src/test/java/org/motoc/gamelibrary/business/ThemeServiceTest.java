package org.motoc.gamelibrary.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.motoc.gamelibrary.model.Theme;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ThemeServiceTest {

    @Mock
    JpaRepository<Theme, Long> themeRepository;

    @InjectMocks
    ThemeService themeService;

    @Test
    void count() {
        when(themeRepository.count()).thenReturn(5L);

        assertThat(themeService.count()).isEqualTo(5L);
    }

    @Test
    void save() {
        String themeName = "Aventure";
        Theme toPersist = new Theme();
        toPersist.setName(themeName);

        Theme toReturn = new Theme();
        toReturn.setId(5L);
        toReturn.setName(themeName);

        when(themeRepository.saveAndFlush(toPersist)).thenReturn(toReturn);

        assertThat(themeService.save(toPersist)).isSameAs(toReturn);
    }

    @Test
    void findById() {

        long id = 4L;

        Theme theme = new Theme();
        theme.setId(id);
        theme.setName("Aventure");

        Optional<Theme> toReturn = Optional.of(theme);


        when(themeRepository.findById(4L)).thenReturn(toReturn);

        assertThat(themeService.findById(id)).isSameAs(theme);
    }

    @Test
    void findByIdNotFound() {
        // TODO merge
    }

    @Test
    void findPage() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Order.asc("name")));

        Theme themeA = new Theme();
        themeA.setId(5L);
        themeA.setName("Aventure");

        Theme themeB = new Theme();
        themeB.setId(7L);
        themeB.setName("Médiéval");

        List<Theme> themes = List.of(themeA, themeB);
        Page<Theme> pageToReturn = new PageImpl<>(themes, pageable, themes.size());

        when(themeRepository.findAll(pageable)).thenReturn(pageToReturn);

        assertThat(themeService.findPage(pageable)).isSameAs(pageToReturn);
    }
}