package org.motoc.gamelibrary.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Article's image : store a file path
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "filePath"))
public class Image {

    /**
     * <p>Chosen strategy is SEQUENCE</p>
     * <a href>https://thoughts-on-java.org/jpa-generate-primary-keys/</a>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotBlank(message = "File path cannot be null or blank")
    @Size(max = 4251, message = "File path cannot exceed 4251 characters")
    @Column(nullable = false)
    private String filePath;

    @ManyToMany(mappedBy = "images")
    private Set<Article> articles = new HashSet<>();

    @ManyToMany(mappedBy = "images")
    private Set<Game> games = new HashSet<>();

    // Helper methods

    public void addArticle(Article article) {
        articles.add(article);
        article.getImages().add(this);
    }

    public void removeArticle(Article article) {
        articles.remove(article);
        article.getImages().remove(this);
    }

    public void addGame(Game game) {
        games.add(game);
        game.getImages().add(this);
    }

    public void removeGame(Game game) {
        games.remove(game);
        game.getImages().remove(this);
    }

}
