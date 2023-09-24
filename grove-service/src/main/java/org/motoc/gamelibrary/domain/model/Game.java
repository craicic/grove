package org.motoc.gamelibrary.domain.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import org.motoc.gamelibrary.domain.enumeration.GameNature;
import org.motoc.gamelibrary.technical.validation.annotation.ConsistentAgeRange;
import org.motoc.gamelibrary.technical.validation.annotation.ConsistentNumberOfPlayer;
import org.motoc.gamelibrary.technical.validation.annotation.SelectYearOrMonth;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
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
@Table(name = "game", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "lower_case_title"))
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_seq_gen")
    @SequenceGenerator(name = "game_seq_gen", sequenceName = "game_sequence", initialValue = 1)
    private Long id;

    @NotBlank(message = "Title cannot be null or blank")
    @Column(nullable = false)
    private String title;

    @ToString.Exclude
    @Column(nullable = false, name = "lower_case_title")
    private String lowerCaseTitle;

    @Size(max = 2000, message = "Description should not exceed 2000 characters")
    @Column(length = 2000)
    private String description;

    @Size(max = 255, message = "Play time should not exceed 20 characters")
    @Column
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

    @Range(min = 0, max = 100, message = "Min month must be between 0 and 100")
    private short minMonth;

    /**
     * Material that the game contains (parts, meeples, cards, etc...)
     */
    @Size(max = 2000, message = "Material should not exceed 2000 characters")
    @Column(length = 2000)
    private String material;

    @Lob
    private String rules;

    /**
     * Describes alternative version of the rules
     */
    @Size(max = 15000, message = "Variant should not exceed 15000 characters")
    @Lob
    private String variant;

    /**
     * An enumeration : toy, board game, wooden game, etc...
     */
    @Column
    private GameNature nature;

    private LocalDate yearOfRelease;

    @OneToMany(mappedBy = "game")
    private Set<Image> images = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "game_creator",
            schema = "public",
            joinColumns = {@JoinColumn(name = "fk_game")},
            inverseJoinColumns = {@JoinColumn(name = "fk_creator")})
    private Set<Creator> creators = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "game_category",
            schema = "public",
            joinColumns = {@JoinColumn(name = "fk_game")},
            inverseJoinColumns = {@JoinColumn(name = "fk_category")})
    private Set<Category> categories = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "game_mechanism",
            schema = "public",
            joinColumns = {@JoinColumn(name = "fk_game")},
            inverseJoinColumns = {@JoinColumn(name = "fk_mechanism")})
    private Set<Mechanism> mechanisms = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "game")
    private Set<GameCopy> gameCopies = new HashSet<>();

    @Override
    public String toString() {
        return "Game{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", lowerCaseTitle='" + lowerCaseTitle + '\'' +
               ", description='" + description + '\'' +
               ", playTime='" + playTime + '\'' +
               ", minNumberOfPlayer=" + minNumberOfPlayer +
               ", maxNumberOfPlayer=" + maxNumberOfPlayer +
               ", minAge=" + minAge +
               ", maxAge=" + maxAge +
               ", nature=" + nature +
               '}';
    }


    // Helper methods

    /**
     * Adding a case-insensitive entry in database
     */
    @PrePersist
    @PreUpdate
    public void toLowerCase() {
        this.lowerCaseTitle = title != null ? title.toLowerCase() : null;
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

    public void addMechanism(Mechanism mechanism) {
        this.mechanisms.add(mechanism);
        mechanism.getGames().add(this);
    }

    public void removeMechanism(Mechanism mechanism) {
        this.mechanisms.remove(mechanism);
        mechanism.getGames().remove(this);
    }
}
