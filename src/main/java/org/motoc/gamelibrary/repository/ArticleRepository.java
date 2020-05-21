package org.motoc.gamelibrary.repository;

import org.motoc.gamelibrary.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
