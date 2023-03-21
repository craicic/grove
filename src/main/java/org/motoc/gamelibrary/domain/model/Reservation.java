package org.motoc.gamelibrary.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.motoc.gamelibrary.domain.enumeration.LoanStatus;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "reservation")
    private Set<GameCopyReservation> gameCopyReservations;

    @ManyToOne
    private Administrator administrator;


}
