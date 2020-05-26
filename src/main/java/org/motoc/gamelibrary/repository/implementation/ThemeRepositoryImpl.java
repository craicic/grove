package org.motoc.gamelibrary.repository.implementation;

import org.motoc.gamelibrary.model.Game;
import org.motoc.gamelibrary.model.Theme;
import org.motoc.gamelibrary.repository.ThemeRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * It's the theme custom repository implementation, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
@Repository
public class ThemeRepositoryImpl implements ThemeRepositoryCustom {


    private final EntityManager entityManager;

    @Autowired
    public ThemeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void remove(Long id) {
        Theme theme = entityManager.find(Theme.class, id);
        for (Game game : theme.getGames()) {
            game.removeTheme(theme);
        }
        entityManager.remove(theme);
    }
}
