package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * A game copy loan
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;

    private String userUuid;
    private LocalDateTime loanStartTime;
    private LocalDateTime loanEndTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_game_copy")
    private GameCopy gameCopy;

    @OneToOne
    @JoinColumn(name = "fk_loan_status")
    private LoanStatus loanStatus;
}
