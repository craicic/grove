package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.model.Creator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository takes advantage of Spring data / JPA
 *
 * @author RouzicJ
 */
public interface CreatorRepository extends JpaRepository<Creator, Long> {
    Page<Creator> findByLowerCaseFirstNameContainingOrLowerCaseLastNameContaining(String keyword,
                                                                                  String repeatKeyword,
                                                                                  Pageable pageable);
}
