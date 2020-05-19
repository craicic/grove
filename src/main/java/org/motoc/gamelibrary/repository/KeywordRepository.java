package org.motoc.gamelibrary.repository;

import org.motoc.gamelibrary.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}
