package org.motoc.gamelibrary.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.motoc.gamelibrary.technical.validation.annotation.ConsistentDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * A game copy loan
 * Note taht loanStatus will not be used before V1.5
 */
@ConsistentDateTime
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loan", schema = "public")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_seq_gen")
    @SequenceGenerator(name = "loan_seq_gen", sequenceName = "loan_sequence", initialValue = 100)
    private Long id;

    @NotNull(message = "Loan start time cannot be null")
    @Column(name = "loan_start_time", nullable = false)
    private LocalDate loanStartTime;

    @NotNull(message = "Loan start time cannot be null")
    @Column(name = "loan_end_time", nullable = false)
    private LocalDate loanEndTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_game_copy")
    private GameCopy gameCopy;

    @NotNull(message = "isClosed must have a value")
    @Column(name = "is_closed", nullable = false)
    private boolean isClosed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_account")
    private Account account;

}
