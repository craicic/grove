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
public class ThemeService extends CrudMethodsImpl<Theme, JpaRepository<Theme, Long>>{

    private static final Logger logger = LoggerFactory.getLogger(ThemeService.class);

    private final ThemeRepository themeRepository;

    @Autowired
    public ThemeService(ThemeRepository themeRepository, JpaRepository<Theme, Long> themeGenericRepository) {
        super(themeGenericRepository);
        this.themeRepository = themeRepository;
    }
//
//    public Theme persist(Theme theme) {
//        Theme result = themeRepository.saveAndFlush(theme);
//        logger.debug("Persisted theme : " + result);
//        return result;
//    }
//
//    public long count() {
//        long result = themeRepository.count();
//        logger.debug("Counting theme(s)=" + result);
//        return result;
//    }
//
//    public Theme findOne(Theme theme) {
//        if (theme != null) {
//            Theme result = themeRepository.getOne(theme.getId());
//            logger.debug("Found theme : " + result);
//            return result;
//        }
//        logger.warn("Theme can't be null");
//        return null;
//    }
//
//    public Page<Theme> findPage(Pageable pageable) {
//        Page<Theme> result = themeRepository.findAll(pageable);
//        logger.debug("Found {} element(s) through {} page(s)", result.getTotalElements(), result.getTotalPages());
//        return result;
//    }
//
//    public void deleteOne(Theme theme) {
//        themeRepository.delete(theme);
//        logger.debug("Deleted theme : " + theme);
//    }

}
