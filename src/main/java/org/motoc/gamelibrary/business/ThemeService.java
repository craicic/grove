package org.motoc.gamelibrary.business;

import org.motoc.gamelibrary.model.Theme;
import org.motoc.gamelibrary.repository.ThemeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Perform business logic on the web entity Theme
 *
 * @author RouzicJ
 */
@Service
@Transactional
public class ThemeService {

    private static final Logger logger = LoggerFactory.getLogger(ThemeService.class);

    private final ThemeRepository themeRepository;

    @Autowired
    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public void persist(Theme theme) {
        themeRepository.saveAndFlush(theme);
    }

    public long count() {
        return themeRepository.count();
    }

    public Theme findOne(Theme theme) {
        if (theme != null)
            return themeRepository.getOne(theme.getId());
        return null;
    }

    public List<Theme> findAll() {
        return themeRepository.findAll();
    }

    public Page<Theme> findPage(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(
                Sort.Order.asc("name")
        ));

        return themeRepository.findAll(pageable);
    }

    public void deleteOne(Theme theme) {
        themeRepository.delete(theme);
    }

}
