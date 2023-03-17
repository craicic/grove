package org.motoc.gamelibrary.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reservation", schema = "public")
public class Reservation {

    @Id
    private Long id;

    private LocalDateTime scheduledWithdrawal;
    private LocalDateTime scheduledReturn;

    private LocalDate dateOfStart;
    private LocalDate dateOfEnding;

}
