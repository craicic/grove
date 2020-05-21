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
    JpaRepository<Theme, Long> themeGenericRepository;
    @InjectMocks
    ThemeService themeService;

    @Test
    void testCount() {
        when(themeGenericRepository.count()).thenReturn(5L);

        assertThat(themeService.count(Theme.class)).isEqualTo(5L);
    }

    @Test
    void testPersist() {
        String themeName = "Aventure";
        Theme toPersist = new Theme();
        toPersist.setName(themeName);

        Theme toReturn = new Theme();
        toReturn.setId(5L);
        toReturn.setName(themeName);

        when(themeGenericRepository.saveAndFlush(toPersist)).thenReturn(toReturn);

        assertThat(themeService.persist(toPersist)).isSameAs(toReturn);
    }

    @Test
    void testFindById(){

        long id = 4L;

        Theme theme = new Theme();
        theme.setId(id);
        theme.setName("Aventure");

        Optional<Theme> toReturn = Optional.of(theme);


        when(themeGenericRepository.findById(4L)).thenReturn(toReturn);

        assertThat(themeService.findById(id, Theme.class)).isSameAs(theme);
    }

    @Test
    void testFindByIdNotFound() {
        long id = 4L;

        when(themeGenericRepository.findById(4L)).thenReturn(Optional.empty());

        assertThat(themeService.findById(id, Theme.class)).isNull();
    }

    @Test
    void testFindPage() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Order.asc("name")));

        Theme themeA = new Theme();
        themeA.setId(5L);
        themeA.setName("Aventure");

        Theme themeB = new Theme();
        themeB.setId(7L);
        themeB.setName("Médiéval");

        List<Theme> themes = List.of(themeA, themeB);
        Page<Theme> pageToReturn = new PageImpl<>(themes, pageable, themes.size());

        when(themeGenericRepository.findAll(pageable)).thenReturn(pageToReturn);

        assertThat(themeService.findPage(pageable)).isSameAs(pageToReturn);
    }
}