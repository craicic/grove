package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.domain.model.Theme;
import org.motoc.gamelibrary.repository.fragment.ThemeFragmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository takes advantage of Spring data.sql / JPA
 */
public interface ThemeRepository extends JpaRepository<Theme, Long>, ThemeFragmentRepository {

    Page<Theme> findByLowerCaseNameContaining(String name, Pageable pageable);
}
