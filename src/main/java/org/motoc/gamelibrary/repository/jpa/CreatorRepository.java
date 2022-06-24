package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.model.Creator;
import org.motoc.gamelibrary.repository.fragment.CreatorFragmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * This repository takes advantage of Spring data / JPA
 */
public interface CreatorRepository extends JpaRepository<Creator, Long>, CreatorFragmentRepository {
    Page<Creator> findByLowerCaseFirstNameContainingOrLowerCaseLastNameContaining(String keyword,
                                                                                  String repeatKeyword,
                                                                                  Pageable pageable);

    @Query("SELECT c FROM Creator AS c WHERE LOWER(CONCAT(c.firstName, c.lastName)) LIKE :name")
    Creator findByFullName(@Param("name") String name);
}
