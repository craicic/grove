package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.model.ProductLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository takes advantage of Spring data / JPA
 */
public interface ProductLineRepository extends JpaRepository<ProductLine, Long> {

    Page<ProductLine> findByLowerCaseNameContaining(String keyword, Pageable pageable);
}
