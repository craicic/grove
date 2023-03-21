package org.motoc.gamelibrary.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "game_copy_reservation", schema = "public")
public class GameCopyReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_copy_reservation_seq_gen")
    @SequenceGenerator(name = "game_copy_reservation_seq_gen", sequenceName = "game_copy_reservation_sequence", initialValue = 1)
    private Long id;

    @ManyToOne
    @MapsId("fk_game_copy")
    private GameCopy gameCopy;

    @ManyToOne
    @MapsId("fk_reservation")
    private Reservation reservation;

    private boolean isLate;
}
