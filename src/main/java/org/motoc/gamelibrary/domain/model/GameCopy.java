package org.motoc.gamelibrary.domain.model;

import lombok.*;
import org.motoc.gamelibrary.domain.enumeration.GeneralState;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A game, it describe a copy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "game_copy", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "object_code"))
public class GameCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Pattern(regexp = "^[0-9]{1,5}$")
    @Column(name = "object_code", nullable = false)
    private String objectCode;

    @DecimalMin(value = "0.0", inclusive = true, message = "Price value cannot be below 0.0")
    @Digits(integer = 10, fraction = 2, message = "Price maximum integer part is 10, maximum fractional part is 2")
    private BigDecimal price;

    /**
     * The location of the game in premises
     */
    @Size(max = 255, message = "Location cannot exceed 255 characters")
    private String location;

    @PastOrPresent(message = "Date of purchase must be in the past or present")
    @Column(name = "date_of_purchase")
    private LocalDate dateOfPurchase;

    @PastOrPresent(message = "Date of purchase must be in the past or present")
    @NotNull(message = "Date of purchase cannot be null")
    @Column(name = "register_date", nullable = false)
    private LocalDate registerDate;

    @NotBlank(message = "Wear condition cannot be null or blank")
    @Column(name = "wear_condition", nullable = false)
    private String wearCondition;

    @NotNull(message = "General State cannot be null")
    @Column(name = "general_state", nullable = false)
    private GeneralState generalState;

    @Column(name = "is_loanable")
    private boolean isLoanable;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "fk_game")
    private Game game;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_seller")
    private Seller seller;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_publisher")
    private Publisher publisher;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "gameCopy")
    private Set<Loan> loans = new HashSet<>();

    // Helper methods

    public void addLoan(Loan loan) {
        this.loans.add(loan);
        loan.setGameCopy(this);
    }

    public void removeLoan(Loan loan) {
        this.loans.remove(loan);
        loan.setGameCopy(null);
    }

    public void addSeller(Seller seller) {
        this.setSeller(seller);
        seller.getGameCopies().add(this);
    }

    public void removeSeller(Seller seller) {
        this.setSeller(null);
        seller.getGameCopies().remove(this);
    }

    public void addPublisher(Publisher publisher) {
        this.setPublisher(publisher);
        publisher.getCopies().add(this);
    }

    public void removePublisher(Publisher publisher) {
        publisher.getCopies().remove(this);
        this.setPublisher(null);
    }

    // addGame/removeGame methods are not needed because adding game is mandatory at the creation of this object
}
