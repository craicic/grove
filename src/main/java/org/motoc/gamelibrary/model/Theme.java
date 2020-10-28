package org.motoc.gamelibrary.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A game theme
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "lowerCaseName"))
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotBlank(message = "Name cannot be null or blank")
    @Size(max = 50, message = "Name cannot exceed 50")
    @Column(nullable = false, length = 50)
    private String name;

    @ToString.Exclude
    @Column(nullable = false, length = 50)
    private String lowerCaseName;

    @ToString.Exclude
    @ManyToMany(mappedBy = "themes")
    private Set<Game> games = new HashSet<>();

    // Other constructors
    public Theme(long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Overridden accessors
    public void setName(String name) {
        this.name = name;
        this.lowerCaseName = name.toLowerCase();
    }

    // Helper methods
    public void addGame(Game game) {
        games.add(game);
        game.getThemes().add(this);
    }

    public void removeGame(Game game) {
        games.remove(game);
        game.getThemes().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Theme theme = (Theme) o;
        return id == theme.id &&
                name.equals(theme.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

