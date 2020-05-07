package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * The author of articles
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
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

    /**
     * <p>Helper method for bi-directional association</p>
     * <a href>https://thoughts-on-java.org/best-practices-many-one-one-many-associations-mappings/</a>
     */
    public void addArticle(Article article) {
        this.articles.add(article);
        article.setArticleAuthor(this);
    }

}
