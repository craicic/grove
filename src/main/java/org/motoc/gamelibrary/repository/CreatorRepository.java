package org.motoc.gamelibrary.repository;

import org.motoc.gamelibrary.model.Creator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatorRepository extends JpaRepository<Creator, Long> {
}
