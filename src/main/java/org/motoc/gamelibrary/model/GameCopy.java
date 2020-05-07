package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.motoc.gamelibrary.model.enumartion.GeneralStateEnum;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @Column(nullable = false)
    private LocalDate registerDate;

    @Column(nullable = false)
    private String wearCondition;

    @Column(nullable = false)
    private GeneralStateEnum generalState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_game")
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_seller")
    private Seller seller;

    @OneToMany(mappedBy = "gameCopy")
    private Set<Loan> loans = new HashSet<>();

    public void addLoan(Loan loan) {
        this.loans.add(loan);
        loan.setGameCopy(this);
    }

}
