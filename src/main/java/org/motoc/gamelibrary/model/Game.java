package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * A game, it's a generic description of the item, not of a copy
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @OneToOne
    public Game coreGame;

    @Column(nullable = false)
    private String name;
    private String description;
    private String numberOfPlayers;

    @Column(nullable = false)
    private short ageMin;
    private short ageMax;

    private String Stuff;
    private String preparation;
    private String goal;
    @Lob
    private String coreRules;
    @Lob
    private String variant;
    @Lob
    private String ending;
    private String nature;
    private String size;
    @Column(nullable = false)
    private boolean isBoardGame;
    private int editionNumber;

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
