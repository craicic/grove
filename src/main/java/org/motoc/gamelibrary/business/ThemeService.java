package org.motoc.gamelibrary.business;

import org.motoc.gamelibrary.business.refactor.SimpleCrudMethodsImpl;
import org.motoc.gamelibrary.model.Theme;
import org.motoc.gamelibrary.repository.ThemeRepository;
import org.motoc.gamelibrary.repository.ThemeRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Perform business logic on the web entity Theme
 *
 * @author RouzicJ
 */
@Service
@Transactional
public class ThemeService extends SimpleCrudMethodsImpl<Theme, JpaRepository<Theme, Long>> {

    private static final Logger logger = LoggerFactory.getLogger(ThemeService.class);

    private final ThemeRepository themeRepository;

    private final ThemeRepositoryCustom themeRepositoryCustom;


    @Autowired
    public ThemeService(ThemeRepository themeRepository, ThemeRepositoryCustom themeRepositoryCustom,
                        JpaRepository<Theme, Long> themeGenericRepository) {
        super(themeGenericRepository, Theme.class);
        this.themeRepository = themeRepository;
        this.themeRepositoryCustom = themeRepositoryCustom;
    }

    // Methods

    /**
     * Calls the DAO to edit a theme by id
     */
    public Theme edit(Theme theme, Long id) {
        return themeRepository.findById(id)
                .map(themeFromPersistence -> {
                    themeFromPersistence.setName(theme.getName());
                    logger.debug("Found theme of id={} : {}", id, themeFromPersistence);
                    return themeRepository.save(themeFromPersistence);
                })
                .orElseGet(() -> {
                    theme.setId(id);
                    logger.debug("No theme of id={} found. Set theme : {}", id, theme);
                    return themeRepository.save(theme);
                });
    }

    /**
     * Calls the DAO to delete a theme by id
     */
    public void remove(Long id) {
        logger.debug("deleting (if exist) theme of id=" + id);
        themeRepositoryCustom.remove(id);
    }
}
