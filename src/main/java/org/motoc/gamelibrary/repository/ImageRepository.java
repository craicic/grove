package org.motoc.gamelibrary.repository;

import org.motoc.gamelibrary.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This repository takes advantage of Spring data / JPA
 *
 * @author RouzicJ
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
