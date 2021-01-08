package org.motoc.gamelibrary.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
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
@Table(name = "image", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "file_path"))
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
    @Column(name = "file_path", nullable = false)
    private String filePath;

    @ManyToMany(mappedBy = "images")
    private Set<Game> games = new HashSet<>();

    // Helper methods

    public void addGame(Game game) {
        games.add(game);
        game.getImages().add(this);
    }

    public void removeGame(Game game) {
        games.remove(game);
        game.getImages().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return id == image.id &&
                filePath.equals(image.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filePath);
    }
}
