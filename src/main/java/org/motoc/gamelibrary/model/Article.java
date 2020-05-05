package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
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


    @Column(nullable = false)
    private LocalDateTime publicationTime;

    private LocalDateTime lastEditTime;

    /**
     * <p>Saved as TEXT type in postgres db</p>
     * <a href>https://www.baeldung.com/jpa-annotation-postgresql-text-type</a>
     */
    @Lob
    @Column(nullable = false)
    private String htmlContent;

    private String shortDescription;

    @ManyToMany
    @JoinTable(
            name = "article_keyword",
            joinColumns = { @JoinColumn(name = "fk_article") },
            inverseJoinColumns = { @JoinColumn(name = "fk_keyword") })
    private Set<Keyword> keywords = new HashSet<Keyword>();

    @ManyToMany
    @JoinTable(
            name = "article_image",
            joinColumns = { @JoinColumn(name = "fk_article") },
            inverseJoinColumns = { @JoinColumn(name = "fk_image") })
    private Set<Image> images = new HashSet<Image>();

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
}
