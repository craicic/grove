package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.domain.model.Mechanism;
import org.motoc.gamelibrary.repository.fragment.MechanismFragmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository takes advantage of Spring data.sql / JPA
 */
public interface MechanismRepository extends JpaRepository<Mechanism, Long>, MechanismFragmentRepository {

    Page<Mechanism> findByLowerCaseTitleContaining(String name, Pageable pageable);
}
