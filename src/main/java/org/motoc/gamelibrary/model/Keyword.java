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
 * Article's keyword for searching purpose
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "keyword", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "tag"))
public class Keyword {

    /**
     * <p>Chosen strategy is SEQUENCE</p>
     * <a href>https://thoughts-on-java.org/jpa-generate-primary-keys/</a>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotBlank(message = "Tag cannot be null or blank")
    @Size(max = 50, message = "Tag cannot exceed 50 characters")
    @Column(nullable = false, length = 50)
    private String tag;

    @ManyToMany(mappedBy = "keywords")
    private Set<Article> articles;

    // Helper methods

    public void addArticle(Article article) {
        articles.add(article);
        article.getKeywords().add(this);
    }

    public void removeArticle(Article article) {
        articles.remove(article);
        article.getKeywords().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Keyword keyword = (Keyword) o;
        return id == keyword.id &&
                tag.equals(keyword.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tag);
    }
}
