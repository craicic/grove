package org.motoc.gamelibrary.model;

import lombok.*;
import org.motoc.gamelibrary.validation.annotation.ConsistentDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * A game copy loan
 * Note taht loanStatus will not be used before V1.5
 */
@ConsistentDateTime
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loan", schema = "public")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Loan start time cannot be null")
    @Column(name = "loan_start_time", nullable = false)
    private LocalDate loanStartTime;

    @NotNull(message = "Loan start time cannot be null")
    @Column(name = "loan_end_time", nullable = false)
    private LocalDate loanEndTime;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    // todo weakness here, LAZY preferred
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_game_copy")
    private GameCopy gameCopy;

    @NotNull(message = "isClosed must have a value")
    @Column(name = "is_closed", nullable = false)
    private boolean isClosed;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_account")
    private Account account;

    /*Uncomment this for V1.5*/
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "fk_loan_status")
//    private LoanStatus loanStatus;

    // Helper methods

    /*Uncomment this for V1.5*/
//    public void addLoanStatus(LoanStatus loanStatus) {
//        this.setLoanStatus(loanStatus);
//        loanStatus.getLoans().add(this);
//    }
//
//    public void removeLoanStatus(LoanStatus loanStatus) {
//        this.setLoanStatus(null);
//        loanStatus.getLoans().remove(this);
//    }
}
