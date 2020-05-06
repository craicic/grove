package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * A game, it describe a copy
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GameCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false)
    private long objectCode;
    private double price;
    private String location;
    private LocalDate dateOfPurchase;
    private LocalDate registerDate;
    private String wearCondition;

    @ManyToOne
    @JoinColumn(name = "fk_game")
    private Game game;

}
