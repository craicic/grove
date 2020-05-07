package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.motoc.gamelibrary.model.enumartion.AgeEnum;
import org.motoc.gamelibrary.validation.annotation.ConsistentNumberOfPlayer;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * A game, it's a generic description of the item, not of a copy
 *
 * @author RouzicJ
 */
@ConsistentNumberOfPlayer
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    public Game coreGame;

    @NotBlank(message = "Name cannot be null or blank")
    @Column(nullable = false)
    private String name;

    @Size(max = 1000, message = "Description should not exceed 1000 characters")
    @Column(length = 1000)
    private String description;

    @Size(max = 20, message = "Description should not exceed 20 characters")
    @Column(length = 20)
    private String playTime;

    @Size(min = 1, max = 100, message = "Min number of players must be between 1 and 100")
    private short minNumberOfPlayer;

    @Size(min = 1, max = 100, message = "Max number of players must be between 1 and 100")
    private short maxNumberOfPlayer;

    @Column(nullable = false)
    private AgeEnum ageMin;
    private AgeEnum ageMax;

    @Size(max = 1000, message = "Stuff should not exceed 1000 characters")
    @Column(length = 1000)
    private String stuff;

    @Size(max = 1000, message = "Preparation should not exceed 1000 characters")
    @Column(length = 1000)
    private String preparation;

    @Size(max = 1000, message = "Goal should not exceed 1000 characters")
    @Column(length = 1000)
    private String goal;

    @Size(max = 15000, message = "Core rules should not exceed 1000 characters")
    @Lob
    private String coreRules;

    @Size(max = 15000, message = "Variant should not exceed 1000 characters")
    @Lob
    private String variant;

    @Size(max = 15000, message = "Ending rules should not exceed 1000 characters")
    @Lob
    private String ending;

    @Size(max = 50, message = "Nature rules should not exceed 50 characters")
    @Column(length = 50)
    private String nature;

    private String size;
    @Column(nullable = false)
    private boolean isBoardGame;

    private String editionNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_product_line")
    private ProductLine productLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_publisher")
    private Publisher publisher;

    @ManyToMany
    @JoinTable(
            name = "game_image",
            joinColumns = {@JoinColumn(name = "fk_game")},
            inverseJoinColumns = {@JoinColumn(name = "fk_image")})
    private Set<Image> images = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "game_author",
            joinColumns = {@JoinColumn(name = "fk_game")},
            inverseJoinColumns = {@JoinColumn(name = "fk_author")})
    private Set<Author> authors = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "game_category",
            joinColumns = {@JoinColumn(name = "fk_game")},
            inverseJoinColumns = {@JoinColumn(name = "fk_category")})
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "game")
    private Set<GameCopy> gameCopies = new HashSet<>();

    // Helper methods
    public void addImage(Image image) {
        this.images.add(image);
        image.getGames().add(this);
    }

    public void removeImage(Image image) {
        this.images.remove(image);
        image.getGames().remove(this);
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
        author.getGames().add(this);
    }

    public void removeAuthor(Author author) {
        this.authors.remove(author);
        author.getGames().remove(this);
    }

    public void addCategory(Category category) {
        this.categories.add(category);
        category.getGames().add(this);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
        category.getGames().remove(this);
    }

    public void addGameCopy(GameCopy gameCopy) {
        this.gameCopies.add(gameCopy);
        gameCopy.setGame(this);
    }
}
