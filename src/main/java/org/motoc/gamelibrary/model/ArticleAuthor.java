package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

/**
 * The creator of articles
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "article_author", schema = "public")
public class ArticleAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotBlank(message = "User uuid cannot be null or blank")
    @Size(max = 50, message = "User uuid should not exceed 50 characters")
    @Column(nullable = false, length = 50)
    private String userUuid;

    @OneToMany(mappedBy = "articleAuthor")
    private Set<Article> articles;

    // Helper methods

    /**
     * <p>Helper method for bi-directional association</p>
     * <a href>https://thoughts-on-java.org/best-practices-many-one-one-many-associations-mappings/</a>
     */
    public void addArticle(Article article) {
        this.articles.add(article);
        article.setArticleAuthor(this);
    }

    public void removeArticle(Article article) {
        this.articles.add(article);
        article.setArticleAuthor(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleAuthor that = (ArticleAuthor) o;
        return id == that.id &&
                userUuid.equals(that.userUuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userUuid);
    }
}
