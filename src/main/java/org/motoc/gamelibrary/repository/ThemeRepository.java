package org.motoc.gamelibrary.repository;

import org.motoc.gamelibrary.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
}
