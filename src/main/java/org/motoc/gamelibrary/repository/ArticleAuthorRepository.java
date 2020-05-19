package org.motoc.gamelibrary.repository;

import org.motoc.gamelibrary.model.ArticleAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleAuthorRepository extends JpaRepository<ArticleAuthor, Long> {
}
