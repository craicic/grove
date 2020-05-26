package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A blog article
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Article {

    /**
     * <p>Chosen strategy is SEQUENCE</p>
     * <a href>https://thoughts-on-java.org/jpa-generate-primary-keys/</a>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull(message = "Publication time cannot be null")
    @PastOrPresent(message = "Publication time must be in the past or the present")
    @Column(nullable = false)
    private LocalDateTime publicationTime;

    @PastOrPresent(message = "Last edit time must be in the past or the present")
    private LocalDateTime lastEditTime;

    /**
     * <p>Saved as TEXT type in postgres db</p>
     * <a href>https://www.baeldung.com/jpa-annotation-postgresql-text-type</a>
     */
    @NotBlank(message = "Html content cannot be null or blank")
    @Size(max = 15000, message = "Html content should not exceed 15000 characters")
    @Lob
    @Column(nullable = false)
    private String htmlContent;

    @NotNull(message = "Short description cannot be null")
    @Size(max = 255)
    private String shortDescription;

    @ManyToMany
    @JoinTable(
            name = "article_keyword",
            joinColumns = {@JoinColumn(name = "fk_article")},
            inverseJoinColumns = {@JoinColumn(name = "fk_keyword")})
    private Set<Keyword> keywords = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "article_image",
            joinColumns = {@JoinColumn(name = "fk_article")},
            inverseJoinColumns = {@JoinColumn(name = "fk_image")})
    private Set<Image> images = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_article_author")
    private ArticleAuthor articleAuthor;

    // Helper methods

    public void addKeyword(Keyword keyword) {
        this.keywords.add(keyword);
        keyword.getArticles().add(this);
    }

    public void removeKeyword(Keyword keyword) {
        this.keywords.remove(keyword);
        keyword.getArticles().remove(this);
    }

    public void addImage(Image image) {
        this.images.add(image);
        image.getArticles().add(this);
    }

    public void removeImage(Image image) {
        this.images.remove(image);
        image.getArticles().remove(this);
    }

    public void addArticleAuthor(ArticleAuthor articleAuthor) {
        this.setArticleAuthor(articleAuthor);
        articleAuthor.getArticles().add(this);
    }

    public void removeArticleAuthor(ArticleAuthor articleAuthor) {
        this.setArticleAuthor(null);
        articleAuthor.getArticles().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id == article.id &&
                publicationTime.equals(article.publicationTime) &&
                Objects.equals(lastEditTime, article.lastEditTime) &&
                htmlContent.equals(article.htmlContent) &&
                shortDescription.equals(article.shortDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, publicationTime, lastEditTime, htmlContent, shortDescription);
    }
}
