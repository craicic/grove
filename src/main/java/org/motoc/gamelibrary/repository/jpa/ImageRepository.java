package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.model.Image;
import org.motoc.gamelibrary.repository.fragment.ImageFragmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository takes advantage of Spring data / JPA
 */
public interface ImageRepository extends JpaRepository<Image, Long>, ImageFragmentRepository {
}
