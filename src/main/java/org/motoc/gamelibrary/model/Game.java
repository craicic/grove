package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.motoc.gamelibrary.model.enumeration.GameNatureEnum;
import org.motoc.gamelibrary.validation.annotation.ConsistentAgeRange;
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
@ConsistentAgeRange
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    /**
     * Core game, if this game is an extension
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Game coreGame;

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
    @Column(nullable = false)
    private short minNumberOfPlayer;

    @Size(min = 1, max = 100, message = "Max number of players must be between 1 and 100")
    private short maxNumberOfPlayer;

    @Size(min = 1, max = 100, message = "Min age must be between 1 and 100")
    private short minAge;

    @Size(min = 1, max = 100, message = "Max age must be between 1 and 100")
    private short maxAge;

    @Size(min = 1, max = 100, message = "Min months must be between 1 and 100")
    private short minMonths;

    /**
     * Stuff the game contains (parts, meeples, cards, etc...)
     */
    @Size(max = 1000, message = "Stuff should not exceed 1000 characters")
    @Column(length = 1000)
    private String stuff;

    /**
     * Description how to prepare stuff before the game starts
     */
    @Size(max = 15000, message = "Preparation should not exceed 15000 characters")
    @Lob
    private String preparation;

    /**
     * Gaol = win condition
     */
    @Size(max = 1000, message = "Goal should not exceed 1000 characters")
    @Column(length = 1000)
    private String goal;

    @Size(max = 15000, message = "Core rules should not exceed 15000 characters")
    @Lob
    private String coreRules;

    /**
     * Describes alternative version of the rules
     */
    @Size(max = 15000, message = "Variant should not exceed 15000 characters")
    @Lob
    private String variant;

    /**
     * Generally describes the last turn and how to
     */
    @Size(max = 15000, message = "Ending rules should not exceed 15000 characters")
    @Lob
    private String ending;

    /**
     * An enumeration : toy, board game, wooden game, etc...
     */
    @Size(max = 50, message = "Nature rules should not exceed 50 characters")
    @Column(length = 50)
    private GameNatureEnum nature;

    /**
     * The size of the game
     */
    private String size;

    private String editionNumber;

    /**
     * The product line of the game, if the game is part of a 'collection'
     */
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
            name = "game_creator",
            joinColumns = {@JoinColumn(name = "fk_game")},
            inverseJoinColumns = {@JoinColumn(name = "fk_creator")})
    private Set<Creator> creators = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "game_category",
            joinColumns = {@JoinColumn(name = "fk_game")},
            inverseJoinColumns = {@JoinColumn(name = "fk_category")})
    private Set<Category> categories = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "game_theme",
            joinColumns = {@JoinColumn(name = "fk_game")},
            inverseJoinColumns = {@JoinColumn(name = "fk_theme")})
    private Set<Theme> themes = new HashSet<>();

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

    public void addCreator(Creator creator) {
        this.creators.add(creator);
        creator.getGames().add(this);
    }

    public void removeCreator(Creator creator) {
        this.creators.remove(creator);
        creator.getGames().remove(this);
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

    public void addTheme(Theme theme) {
        this.themes.add(theme);
        theme.getGames().add(this);
    }

    public void removeTheme(Theme theme) {
        this.themes.remove(theme);
        theme.getGames().remove(this);
    }
    // I think removeGameCopy is not needed, because the relationship is not optional

}
