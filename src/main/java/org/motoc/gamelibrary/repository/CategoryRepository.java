package org.motoc.gamelibrary.repository;

import org.motoc.gamelibrary.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
