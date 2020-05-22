package org.motoc.gamelibrary.business;

import org.motoc.gamelibrary.business.refactor.CrudMethodsImpl;
import org.motoc.gamelibrary.model.Theme;
import org.motoc.gamelibrary.repository.ThemeRepository;
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
public class ThemeService extends CrudMethodsImpl<Theme, JpaRepository<Theme, Long>> {

    private static final Logger logger = LoggerFactory.getLogger(ThemeService.class);

    private final ThemeRepository themeRepository;


    @Autowired
    public ThemeService(ThemeRepository themeRepository, JpaRepository<Theme, Long> themeGenericRepository) {
        super(themeGenericRepository);
        this.themeRepository = themeRepository;
    }

}
