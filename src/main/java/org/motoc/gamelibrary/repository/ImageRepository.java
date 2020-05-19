package org.motoc.gamelibrary.repository;

import org.motoc.gamelibrary.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
