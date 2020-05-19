package org.motoc.gamelibrary.repository;

import org.motoc.gamelibrary.model.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLineRepository extends JpaRepository<ProductLine, Long> {
}
