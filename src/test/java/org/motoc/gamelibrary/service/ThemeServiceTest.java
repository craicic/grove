package org.motoc.gamelibrary.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.motoc.gamelibrary.domain.model.Theme;
import org.motoc.gamelibrary.repository.jpa.ThemeRepository;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ThemeServiceTest {

    @Mock
    ThemeRepository themeRepository;

    @InjectMocks
    ThemeService themeService;

    @Test
    void count() {
        when(themeRepository.count()).thenReturn(5L);

        assertThat(themeService.count()).isEqualTo(5L);
    }

    @Test
    void save() {
        final String themeName = "Aventure";
        Theme toPersist = new Theme();
        toPersist.setName(themeName);

        Theme toReturn = new Theme();
        toReturn.setId(5L);
        toReturn.setName(themeName);

        when(themeRepository.save(toPersist)).thenReturn(toReturn);

        assertThat(themeService.save(toPersist)).isSameAs(toReturn);
    }

    @Test
    void findById() {

        final long id = 4L;

        Theme theme = new Theme();
        theme.setId(id);
        theme.setName("Aventure");

        when(themeRepository.findById(id)).thenReturn(Optional.of(theme));

        assertThat(themeService.findById(id)).isSameAs(theme);
    }

    @Test
    void findById_NotFoundException() {
        final long id = 4L;

        when(themeRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            themeService.findById(id);
        }).hasMessageContaining("No theme of id=" + id + " found.");
    }

    @Test
    void findPage() {
        final Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Order.asc("name")));

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

    @Test
    void edit_ShouldReplace() {
        final long id = 1L;
        final String name = "Antiquité";
        Theme theme = new Theme();
        theme.setId(id);
        theme.setName(name);

        when(themeRepository.findById(id)).thenReturn(Optional.of(theme));
        when(themeRepository.saveTheme(theme)).thenReturn(theme);

        assertThat(themeService.edit(theme, id)).isEqualTo(theme);
    }

    @Test
    void edit_ShouldCreate() {
        final long id = 1L;
        final String name = "Antiquité";
        Theme theme = new Theme();
        theme.setName(name);

        when(themeRepository.findById(id)).thenReturn(Optional.empty());
        when(themeRepository.saveTheme(theme)).thenReturn(theme);

        assertThat(themeService.edit(theme, id)).isEqualTo(theme);
    }
}