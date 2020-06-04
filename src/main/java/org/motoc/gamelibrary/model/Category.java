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
 * A game category
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotBlank(message = "Name cannot be null or blank")
    @Size(max = 50, message = "Name cannot exceed 50")
    @Column(nullable = false, length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category parent;

    @ToString.Exclude
    @OneToMany(mappedBy = "parent")
    private Set<Category> children = new HashSet<>();

    @ToString.Exclude
    @ManyToMany(mappedBy = "categories")
    private Set<Game> games = new HashSet<>();

    // Helper methods

    public void addGame(Game game) {
        games.add(game);
        game.getCategories().add(this);
    }

    public void removeGame(Game game) {
        games.remove(game);
        game.getCategories().remove(this);
    }

    public void addParent(Category parent) {
        parent.getChildren().add(this);
        this.setParent(parent);
    }

    public void removeParent(Category parent) {
        if (parent != null) {
            parent.getChildren().remove(this);
            this.setParent(null);
        }
    }

    public void addChild(Category child) {
        child.setParent(this);
        this.getChildren().add(child);
    }

    public void removeChild(Category child) {
        child.setParent(null);
        this.getChildren().remove(child);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id &&
                name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
