package org.motoc.gamelibrary.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.motoc.gamelibrary.domain.enumeration.LoanStatus;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "reservation", schema = "public")
public class Reservation {

    @Id
    private Long id;

    private LocalDateTime dateTimeOfWithdrawal;
    private LocalDateTime dateTimeOfReturn;

    @Column(nullable = false)
    private LocalDate dateOfStart;
    private LocalDate dateOfEnding;

    @Column(nullable = false)
    private LoanStatus status;

    @PastOrPresent
    private LocalDateTime dateTimeOfCreation;

    /**
     * In euro
     */
    @DecimalMin(value = "0.0", inclusive = true, message = "Total amount value cannot be below 0.0")
    @Digits(integer = 10, fraction = 2, message = "Total amount maximum integer part is 10, maximum fractional part is 2")
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "reservation")
    private Set<GameCopyReservation> gameCopyReservations;

    @ManyToOne
    private Administrator administrator;


}
