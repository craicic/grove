package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * The product line of a game
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductLine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;

    @OneToMany(mappedBy = "productLine")
    private Set<Game> games = new HashSet<>();

    // Helper methods
    public void addGame(Game game) {
        this.games.add(game);
        game.setProductLine(this);
    }
}
