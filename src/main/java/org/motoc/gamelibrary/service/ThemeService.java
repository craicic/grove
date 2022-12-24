package org.motoc.gamelibrary.service;

import org.motoc.gamelibrary.domain.model.Theme;
import org.motoc.gamelibrary.repository.jpa.ThemeRepository;
import org.motoc.gamelibrary.service.refactor.SimpleCrudMethodsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

/**
 * Perform service logic on the web entity Theme
 */
@Service
@Transactional
public class ThemeService extends SimpleCrudMethodsImpl<Theme, JpaRepository<Theme, Long>> {

    private static final Logger logger = LoggerFactory.getLogger(ThemeService.class);

    private final ThemeRepository themeRepository;


    @Autowired
    public ThemeService(ThemeRepository themeRepository,
                        JpaRepository<Theme, Long> themeGenericRepository) {
        super(themeGenericRepository, Theme.class);
        this.themeRepository = themeRepository;
    }

    // Methods

    /**
     * Calls the DAO to edit a new theme (id should be null)
     */
    public Theme save(@Valid Theme theme) {
        if (theme.getId() == null) {
            logger.debug("Trying to save new theme={}", theme.getName());
        } else {
            logger.debug("Trying to save new theme={} of id={}", theme.getName(), theme.getId());
        }
        return themeRepository.saveTheme(theme);
    }

    /**
     * Calls the DAO to edit a theme by id
     */
    public Theme edit(@Valid Theme theme, Long id) {
        return themeRepository.findById(id)
                .map(themeFromPersistence -> {
                    themeFromPersistence.setName(theme.getName());
                    logger.debug("Found theme of id={} : {}", id, themeFromPersistence);
                    return themeRepository.saveTheme(themeFromPersistence);
                })
                .orElseGet(() -> {
                    theme.setId(id);
                    logger.debug("No theme of id={} found. Set theme : {}", id, theme);
                    return themeRepository.saveTheme(theme);
                });
    }

    /**
     * Calls the DAO to delete a theme by id
     */
    public void remove(Long id) {
        logger.debug("Deleting (if exist) theme of id=" + id);
        themeRepository.remove(id);
    }

    /**
     * Calls the DAO to performs a paged search on theme
     */
    public Page<Theme> quickSearch(String keyword, Pageable pageable) {
        logger.debug("Find all theme that contains : " + keyword);
        return themeRepository.findByLowerCaseNameContaining(keyword, pageable);
    }

    /**
     * Calls the DAO to search all themes
     */
    public List<Theme> findAll() {
        logger.debug("Find all themes");
        return themeRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }
}
