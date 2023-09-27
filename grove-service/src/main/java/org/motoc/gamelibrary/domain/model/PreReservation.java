package org.motoc.gamelibrary.domain.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "pre_reservation", schema = "public")
public class PreReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pre_reservation_seq_gen")
    @SequenceGenerator(name = "pre_reservation_seq_gen", sequenceName = "pre_reservation_sequence", initialValue = 1)
    private Long id;

    @Pattern(regexp = "^[0-9]{1,6}$")
    private Integer code;

    private LocalDate dateOfStart;

    private LocalDate dateOfEnding;

    @PastOrPresent(message = "Date time of Demand must be in the past or present")
    private LocalDateTime dateTimeOfDemand;

    @PastOrPresent(message = "Date time of Closure must be in the past or present")
    private LocalDateTime dateTimeOfClosure;

    /**
     * In order to clean the database, this boolean coupled with dateTimeOfClosure can ease us to delete useless rows.
     */
    private boolean isClosed;

    /**
     * My idea here is to set bidirectional relationship, because there is few game copies and pre-reservation a cleaned often
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "game_copy_pre_reservation",
            schema = "public",
            joinColumns = @JoinColumn(name = "fk_pre_reservation"),
            inverseJoinColumns = @JoinColumn(name = "fk_game_copy"))
    private Set<GameCopy> gameCopies = new HashSet<>();

    public void addGameCopy(GameCopy copy) {
        this.gameCopies.add(copy);
        copy.getPreReservations().add(this);
    }

    public void removeGameCopy(GameCopy copy) {
        this.gameCopies.remove(copy);
        copy.getPreReservations().remove(this);
    }
}
