package org.motoc.gamelibrary.domain.model;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.motoc.gamelibrary.domain.enumeration.GameNature;
import org.motoc.gamelibrary.technical.validation.annotation.ConsistentAgeRange;
import org.motoc.gamelibrary.technical.validation.annotation.ConsistentNumberOfPlayer;
import org.motoc.gamelibrary.technical.validation.annotation.SelectYearOrMonth;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * A game, it's a generic description of the item, not of a copy
 */

@ConsistentNumberOfPlayer
@ConsistentAgeRange
@SelectYearOrMonth
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "game", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "lower_case_name"))
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_seq_gen")
    @SequenceGenerator(name = "game_seq_gen", sequenceName = "game_sequence", initialValue = 100)
    private Long id;

    /**
     * Core game, if this game is an extension
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Game coreGame;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "coreGame")
    private Set<Game> expansions = new HashSet<>();

    @NotBlank(message = "Name cannot be null or blank")
    @Column(nullable = false)
    private String name;

    @ToString.Exclude
    @Column(nullable = false, name = "lower_case_name")
    private String lowerCaseName;

    @Size(max = 1000, message = "Description should not exceed 1000 characters")
    @Column(length = 1000)
    private String description;

    @Size(max = 20, message = "Description should not exceed 20 characters")
    @Column(length = 20)
    private String playTime;

    @Range(min = 1, max = 100, message = "Min number of players must be between 1 and 100")
    @Column(nullable = false)
    private short minNumberOfPlayer;

    @Range(min = 0, max = 100, message = "Max number of players must be between 0 and 100")
    private short maxNumberOfPlayer;

    @Range(min = 0, max = 100, message = "Min age must be between 0 and 100")
    private short minAge;

    @Range(min = 0, max = 100, message = "Max age must be between 0 and 100")
    private short maxAge;

    @Range(min = 0, max = 100, message = "Min months must be between 0 and 100")
    private short minMonth;

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
    @Column(length = 50)
    private GameNature nature;

    /**
     * The size of the game
     */
    private String size;

    /**
     * The product line of the game, if the game is part of a 'collection'
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_product_line")
    private ProductLine productLine;

    @OneToMany(mappedBy = "game")
    private Set<Image> images = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
            name = "game_creator",
            joinColumns = {@JoinColumn(name = "fk_game")},
            inverseJoinColumns = {@JoinColumn(name = "fk_creator")})
    private Set<Creator> creators = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
            name = "game_category",
            joinColumns = {@JoinColumn(name = "fk_game")},
            inverseJoinColumns = {@JoinColumn(name = "fk_category")})
    private Set<Category> categories = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "game_theme",
            joinColumns = {@JoinColumn(name = "fk_game")},
            inverseJoinColumns = {@JoinColumn(name = "fk_theme")})
    private Set<Theme> themes = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "game")
    private Set<GameCopy> gameCopies = new HashSet<>();


    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lowerCaseName='" + lowerCaseName + '\'' +
                ", description='" + description + '\'' +
                ", playTime='" + playTime + '\'' +
                ", minNumberOfPlayer=" + minNumberOfPlayer +
                ", maxNumberOfPlayer=" + maxNumberOfPlayer +
                ", minAge=" + minAge +
                ", maxAge=" + maxAge +
                ", minMonth=" + minMonth +
                ", stuff='" + stuff + '\'' +
                ", preparation='" + preparation + '\'' +
                ", goal='" + goal + '\'' +
                ", coreRules='" + coreRules + '\'' +
                ", variant='" + variant + '\'' +
                ", ending='" + ending + '\'' +
                ", nature=" + nature +
                ", size='" + size + '\'' +
                ", images=" + images +
                ", creators=" + creators +
                ", categories=" + categories +
                ", gameCopies=" + gameCopies +
                '}';
    }


    // Helper methods

    /**
     * Adding a case-insensitive entry in database
     */
    @PrePersist
    @PreUpdate
    public void toLowerCase() {
        this.lowerCaseName = name != null ? name.toLowerCase() : null;
    }

    public void addImage(Image image) {
        this.images.add(image);
        image.setGame(this);
    }

    public void removeImage(Image image) {
        this.images.remove(image);
        image.setGame(null);
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

    public void removeGameCopy(GameCopy gameCopy) {
        this.gameCopies.remove(gameCopy);
        gameCopy.setGame(null);
    }

    public void addTheme(Theme theme) {
        this.themes.add(theme);
        theme.getGames().add(this);
    }

    public void removeTheme(Theme theme) {
        this.themes.remove(theme);
        theme.getGames().remove(this);
    }


    // Self one to many helper methods
    public void addProductLine(ProductLine productLine) {
        productLine.getGames().add(this);
        this.setProductLine(productLine);
    }

    public void removeProductLine(ProductLine productLine) {
        productLine.getGames().remove(this);
        this.setProductLine(null);
    }

    public void addCoreGame(Game coreGame) {
        coreGame.getExpansions().add(this);
        this.setCoreGame(coreGame);
    }

    public void removeCoreGame() {
        if (this.getCoreGame() != null) {
            this.getCoreGame().getExpansions().remove(this);
            this.setCoreGame(null);
        }
    }

    public void addExpansion(Game expansion) {
        expansion.setCoreGame(this);
        this.getExpansions().add(expansion);
    }

    public void removeExpansion(Game expansion) {
        expansion.setCoreGame(null);
        this.getExpansions().remove(expansion);
    }
}
