package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.model.Category;
import org.motoc.gamelibrary.repository.fragment.CategoryFragmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository takes advantage of Spring data.sql / JPA
 */
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryFragmentRepository {
}
