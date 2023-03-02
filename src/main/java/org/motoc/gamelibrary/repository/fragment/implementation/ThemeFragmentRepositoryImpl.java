package org.motoc.gamelibrary.repository.fragment.implementation;

import org.motoc.gamelibrary.domain.model.Game;
import org.motoc.gamelibrary.domain.model.Theme;
import org.motoc.gamelibrary.repository.fragment.ThemeFragmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * It's the theme custom repository implementation, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
@Repository
public class ThemeFragmentRepositoryImpl implements ThemeFragmentRepository {

    private static final Logger logger = LoggerFactory.getLogger(ThemeFragmentRepositoryImpl.class);

    private final EntityManager em;


    @Autowired
    public ThemeFragmentRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public void remove(Long id) {

        Theme theme = em.find(Theme.class, id);
        if (theme != null) {
            for (Game game : theme.getGames()) {
                game.removeTheme(theme);
            }
            em.remove(theme);
            logger.debug("Entity Manager will now handle deletion for the theme of id={}", id);
        } else
            logger.info("Tried to delete, but theme of id={} doesn't exist", id);
    }

    @Override
    @Transactional
    public Theme saveTheme(Theme theme) {
        em.persist(theme);
        if (theme != null && theme.getId() != null) {
            logger.debug("Entity Manager will now handle persistence for the theme of name={} and id={}", theme.getName(), theme.getId());
        } else {
            logger.info("Tried to persist a theme but an error occurred");
        }
        return theme;
    }

}
