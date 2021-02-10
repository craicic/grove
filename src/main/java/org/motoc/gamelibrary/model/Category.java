package org.motoc.gamelibrary.model;

import lombok.*;
import org.motoc.gamelibrary.validation.annotation.EitherChildOrParent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
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
@Table(name = "category", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "lower_case_name"))
@EitherChildOrParent
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotBlank(message = "Name cannot be null or blank")
    @Size(max = 50, message = "Name cannot exceed 50")
    @Column(nullable = false, length = 50)
    private String name;

    @ToString.Exclude
    @Column(name = "lower_case_name", nullable = false, length = 50)
    private String lowerCaseName;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Category parent;


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "parent")
    private Set<Category> children = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "categories")
    private Set<Game> games = new HashSet<>();

    // Overridden accessors
    public void setName(String name) {
        this.name = name;
        this.lowerCaseName = name.toLowerCase();
    }

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

    public void removeParent() {
        if (this.getParent() != null) {
            this.getParent().getChildren().remove(this);
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
}

