package org.motoc.gamelibrary.domain.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * A game category
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "lower_case_name", name = "unique_cat"))
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Name cannot be null or blank")
    @Size(max = 50, message = "Name cannot exceed 50")
    @Column(nullable = false, length = 50)
    private String name;

    @ToString.Exclude
    @Column(name = "lower_case_name", nullable = false, length = 50)
    private String lowerCaseName;


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "categories")
    private Set<Game> games = new HashSet<>();

    // Helper methods

    /**
     * Adding a case-insensitive entry in database
     */
    @PrePersist
    @PreUpdate
    public void toLowerCase() {
        this.lowerCaseName = name != null ? name.toLowerCase() : null;
    }

    public void addGame(Game game) {
        games.add(game);
        game.getCategories().add(this);
    }

    public void removeGame(Game game) {
        games.remove(game);
        game.getCategories().remove(this);
    }
}

