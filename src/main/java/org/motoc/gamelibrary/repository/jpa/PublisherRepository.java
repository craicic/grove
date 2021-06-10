package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.model.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository takes advantage of Spring data / JPA
 */
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Page<Publisher> findByLowerCaseNameContaining(String keyword,
                                                  Pageable pageable);
}
