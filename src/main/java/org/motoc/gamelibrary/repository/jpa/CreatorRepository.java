package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.model.Creator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This repository takes advantage of Spring data / JPA
 *
 * @author RouzicJ
 */
@Repository
public interface CreatorRepository extends JpaRepository<Creator, Long> {
}
